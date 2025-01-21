package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.models.OrderItem
import com.example.myapplication.ui.models.Product
import com.example.myapplication.ui.models.Table
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.theme.white
import com.example.myapplication.ui.viewmodels.OrderViewModel


/*
represents the screen "Criar Pedido"
includes a dropdown menu to select the table, a button to add products to the order,
a button to create the order (enabled only when there are products in the order) and
a table with the information about the products, quantities and prices of the order
 */
@Composable
fun CreateOrderScreen(
    navController: NavController,
    viewModel: OrderViewModel,
    employeeId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxHeight()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.select_a_table),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(top = 32.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            //dropdown menu to select the table for the order
            TableDropDownMenu(viewModel)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                viewModel.new.value = true
                //button to choose the category to add products to the list
                AddProductButton(navController)
                //button to create order, enabled when list of products is not empty
                CreateOrderButton(
                    navController,
                    viewModel,
                    enabled = viewModel.listProducts.isNotEmpty()
                )
            }


            //products table
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            ) {
                //header
                item {
                    TableHeaderRow()
                }
                val selectedProducts = viewModel.listProducts
                //products list
                itemsIndexed(selectedProducts) { index, (product, quantity) ->
                    ProductTableRow(
                        product = product,
                        productQuantity = quantity,
                        onQuantityChanged = { quantity: Int ->
                            if (quantity > 0) {
                                selectedProducts[index] = product to quantity
                            } else {
                                selectedProducts.removeAt(index)
                            }
                        }
                    )
                }
            }
        }
    }
}

//dropdwon menu that allows the user to select a table for the order
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TableDropDownMenu(
    viewModel: OrderViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    val tables by viewModel.tableList.collectAsState()
    LaunchedEffect(null) { viewModel.getTables() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = stringResource(id = R.string.table_nr) + viewModel.selectedTable.intValue.toString(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    disabledIndicatorColor = MaterialTheme.colorScheme.inversePrimary
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
            ) {
                tables.forEach { item: Table ->
                    DropdownMenuItem(
                        {
                            Text(
                                text = stringResource(id = R.string.table_nr) + item.id.toString(),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            viewModel.selectedTable.value = item.id
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

/*
this button allows the navigation to the Choose Category screen to select a category,
before selecting the products
 */
@Composable
fun AddProductButton(
    navController: NavController
) {
    Button(
        onClick = {
            navController.navigate(GourmetManagerScreen.ChooseCategory.name)
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(
            Icons.Rounded.Add, contentDescription = "", tint = white
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = stringResource(id = R.string.add_product),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/*
only enabled when the list of products is not empty - receives this information from
parameter
enabled"
adds a new order to the database with the info about the current employeeId, the
table selected by the user and as an open order and orderItems for the respecitve products
and quantities
 */
@Composable
fun CreateOrderButton(
    navController: NavController,
    viewModel: OrderViewModel,
    enabled: Boolean
) {
    val showDialog = remember { mutableStateOf(false) }
    val orderProducts = viewModel.listProducts

    val selectedTable = viewModel.selectedTable.value


    val clicked = remember { mutableStateOf(false) }
    Button(
        onClick = {
            clicked.value = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
        ),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (clicked.value) {
        LaunchedEffect(orderProducts) {
            //adds the new order and the order items to the respective tables
            if (orderProducts.isNotEmpty()) {
                val newOrder = Order(
                    open = 1,
                    employeeId = 1,
                    tableNr = selectedTable,
                    total = calculateOrderTotal(orderProducts)
                )
                viewModel.addOrderWithItems(newOrder, orderProducts.map { (product, quantity) ->
                    OrderItem(orderId = 0, productId = product.id, quantity = quantity)
                })
            }
        }
        showDialog.value = true
        clicked.value = false
    }
    if (showDialog.value) {
        val orderNr by viewModel.orderNr.collectAsState()
        ConfirmationDialog(
            orderNr = orderNr,
            action = "create",
            onDismissRequest = {
                showDialog.value = false
                viewModel.clearOrder()
                navController.navigate(GourmetManagerScreen.Home.name)
            },
        )
    }
}

//calculate the price of product * quantity of product (product line total)
fun calculateProductTotal(product: Product, quantity: Int): Double {
    return product.price * quantity
}

fun calculateOrderTotal(listProducts: List<Pair<Product, Int>>): Double {
    var total = 0.0
    for (pair in listProducts) {
        total += pair.first.price * pair.second
    }
    return total

}


//header of products list table
@Composable
fun TableHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.product),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = stringResource(id = R.string.quantity),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.weight(0.2f)
        )
        Text(
            text = stringResource(id = R.string.price),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.weight(0.2f)
        )
    }
}

//rows of the products list table with product information
@Composable
fun ProductTableRow(
    product: Product,
    productQuantity: Int,
    onQuantityChanged: (Int) -> Unit
) {
    var quantity by remember { mutableIntStateOf(productQuantity) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 8.dp)
        )
        TableQuantitySelector(
            initialQuantity = quantity
        ) {
            quantity = it
            onQuantityChanged(it)
        }
        Text(
            text = calculateProductTotal(product, quantity).toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(0.2f)
        )

    }
}

@Composable
fun ConfirmationDialog(
    orderNr: Int,
    action: String,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() })
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text =
                    if (action == "create") {
                        //the order number does not update immediately when created,
                        //so it is necessary to add 1
                        String.format(stringResource(id = R.string.order_sucess_created), orderNr)
                    } else if (action == "edit") {
                        //if we are editing an existing order
                        String.format(stringResource(id = R.string.order_sucess_updated), orderNr)
                    } else if (action == "delete") {
                        //if we are deleting an existing order
                        String.format(stringResource(id = R.string.order_sucess_deleted), orderNr)
                    } else {
                        //if we are closing an existing order
                        String.format(stringResource(id = R.string.order_sucess_closed), orderNr)
                    },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            stringResource(id = R.string.ok),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TableQuantitySelector(
    initialQuantity: Int,
    onQuantityChanged: (Int) -> Unit
) {
    var quantity by remember { mutableIntStateOf(initialQuantity) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(
            onClick = {
                if (quantity > 0) {
                    quantity--
                    onQuantityChanged(quantity)
                }
            },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter =
                if (quantity > 1) {
                    painterResource(R.drawable.round_remove_24)
                } else {
                    painterResource(id = R.drawable.delete)
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(30.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        IconButton(
            onClick = {
                quantity++
                onQuantityChanged(quantity)
            },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun CreateOrderScreenPreview() {
    GourmetManagerTheme {
        CreateOrderScreen(
            navController = rememberNavController(),
            viewModel = OrderViewModel(),
            employeeId = 1,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}