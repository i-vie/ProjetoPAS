package com.example.myapplication

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.models.OrderItem
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.OrderViewModel


//screen to edit order details (table and products)
@SuppressLint("UnrememberedMutableState")
@Composable
fun EditOrderDetailsScreen(
    navController: NavController,
    viewModel: OrderViewModel,
    modifier: Modifier = Modifier
) {
    val order by viewModel.selectedOrder.collectAsState()
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
            Text(
                text = stringResource(R.string.select_a_table),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(top = 32.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            //dropdown menu to select the table for the order
            viewModel.selectedTable.value = order.tableNr
            TableDropDownMenu(viewModel)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                viewModel.new.value = false
                //button to choose the category to add products to the list
                AddProductButton(navController)
                //button to save order detail changes
                EditOrderButton(
                    order,
                    navController,
                    orderViewModel = viewModel,
                    enabled = true
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
                itemsIndexed(selectedProducts) { index, (product, quantity) ->
                    ProductTableRow(
                        product = product,
                        productQuantity = quantity,
                        onQuantityChanged = { newQuantity ->
                            selectedProducts[index] = product to newQuantity
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun EditOrderButton(
    order: Order,
    navController: NavController,
    orderViewModel: OrderViewModel,
    enabled: Boolean
) {

    val orderProducts = orderViewModel.listProducts
    val showDialog = remember { mutableStateOf(false) }
    val selectedTable = orderViewModel.selectedTable.value

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
            text = stringResource(id = R.string.change),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (clicked.value) {
        LaunchedEffect(orderProducts) {
            //adds the new order and the order items to the respective tables
            if (orderProducts.isNotEmpty()) {
                orderViewModel.updateOrder(order, selectedTable, calculateOrderTotal(orderProducts))
                orderViewModel.updateOrderItems(order.id, orderProducts.map { (product, quantity) ->
                    OrderItem(orderId = order.id, productId = product.id, quantity = quantity)
                })
            }
        }
        showDialog.value = true
        clicked.value = false
    }
    if (showDialog.value) {
        ConfirmationDialog(
            orderNr = order.id,
            action = "edit",
            onDismissRequest = {
                showDialog.value = false
                orderViewModel.clearOrder()
                navController.navigate(GourmetManagerScreen.Home.name)
            },
        )
    }
}

@Preview
@Composable
fun EditOrderDetailsPreview() {
    GourmetManagerTheme {

        EditOrderDetailsScreen(
            navController = rememberNavController(),
            viewModel = OrderViewModel(),
            modifier = Modifier.fillMaxSize()
        )
    }
}