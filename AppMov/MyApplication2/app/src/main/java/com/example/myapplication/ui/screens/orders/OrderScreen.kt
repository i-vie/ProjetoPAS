package com.example.myapplication

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.theme.mediumGrey
import com.example.myapplication.ui.viewmodels.OrderViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


//order screen with the orders
@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrderViewModel,
    employeeId: Int,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    //tabs for all open orders, orders for the current employee and all orders (open or closed)
    val tabTitles =
        listOf(
            stringResource(id = R.string.open),
            stringResource(id = R.string.my_orders),
            stringResource(id = R.string.today)
        )
    viewModel.getOpenOrders()
    viewModel.getMyOrders(employeeId)
    viewModel.getOrdersToday()
    val openOrders by viewModel.openOrders.collectAsState()
    val myOrders by viewModel.myOrders.collectAsState()
    val allOrders by viewModel.orderList.collectAsState()

    val orders = when (selectedTabIndex) {
        0 -> openOrders
        1 -> myOrders
        2 -> allOrders
        else -> emptyList()
    }

    val title = when (selectedTabIndex) {
        0 -> stringResource(id = R.string.open_orders)
        1 -> stringResource(id = R.string.my_orders_det)
        2 -> stringResource(id = R.string.all_orders)
        else -> ""
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, color = MaterialTheme.colorScheme.onPrimary) }
                )
            }
        }

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        viewModel.clearOrder()
                        navController.navigate(GourmetManagerScreen.CreateOrder.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.new_order),
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Title(title)
            Spacer(modifier = Modifier.height(8.dp))
            orders.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardOrder(
                        order = item,
                        total = item.total,
                        onClick = ({
                            viewModel.selectOrder(item)
                            navController.navigate(GourmetManagerScreen.ViewDetails.name)
                            viewModel.getOrderItemsByOrderId(item.id)
                        }),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }

        }
    }
}

@Composable
fun CardOrder(
    order: Order,
    total: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        var formattedDate = ""

        order.dateCreated?.let { dateString ->

            try {
                val iso8601Format =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val date = iso8601Format.parse(dateString)
                formattedDate = date?.let { dateFormat.format(it) }.toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        val color = if (order.open == 1) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            mediumGrey
        }
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = order.id.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    color = color,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.table_nr) + order.tableNr.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color = color
                )
                if (formattedDate.isNotBlank()) {
                    Text(
                        text = formattedDate.substring(0, 10),
                        style = MaterialTheme.typography.bodyLarge,
                        color = color
                    )
                }
                if (order.open == 0) {
                    Text(
                        text = stringResource(id = R.string.closed),
                        style = MaterialTheme.typography.bodySmall,
                        color = color
                    )
                }
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$total €",
                    style = MaterialTheme.typography.displaySmall,
                    color = color
                )
                if (formattedDate.isNotBlank()) {
                    Text(
                        text = formattedDate.substring(11),
                        style = MaterialTheme.typography.bodyLarge,
                        color = color
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderScreenPreview() {
    GourmetManagerTheme {
        OrderScreen(
            navController = rememberNavController(),
            viewModel = OrderViewModel(),
            employeeId = 1,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}