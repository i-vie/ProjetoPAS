package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.NavigationItems
import com.example.myapplication.ui.screens.LoadingScreen
import com.example.myapplication.ui.screens.categories.MenuScreen
import com.example.myapplication.ui.theme.Poppins
import com.example.myapplication.ui.theme.yellow
import com.example.myapplication.ui.viewmodels.AppViewModel
import com.example.myapplication.ui.viewmodels.HomeViewModel
import com.example.myapplication.ui.viewmodels.MenuViewModel
import com.example.myapplication.ui.viewmodels.OrderViewModel
import com.example.myapplication.ui.viewmodels.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * enum values that represent the screens in the app
 */
enum class GourmetManagerScreen(@StringRes val title: Int) {
    Login(title = R.string.app_name),
    Home(title = R.string.home),
    Order(title = R.string.order),
    Reservation(R.string.reservations),
    Menu(R.string.menu),
    NewOrder(title = R.string.new_order),
    CreateOrder(title = R.string.create_order),
    AddProduct(title = R.string.add_product),
    ChooseCategory(title = R.string.choose_category),
    CreateProduct(title = R.string.add_new_product),
    ShowProducts(title = R.string.show_products),
    CreateCategory(title = R.string.add_category),
    EditProduct(title = R.string.change_product),
    Settings(title = R.string.settings),
    EditCategory(title = R.string.change_category),
    ViewDetails(title = R.string.order_details),
    EditDetails(title = R.string.edit_details),
    Tables(title = R.string.tables),
    Employees(title = R.string.employees),
    Profile(title = R.string.profile),
    CreateAccount(title = R.string.create_account),
    ForgotPassword(title = R.string.forgot_passoword),
    ViewEmployeeDetails(title = R.string.employee_details),
    ChangePassword(title = R.string.change_password),
    CreateReservation(title = R.string.create_reservation),
    ViewReservationDetails(title = R.string.view_reservation),
    EditReservationDetails(title = R.string.edit_reservation),
    ChangeLocale(title = R.string.change_locale)
}

class MinFabItem(
    val icon: Painter,
    val label: String
)

//screen list where it is not possible to navigate back from adn where the
//floating button will appear
val listScreens: List<GourmetManagerScreen> = listOf(
    GourmetManagerScreen.Login,
    GourmetManagerScreen.Home,
    GourmetManagerScreen.Order,
    GourmetManagerScreen.Reservation,
    GourmetManagerScreen.Menu
)

val orderScreens: List<GourmetManagerScreen> = listOf(
    GourmetManagerScreen.Order,
    GourmetManagerScreen.ViewDetails,
    GourmetManagerScreen.EditDetails,
    GourmetManagerScreen.ChooseCategory,
    GourmetManagerScreen.CreateOrder,
    GourmetManagerScreen.AddProduct
)

val menuScreens: List<GourmetManagerScreen> = listOf(
    GourmetManagerScreen.Menu,
    GourmetManagerScreen.EditCategory,
    GourmetManagerScreen.CreateCategory,
    GourmetManagerScreen.CreateProduct,
    GourmetManagerScreen.ShowProducts,
    GourmetManagerScreen.EditProduct
)

val reservationScreens: List<GourmetManagerScreen> = listOf(
    GourmetManagerScreen.Reservation,
    GourmetManagerScreen.CreateReservation,
    GourmetManagerScreen.ViewReservationDetails,
    GourmetManagerScreen.EditReservationDetails
)

val loginScreens: List<GourmetManagerScreen> = listOf(
    GourmetManagerScreen.CreateAccount,
    GourmetManagerScreen.ForgotPassword,
    GourmetManagerScreen.Login
)

//top bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GourmetManagerTopBar(
    currentScreen: GourmetManagerScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onClick: () -> Unit,
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {

    val darkTheme by viewModel.darkTheme.collectAsState()
    //top bar is present is all screens except for the Login, loading, create account and forgot password screen
    if (!loginScreens.contains(currentScreen)) {
        CenterAlignedTopAppBar(
            title = {
                //main screen has the logo at the top instead of the title of the screen
                if (currentScreen == GourmetManagerScreen.Home) {
                    Image(
                        modifier = modifier
                            .width(250.dp)
                            .padding(dimensionResource(R.dimen.padding_small)),
                        painter = painterResource(R.drawable.logo_horizontal_alternativo),
                        contentDescription = null
                    )
                } else {
                    Text(
                        text = stringResource(currentScreen.title),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.outline
            ),
            modifier = modifier
                .shadow(4.dp),
            navigationIcon = {
                if (listScreens.contains(currentScreen)) {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else if (currentScreen == GourmetManagerScreen.CreateOrder) {
                    IconButton(
                        onClick = {
                            navController.navigate(GourmetManagerScreen.Order.name)
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else if (canNavigateBack && !listScreens.contains(currentScreen)) {
                    IconButton(
                        onClick = { navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            },
            actions = {
                val iconId = if (darkTheme) R.drawable.sun else R.drawable.moon
                IconButton(onClick = {
                    viewModel.setTheme(!darkTheme)
                }) {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }
}

//bottom bar
@Composable
fun GourmetManagerBottomBar(
    currentScreen: GourmetManagerScreen,
    currentDestination: NavDestination?,
    navController: NavController
) {

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .height(80.dp)
            .shadow(4.dp)
    ) {
        //each item of the bar: icon and title
        BottomNavigationItem(
            modifier = Modifier.padding(4.dp),
            icon = {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = null,
                    tint = if (currentScreen == GourmetManagerScreen.Home) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier
                        .height(36.dp)
                        .width(36.dp)
                )
            },
            label = {
                Text(
                    stringResource(GourmetManagerScreen.Home.title),
                    color = if (currentScreen == GourmetManagerScreen.Home) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            },
            selected = currentDestination?.hierarchy?.any { it.route == GourmetManagerScreen.Home.name } == true,
            unselectedContentColor = MaterialTheme.colorScheme.inversePrimary,
            onClick = {
                navController.navigate(GourmetManagerScreen.Home.name)
            }
        )
        BottomNavigationItem(
            modifier = Modifier.padding(top = 4.dp),
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ementa3),
                    contentDescription = null,
                    tint = if (menuScreens.contains(currentScreen)) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .padding(1.dp)
                )
            },
            label = {
                Text(
                    stringResource(GourmetManagerScreen.Menu.title),
                    color = if (menuScreens.contains(currentScreen)) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            },
            selected = currentDestination?.hierarchy?.any { it.route == GourmetManagerScreen.Menu.name } == true,
            unselectedContentColor = MaterialTheme.colorScheme.inversePrimary,
            onClick = {
                navController.navigate(GourmetManagerScreen.Menu.name)
            }
        )
        BottomNavigationItem(
            modifier = Modifier.padding(top = 4.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.pedidos2),
                    contentDescription = null,
                    tint = if (orderScreens.contains(currentScreen)) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .padding(1.dp)
                )
            },
            label = {
                Text(
                    stringResource(GourmetManagerScreen.Order.title),
                    color = if (orderScreens.contains(currentScreen)) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            },
            selected = currentDestination?.hierarchy?.any { it.route == GourmetManagerScreen.Order.name } == true,
            onClick = {
                navController.navigate(GourmetManagerScreen.Order.name)
            }
        )
        BottomNavigationItem(
            modifier = Modifier.padding(top = 4.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.reservas6),
                    contentDescription = null,
                    tint = if (reservationScreens.contains(currentScreen)) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier
                        .height(36.dp)
                        .width(36.dp)
                )
            },
            label = {
                Text(
                    stringResource(GourmetManagerScreen.Reservation.title),
                    color = if (reservationScreens.contains(currentScreen)) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            },
            selected = currentDestination?.hierarchy?.any { it.route == GourmetManagerScreen.Reservation.name } == true,
            onClick = {
                navController.navigate(GourmetManagerScreen.Reservation.name)
            }
        )
    }
}


@Composable
fun GourmetManagerFloatingButton(
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(GourmetManagerScreen.CreateOrder.name)
            },
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ) {
            Icon(
                painterResource(id = R.drawable.novo_pedido),
                "adicionar pedido",
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
        }
    }
}

@Composable
fun SubMenu(
    navController: NavController,
    viewModel: AppViewModel,
    scope: CoroutineScope,
    drawerState: androidx.compose.material3.DrawerState
) {
    Column(Modifier.padding(start = 65.dp, end = 24.dp)) {

        LaunchedEffect(viewModel) {
            viewModel.getLoggedInEmployee()
        }
        val loggedInEmployee by viewModel.loggedInEmployee.collectAsState()
        val admin = loggedInEmployee!!.admin
        ClickableText(
            text = AnnotatedString(
                text = stringResource(id = R.string.profile),
                spanStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = Poppins
                )
            ),
            onClick = {
                scope.launch {
                    navController.navigate(GourmetManagerScreen.Profile.name)
                    drawerState.close()
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        //not implemented at this point after modifications
        /*
        if (admin == 1) {
            ClickableText(
                text = AnnotatedString(
                    text = stringResource(id = R.string.employees),
                    spanStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = Poppins
                    )
                ),
                onClick = {
                    scope.launch {
                        navController.navigate(GourmetManagerScreen.Employees.name)
                        drawerState.close()
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
            ClickableText(
                text = AnnotatedString(
                    text = stringResource(id = R.string.tables),
                    spanStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = Poppins
                    )
                ),
                onClick = {
                    scope.launch {
                        navController.navigate(GourmetManagerScreen.Tables.name)
                        drawerState.close()
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

         */
        ClickableText(
            text = AnnotatedString(
                text = stringResource(id = R.string.change_locale),
                spanStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = Poppins
                )
            ),
            onClick = {
                scope.launch {
                    navController.navigate(GourmetManagerScreen.ChangeLocale.name)
                    drawerState.close()
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ClickableText(
            text = AnnotatedString(
                text = stringResource(id = R.string.logout),
                spanStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = Poppins
                )
            ),
            onClick = {
                scope.launch {
                    viewModel.logout()
                    navController.navigate(GourmetManagerScreen.Login.name)
                    drawerState.close()
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GourmetManagerApp(
    viewModel: AppViewModel,
    orderViewModel: OrderViewModel,
    menuViewModel: MenuViewModel,
    homeViewModel: HomeViewModel,
    reservationViewModel: ReservationViewModel
) {

    val showSubMenu = remember { mutableStateOf(false) }
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val isLoading by viewModel.isLoading.collectAsState()//remember { mutableStateOf(true) }

    //while data is loading, a loading screen will be displayed
    LaunchedEffect(isLoading) {
        viewModel.isUserLoggedIn()
        viewModel.getLoggedInEmployee()
        delay(1000)
        viewModel.setLoading(false)
    }
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()
    val loggedInEmployee by viewModel.loggedInEmployee.collectAsState()


    if (isLoading) {
        LoadingScreen()
    } else {
        val startDestination =
            if (isUserLoggedIn) {
                GourmetManagerScreen.Home.name
            } else {
                GourmetManagerScreen.Login.name
            }


        //items that will be displayed in the navigation drawer
        val menuItems = listOf(
            NavigationItems(
                screen = GourmetManagerScreen.Order,
                icon = Icon(
                    painter = painterResource(R.drawable.pedidos2),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            ),
            NavigationItems(
                screen = GourmetManagerScreen.Menu,
                icon = Icon(
                    painter = painterResource(R.drawable.ementa3),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            ),
            NavigationItems(
                screen = GourmetManagerScreen.Reservation,
                icon = Icon(
                    painter = painterResource(R.drawable.reservas6),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .padding(1.dp)
                )
            ),
            NavigationItems(
                screen = GourmetManagerScreen.Settings,
                icon = Icon(
                    Icons.Rounded.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .padding(1.dp)
                )
            )
        )


        // Get current back stack entry
        val backStackEntry by navController.currentBackStackEntryAsState()
        // Get the name of the current screen
        val currentScreen = GourmetManagerScreen.valueOf(
            backStackEntry?.destination?.route ?: GourmetManagerScreen.Login.name
        )
        val currentDestination = backStackEntry?.destination

        val loggedIn = currentScreen != GourmetManagerScreen.Login

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = MaterialTheme.colorScheme.primary,
                    drawerContentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .width(250.dp)
                                .padding(16.dp),
                            painter = painterResource(R.drawable.logo_horizontal_alternativo),
                            contentDescription = null
                        )
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close drawer",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp), color = yellow
                    )
                    menuItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                unselectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            label = { Text(text = stringResource(item.screen.title)) },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                if (item.screen == GourmetManagerScreen.Settings) {
                                    showSubMenu.value = !showSubMenu.value
                                } else {
                                    scope.launch {
                                        navController.navigate(item.screen.name)
                                        drawerState.close()
                                    }
                                }
                            },
                            icon = { item.icon },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                        if (item.screen == GourmetManagerScreen.Settings) {
                            AnimatedVisibility(visible = showSubMenu.value) {
                                SubMenu(
                                    navController = navController,
                                    scope = scope,
                                    viewModel = viewModel,
                                    drawerState = drawerState
                                )
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(horizontal = 16.dp), color = yellow
                        )
                    }
                }
            },
            gesturesEnabled = loggedIn
        ) {
            Scaffold(
                topBar = {
                    if (loggedIn) {
                        GourmetManagerTopBar(
                            currentScreen = currentScreen,
                            canNavigateBack = navController.previousBackStackEntry != null,
                            navigateUp = { navController.navigateUp() },
                            viewModel = viewModel,
                            navController = navController,
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        )
                    }
                },
                bottomBar = {
                    if (!loginScreens.contains(currentScreen)) {
                        GourmetManagerBottomBar(
                            currentScreen = currentScreen,
                            currentDestination = currentDestination,
                            navController = navController
                        )
                    }
                },
                floatingActionButton = {
                    if (currentScreen == GourmetManagerScreen.Home || currentScreen == GourmetManagerScreen.Menu) {
                        GourmetManagerFloatingButton(
                            navController = navController
                        )
                    }
                },
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable(GourmetManagerScreen.Login.name) {
                        LoginScreen(navController, viewModel = viewModel)
                    }
                    composable(GourmetManagerScreen.Home.name) {
                        loggedInEmployee?.let { it1 ->
                            HomeScreen(
                                viewModel = homeViewModel,
                                employeeName = it1.name
                            )
                        }
                    }
                    composable(GourmetManagerScreen.Order.name) {
                        loggedInEmployee?.let { it1 ->
                            OrderScreen(
                                navController = navController,
                                viewModel = orderViewModel,
                                employeeId = it1.id
                            )
                        }
                    }
                    composable(GourmetManagerScreen.CreateOrder.name) {
                        loggedInEmployee?.let { it1 ->
                            CreateOrderScreen(
                                navController,
                                viewModel = orderViewModel,
                                employeeId = it1.id
                            )
                        }
                    }
                    composable(GourmetManagerScreen.AddProduct.name) {
                        AddProductScreen(
                            navController,
                            viewModel = orderViewModel
                        )
                    }
                    composable(GourmetManagerScreen.ChooseCategory.name) {
                        ChooseCategoryScreen(
                            navController,
                            viewModel = orderViewModel
                        )
                    }
                    composable(GourmetManagerScreen.Menu.name) {
                        MenuScreen(
                            navController = navController,
                            viewModel = menuViewModel
                        )
                    }
                    composable(GourmetManagerScreen.EditCategory.name) {
                        EditCategoryScreen(
                            navController = navController,
                            viewModel = menuViewModel
                        )

                    }
                    composable(GourmetManagerScreen.ShowProducts.name) {
                        ShowProductsScreen(
                            navController = navController,
                            viewModel = menuViewModel
                        )
                    }
                    composable(GourmetManagerScreen.CreateProduct.name) {
                        CreateProductScreen(
                            navController = navController,
                            viewModel = menuViewModel
                        )
                    }
                    composable(GourmetManagerScreen.CreateCategory.name) {
                        CreateCategoryScreen(
                            navController = navController,
                            viewModel = menuViewModel
                        )
                    }
                    composable(GourmetManagerScreen.EditProduct.name) {
                        EditProductScreen(
                            navController = navController,
                            viewModel = menuViewModel
                        )
                    }
                    composable(GourmetManagerScreen.Reservation.name) {
                        ReservationScreen(
                            viewModel = reservationViewModel,
                            navController = navController
                        )
                    }
                    composable(GourmetManagerScreen.ViewDetails.name) {
                        ViewOrderDetailsScreen(
                            navController = navController,
                            viewModel = orderViewModel
                        )
                    }
                    composable(GourmetManagerScreen.EditDetails.name) {
                        EditOrderDetailsScreen(
                            navController = navController,
                            viewModel = orderViewModel
                        )
                    }
                    composable(GourmetManagerScreen.Profile.name) {
                        ProfileScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(GourmetManagerScreen.Tables.name) {
                        TablesScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    /*
                composable(GourmetManagerScreen.Employees.name) {
                    EmployeesScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(GourmetManagerScreen.CreateAccount.name) {
                    CreateAccountScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(GourmetManagerScreen.ForgotPassword.name) {
                    ForgotPasswordScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(GourmetManagerScreen.ViewEmployeeDetails.name) {
                    EmployeeDetailsScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(GourmetManagerScreen.ChangePassword.name) {
                    ChangePasswordScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }

                 */
                    composable(GourmetManagerScreen.CreateReservation.name) {
                        loggedInEmployee?.let { it1 ->
                            CreateReservationScreen(
                                navController = navController,
                                viewModel = reservationViewModel,
                                employeeId = it1.id
                            )
                        }
                    }
                    composable(GourmetManagerScreen.ViewReservationDetails.name) {
                        ViewReservationDetailsScreen(
                            navController = navController,
                            viewModel = reservationViewModel
                        )

                    }
                    composable(GourmetManagerScreen.EditReservationDetails.name) {
                        EditReservationDetailsScreen(
                            navController = navController,
                            viewModel = reservationViewModel
                        )
                    }
                    composable(GourmetManagerScreen.ChangeLocale.name) {
                        ChangeLocaleScreen()
                    }
                }
            }
        }
    }
}


