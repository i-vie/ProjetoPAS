package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myapplication.ui.models.Reservation
import com.example.myapplication.ui.viewmodels.ReservationViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ViewReservationDetailsScreen(
    navController: NavController,
    viewModel: ReservationViewModel,
    modifier: Modifier = Modifier
) {

    val reservation by viewModel.selectedReservation.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Row {
                Column {
                    Spacer(
                        modifier
                            .padding(16.dp)
                    )
                    Text(
                        text = stringResource(R.string.reservation_nr),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.table),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.reservation_nr_people),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.date),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.hour),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.date_created),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.employeeNr),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    if (!reservation.comments.isNullOrEmpty()) {
                        Text(
                            text = stringResource(R.string.comments),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                        )
                    }
                    Spacer(modifier.padding(24.dp))
                }
                Column {
                    Spacer(
                        modifier
                            .padding(16.dp)
                    )
                    Text(
                        text = "${reservation.id}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = reservation.tableNr.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = "${reservation.nrPeople}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = reservation.dateReservation.substring(0, 10),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    Text(
                        text = reservation.dateReservation.substring(11, 16),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    reservation.dateCreated?.let {
                        Text(
                            text = it.substring(0, 10),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                        )
                    }
                    Text(
                        text = "${reservation.employeeId}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                    if (!reservation.comments.isNullOrEmpty()) {
                        Text(
                            text = reservation.comments,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                        )
                    }
                    Spacer(modifier.padding(24.dp))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //button to delete reservations to dates after the current date
                if (reservation.dateReservation > System.currentTimeMillis().toString()) {
                    DeleteReservationButton(
                        reservation = reservation,
                        viewModel = viewModel,
                        navController = navController
                    )
                    //button to edit the reservation details
                    EditReservationButton(
                        navController = navController
                    )
                }
            }

        }
    }
}

@Composable
fun DeleteReservationButton(
    reservation: Reservation,
    viewModel: ReservationViewModel,
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
        DeleteReservationDialog(
            reservation = reservation,
            onDismissRequest = { showDeleteDialog.value = false },
            onConfirmation = {
                viewModel.deleteReservation(reservation)
                showDeleteDialog.value = false
                showDialog.value = true
            }
        )
    }
    if (showDialog.value) {
        ReservationConfirmationDialog(
            reservationId = reservation.id,
            action = "delete",
            onDismissRequest = {
                showDialog.value = false
                navController.navigate(GourmetManagerScreen.Reservation.name)
            },
        )
    }
}

@Composable
fun DeleteReservationDialog(
    reservation: Reservation,
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
                        stringResource(id = R.string.delete_reservation_confirmation),
                        reservation.id
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
fun EditReservationButton(
    navController: NavController
) {
    Button(
        onClick = {
            navController.navigate(GourmetManagerScreen.EditReservationDetails.name)
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

