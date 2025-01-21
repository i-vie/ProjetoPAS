package com.example.myapplication

import android.app.Application
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.HomeViewModel

//home screen with dashboard with some statistics about orders
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    employeeName: String,
    modifier: Modifier = Modifier
) {

    val openOrders by viewModel.totalOpenOrdersToday.collectAsState()
    val closedOrders by viewModel.totalClosedOrdersToday.collectAsState()
    val totalClosedOrdersPrice by viewModel.totalOrdersPriceToday.collectAsState()
    val totalReservationsToday by viewModel.totalReservationsToday.collectAsState()
    val newReservations by viewModel.totalNewReservations.collectAsState()
    val totalOrdersToday by viewModel.totalOrdersToday.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getTotalOpenOrdersToday()
        viewModel.getTotalClosedOrdersToday()
        viewModel.getTotalRevenueToday()
        viewModel.getTotalOrdersToday()
        viewModel.getTotalNewReservations()
        viewModel.getTotalReservationsToday()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Greeting(employeeName)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Title(stringResource(id = R.string.dashboard))
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                CardItem(
                    title = R.string.open_orders_stat,
                    number = openOrders.toString(),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                CardItem(
                    title = R.string.closed_orders_stat,
                    number = closedOrders.toString(),
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardItem(
                    title = R.string.total_stat,
                    number = totalClosedOrdersPrice.toString(),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CardItem(
                    title = R.string.total_orders,
                    number = totalOrdersToday.toString(),
                    modifier = Modifier
                        .weight(1f)
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardItem(
                    title = R.string.new_reservation_stat,
                    number = newReservations.toString(),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CardItem(
                    title = R.string.today_reservation_stat,
                    number = totalReservationsToday.toString(),
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.outlineVariant
    )
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 3.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
fun CardItem(
    @StringRes title: Int,
    number: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSecondaryContainer),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                modifier = Modifier
                    .padding(4.dp),
                text = stringResource(title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = String.format(stringResource(id = R.string.hello), name),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.padding(16.dp)
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    GourmetManagerTheme {
        HomeScreen(
            viewModel = HomeViewModel(Application()),
            employeeName = "Employee",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}