package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.models.Product
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.OrderViewModel

//screen to add products to the order
@Composable
fun AddProductScreen(
    navController: NavController,
    viewModel: OrderViewModel,
    modifier: Modifier = Modifier
) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    //list of product and quantity to add to the order
    val productsToAdd = remember { mutableStateListOf<Pair<Product, Int>>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CancelButton {
                    navController.navigate(GourmetManagerScreen.CreateOrder.name) {
                        popUpTo(GourmetManagerScreen.CreateOrder.name) {
                            inclusive = true
                        }
                    }
                }
                //button to add the products to the order
                AddProductsButton(
                    navController = navController,
                    viewModel = viewModel,
                    productsToAdd = productsToAdd,
                    new = viewModel.new.value
                )
            }
            LaunchedEffect(Unit) {
                selectedCategory?.let { viewModel.getProductsFromCategory(it) }
            }

            //lista de produtos da categoria selecionada
            val productsInCategory by viewModel.productList.collectAsState()

            if (productsInCategory.isEmpty()) { //if there are no products for this category
                Text(
                    text = stringResource(id = R.string.no_products),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                productsInCategory.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CardAddProduct(
                            product = item,
                            //changing the quantity of a product in the list of products to add
                            onQuantityChanged = { quantity ->
                                if (quantity > 0) {
                                    val existingIndex =
                                        productsToAdd.indexOfFirst { it.first == item }
                                    if (existingIndex >= 0) {
                                        productsToAdd[existingIndex] = item to quantity
                                    } else {
                                        productsToAdd.add(item to quantity)
                                    }
                                } else {
                                    //remove the product from the list of products to add if the quantity is zero
                                    productsToAdd.removeAll { it.first == item }
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


//card that shows each product and allows to change quantities
@Composable
fun CardAddProduct(
    product: Product,
    onQuantityChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableIntStateOf(0) }

    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Row(
            modifier = modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.width(120.dp)
            )
            //to change quantities
            QuantitySelector(
                initialQuantity = 0
            ) {
                quantity = it
                onQuantityChanged(it)
            }
        }

    }
}

//allows the user to change quantities of a product
@Composable
fun QuantitySelector(
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
                painter = painterResource(R.drawable.round_remove_24),
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

@Composable
fun AddProductsButton(
    navController: NavController,
    viewModel: OrderViewModel,
    productsToAdd: List<Pair<Product, Int>>,
    new: Boolean
) {

    Button(
        onClick = {
            for (item in productsToAdd) {
                val existingItemIndex =
                    viewModel.listProducts.indexOfFirst { it.first.id == item.first.id }
                if (existingItemIndex != -1) {
                    //if the product is already associated to the order, quantity will be updated
                    val existingProductPair = viewModel.listProducts[existingItemIndex]
                    viewModel.listProducts[existingItemIndex] =
                        existingProductPair.first to (existingProductPair.second + item.second)
                } else {
                    //if not, it will add the new product
                    viewModel.listProducts.add(item)
                }
            }
            if (new) {
                navController.navigate(GourmetManagerScreen.CreateOrder.name)
            } else {
                navController.navigate(GourmetManagerScreen.EditDetails.name)
            }
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(
            Icons.Rounded.Add, contentDescription = "", tint = MaterialTheme.colorScheme.onTertiary
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = stringResource(id = R.string.add),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}


@Composable
fun CancelButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.cancel),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
fun AddProductPreview() {
    GourmetManagerTheme {
        AddProductScreen(
            navController = rememberNavController(),
            viewModel = OrderViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}