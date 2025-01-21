package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


//screen to edit a reservation
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditReservationDetailsScreen(
    navController: NavController,
    viewModel: ReservationViewModel,
    modifier: Modifier = Modifier
) {

    val reservation by viewModel.selectedReservation.collectAsState()

    var nrPeople by remember { mutableStateOf(reservation.nrPeople.toString()) }
    var isNumberValid by remember { mutableStateOf(true) }
    var comments by remember { mutableStateOf(reservation.comments ?: "") }
    var isDateValid by remember { mutableStateOf(true) }
    var dateErrorMessage by remember { mutableStateOf("") }
    val errorPastDate = stringResource(id = R.string.error_past_date)
    val errorInvalidDate = stringResource(id = R.string.select_valid_date)
    var isTimeValid by remember { mutableStateOf(true) }
    var timeErrorMessage by remember { mutableStateOf("") }
    val errorInvalidTime = stringResource(id = R.string.select_valid_time)
    var day by remember { mutableStateOf(reservation.dateReservation.substring(8, 10)) }
    var month by remember { mutableStateOf(reservation.dateReservation.substring(5, 7)) }
    var year by remember { mutableStateOf(reservation.dateReservation.substring(0, 4)) }
    var hour by remember { mutableStateOf(reservation.dateReservation.substring(11, 13)) }
    var minutes by remember { mutableStateOf(reservation.dateReservation.substring(14, 16)) }

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
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier.weight(3f)) {
                    DateFields(
                        day = day,
                        month = month,
                        year = year,
                        isDateValid = isDateValid,
                        errorMessage = dateErrorMessage,
                        onDayChange = {
                            day = it
                            isDateValid =
                                validateDate(
                                    day,
                                    month,
                                    year,
                                    errorPastDate,
                                    errorInvalidDate
                                ).first
                            dateErrorMessage =
                                validateDate(
                                    day,
                                    month,
                                    year,
                                    errorPastDate,
                                    errorInvalidDate
                                ).second
                        },
                        onMonthChange = {
                            month = it
                            isDateValid =
                                validateDate(
                                    day,
                                    month,
                                    year,
                                    errorPastDate,
                                    errorInvalidDate
                                ).first
                            dateErrorMessage =
                                validateDate(
                                    day,
                                    month,
                                    year,
                                    errorPastDate,
                                    errorInvalidDate
                                ).second
                        },
                        onYearChange = {
                            year = it
                            isDateValid =
                                validateDate(
                                    day,
                                    month,
                                    year,
                                    errorPastDate,
                                    errorInvalidDate
                                ).first
                            dateErrorMessage =
                                validateDate(
                                    day,
                                    month,
                                    year,
                                    errorPastDate,
                                    errorInvalidDate
                                ).second
                        }
                    )
                }
                //Spacer(modifier = Modifier.height(8.dp))
                Column(modifier.weight(2f)) {
                    TimeFields(
                        hour = hour,
                        minutes = minutes,
                        isTimeValid = isTimeValid,
                        errorMessage = timeErrorMessage,
                        onHourChange = {
                            hour = it
                            isTimeValid = validateTime(hour, minutes, errorInvalidTime).first
                            timeErrorMessage = validateTime(hour, minutes, errorInvalidTime).second
                        },
                        onMinutesChange = {
                            minutes = it
                            isTimeValid = validateTime(hour, minutes, errorInvalidTime).first
                            timeErrorMessage = validateTime(hour, minutes, errorInvalidTime).second
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            NumberPeopleField(nrPeople = nrPeople,
                isNumberValid = isNumberValid,
                onValueChange = {
                    nrPeople = it
                    isNumberValid = try {
                        it.toInt()
                        true
                    } catch (e: NumberFormatException) {
                        false
                    }
                })
            Spacer(modifier = Modifier.height(8.dp))
            TableReservationsDropDownMenu(viewModel)
            CommentsField(comments) { comments = it }
            UpdateReservationButton(
                reservation = reservation,
                enabled = isNumberValid && isDateValid,
                nrPeople = try {
                    nrPeople.toInt()
                } catch (e: NumberFormatException) {
                    0
                },
                comments = comments,
                day = day,
                month = month,
                year = year,
                hour = hour,
                minutes = minutes,
                isDateValid = isDateValid,
                isTimeValid = isTimeValid,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun UpdateReservationButton(
    reservation: Reservation,
    enabled: Boolean,
    nrPeople: Int,
    comments: String,
    day: String,
    month: String,
    year: String,
    hour: String,
    minutes: String,
    isDateValid: Boolean,
    isTimeValid: Boolean,
    navController: NavController,
    viewModel: ReservationViewModel
) {

    val showEditDialog = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    var dateReservation = 0L
    if (isDateValid && isTimeValid) {
        try {
            dateReservation = getMillisForDate(
                year.toInt(),
                month.toInt(),
                day.toInt(),
                hour.toInt(),
                minutes.toInt()
            )
        } catch (_: NumberFormatException) {
        }
    }

    val selectedTable = viewModel.selectedTable.intValue

    val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm",
        Locale.getDefault()
    )
    val formattedReservationDate = dateFormat.format(dateReservation)

    Button(
        onClick = {
            showEditDialog.value = true
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
            text = stringResource(id = R.string.edit_reservation),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (showEditDialog.value) {
        EditReservationDialog(
            reservation = reservation,
            onDismissRequest = { showEditDialog.value = false },
            onConfirmation = {
                viewModel.updateReservation(
                    reservation, selectedTable,
                    formattedReservationDate, nrPeople, comments
                )
                showEditDialog.value = false
                showDialog.value = true
            }
        )
    }
    if (showDialog.value) {
        ReservationConfirmationDialog(
            reservationId = reservation.id,
            action = "edit",
            onDismissRequest = {
                showDialog.value = false
                navController.navigate(GourmetManagerScreen.Reservation.name)
            },
        )
    }
}

@Composable
fun EditReservationDialog(
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
                        stringResource(id = R.string.edit_reservation_confirmation),
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