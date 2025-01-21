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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.models.Product
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.OrderViewModel

//screen to see the details of a selected order
@SuppressLint("UnrememberedMutableState")
@Composable
fun ViewOrderDetailsScreen(
    navController: NavController,
    viewModel: OrderViewModel,
    modifier: Modifier = Modifier
) {
    val order by viewModel.selectedOrder.collectAsState()
    LaunchedEffect(key1 = order.id) {
        viewModel.getOrderItemsByOrderId(order.id)
    }
    val orderProducts by viewModel.orderItems.collectAsState()

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
            Title(
                String.format(
                    stringResource(id = R.string.order_title),
                    order.id,
                    order.tableNr
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (order.open == 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    //button to edit the order details (table or products)
                    EditButton(
                        navController = navController
                    )

                }
            } else {
                Spacer(Modifier.padding(16.dp))
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
                viewModel.listProducts.clear()
                items(orderProducts) { item ->
                    LaunchedEffect(key1 = item.productId) {
                        viewModel.getProductById(item.productId)
                    }
                    val product by viewModel.productResult.collectAsState()

                    product?.let {
                        OrderProductTableRow(it, item.quantity, viewModel)
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${stringResource(R.string.employeeNr)} ${order.employeeId}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(top = 32.dp, start = 16.dp)
                )
            }
            if (order.open == 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DeleteOrderButton(
                        order = order,
                        viewModel = viewModel,
                        navController = navController
                    )
                    CloseOrderButton(
                        order = order,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun EditButton(
    navController: NavController
) {
    Button(
        onClick = {
            navController.navigate(GourmetManagerScreen.EditDetails.name)
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(
            Icons.Rounded.Edit,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = stringResource(id = R.string.edit),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun CloseOrderButton(
    order: Order,
    viewModel: OrderViewModel,
    navController: NavController
) {
    val showCloseDialog = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    Button(
        onClick = {
            showCloseDialog.value = true
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(
            Icons.Rounded.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = stringResource(id = R.string.close),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (showCloseDialog.value) {
        CloseOrderDialog(
            order = order,
            onDismissRequest = { showCloseDialog.value = false },
            onConfirmation = {
                viewModel.closeOrder(order)
                showCloseDialog.value = false
                showDialog.value = true
            }
        )
    }
    if (showDialog.value) {
        ConfirmationDialog(
            orderNr = order.id,
            action = "close",
            onDismissRequest = {
                showDialog.value = false
                viewModel.clearOrder()
                navController.navigate(GourmetManagerScreen.Order.name)
            },
        )
    }
}

@Composable
fun DeleteOrderButton(
    order: Order,
    viewModel: OrderViewModel,
    navController: NavController
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    Button(
        onClick = {
            showDeleteDialog.value = true
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Icon(
            Icons.Rounded.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = stringResource(id = R.string.delete),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (showDeleteDialog.value) {
        DeleteOrderDialog(
            order = order,
            onDismissRequest = { showDeleteDialog.value = false },
            onConfirmation = {
                viewModel.deleteOrder(order)
                showDeleteDialog.value = false
                showDialog.value = true
            }
        )
    }
    if (showDialog.value) {
        ConfirmationDialog(
            orderNr = order.id,
            action = "delete",
            onDismissRequest = {
                showDialog.value = false
                viewModel.clearOrder()
                navController.navigate(GourmetManagerScreen.Order.name)
            },
        )
    }
}

@Composable
fun DeleteOrderDialog(
    order: Order,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {

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
                    text = String.format(
                        stringResource(id = R.string.delete_order_confirmation),
                        order.id
                    ),
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
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
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
fun CloseOrderDialog(
    order: Order,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {

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
                    text = String.format(
                        stringResource(id = R.string.close_order_confirmation),
                        order.id
                    ),
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
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            stringResource(id = R.string.confirm),
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
fun OrderProductTableRow(
    product: Product,
    quantity: Int,
    viewModel: OrderViewModel
) {
    viewModel.listProducts.add(Pair(product, quantity))
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
                .weight(0.6f)
                .padding(start = 8.dp)
        )
        Text(
            text = quantity.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(0.2f)
        )

        Text(
            text = calculateProductTotal(product, quantity).toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(0.2f)
        )

    }
}

@Preview
@Composable
fun ViewOrderDetailsPreview() {
    GourmetManagerTheme {

        ViewOrderDetailsScreen(
            navController = rememberNavController(),
            viewModel = OrderViewModel(),
            modifier = Modifier.fillMaxSize()
        )

    }
}