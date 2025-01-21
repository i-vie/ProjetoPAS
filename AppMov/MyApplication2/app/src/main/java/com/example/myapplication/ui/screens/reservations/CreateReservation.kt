package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myapplication.ui.models.Reservation
import com.example.myapplication.ui.models.Table
import com.example.myapplication.ui.theme.Poppins
import com.example.myapplication.ui.viewmodels.ReservationViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeParseException
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateReservationScreen(
    navController: NavController,
    viewModel: ReservationViewModel,
    employeeId: Int,
    modifier: Modifier = Modifier
) {

    var nrPeople by remember { mutableStateOf("") }
    var isNumberValid by remember { mutableStateOf(true) }
    var comments by remember { mutableStateOf("") }
    var isDateValid by remember { mutableStateOf(true) }
    var dateErrorMessage by remember { mutableStateOf("") }
    val errorPastDate = stringResource(id = R.string.error_past_date)
    val errorInvalidDate = stringResource(id = R.string.select_valid_date)
    var isTimeValid by remember { mutableStateOf(true) }
    var timeErrorMessage by remember { mutableStateOf("") }
    val errorInvalidTime = stringResource(id = R.string.select_valid_time)
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }

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
                text = stringResource(R.string.fill_details),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(top = 32.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier.weight(1.5f)) {
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
                Column(modifier.weight(1f)) {
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
            TableReservationsDropDownMenu(viewModel)
            CommentsField(comments) { comments = it }
            CreateReservationButton(
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
                employeeId = employeeId,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TableReservationsDropDownMenu(
    viewModel: ReservationViewModel
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
                            viewModel.selectedTable.intValue = item.id
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NumberPeopleField(nrPeople: String, isNumberValid: Boolean, onValueChange: (String) -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = nrPeople,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.nr_people),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = if (isNumberValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                unfocusedIndicatorColor = if (isNumberValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (!isNumberValid && nrPeople.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.invalid_number),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }
    }
}


@Composable
fun CommentsField(comments: String, onValueChange: (String) -> Unit) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = comments,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = R.string.comments),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.outline,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        maxLines = 3,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
    )
}


@Composable
fun DateFields(
    day: String,
    month: String,
    year: String,
    isDateValid: Boolean,
    errorMessage: String,
    onDayChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 4.dp)
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 2.dp),
            value = day,
            onValueChange = onDayChange,
            label = {
                Text(
                    text = stringResource(id = R.string.day),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
        TextField(
            modifier = Modifier
                .weight(1.1f)
                .padding(start = 2.dp, end = 2.dp),
            value = month,
            onValueChange = onMonthChange,
            label = {
                Text(
                    text = stringResource(id = R.string.month),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = if (isDateValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                unfocusedIndicatorColor = if (isDateValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
        TextField(
            modifier = Modifier
                .weight(1.2f)
                .padding(start = 2.dp),
            value = year,
            onValueChange = onYearChange,
            label = {
                Text(
                    text = stringResource(id = R.string.year),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = if (isDateValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                unfocusedIndicatorColor = if (isDateValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
    if (!isDateValid && (day.isNotEmpty() || month.isNotEmpty() || year.isNotEmpty())) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
        )
    }
}

@Composable
fun TimeFields(
    hour: String,
    minutes: String,
    isTimeValid: Boolean,
    errorMessage: String,
    onHourChange: (String) -> Unit,
    onMinutesChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 16.dp)
    ) {
        TextField(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 2.dp),
            value = hour,
            onValueChange = onHourChange,
            label = {
                Text(
                    text = stringResource(id = R.string.hour),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = if (isTimeValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                unfocusedIndicatorColor = if (isTimeValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
        TextField(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 2.dp),
            value = minutes,
            onValueChange = onMinutesChange,
            label = {
                Text(
                    text = stringResource(id = R.string.minutes),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = if (isTimeValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                unfocusedIndicatorColor = if (isTimeValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
    if (!isTimeValid && (hour.isNotEmpty() || minutes.isNotEmpty())) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
        )
    }
}

@Composable
fun CreateReservationButton(
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
    employeeId: Int,
    navController: NavController,
    viewModel: ReservationViewModel
) {

    val showDialog = remember { mutableStateOf(false) }
    val clicked = remember { mutableStateOf(false) }
    var dateReservation by remember { mutableLongStateOf(System.currentTimeMillis()) }
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

    val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm",
        Locale.getDefault()
    )
    val formattedReservationDate = dateFormat.format(dateReservation)

    val selectedTable = viewModel.selectedTable.value

    val reservation = Reservation(
        employeeId = employeeId,
        tableNr = selectedTable,
        dateReservation = formattedReservationDate,
        nrPeople = nrPeople,
        comments = comments
    )

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
            text = stringResource(id = R.string.create_reservation),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (clicked.value) {
        viewModel.addReservation(reservation)
        navController.navigate(GourmetManagerScreen.Reservation.name)
        showDialog.value = true
        clicked.value = false
    }
    if (showDialog.value) {
        val reservationNr by viewModel.reservationNr.collectAsState()
        ReservationConfirmationDialog(
            reservationId = reservationNr,
            action = "create",
            onDismissRequest = {
                showDialog.value = false
                navController.navigate(GourmetManagerScreen.Home.name)
            },
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun validateDate(
    day: String,
    month: String,
    year: String,
    errorPastDate: String,
    errorInvalidDate: String
): Pair<Boolean, String> {
    var valid = true
    var dateErrorMessage = ""
    val currentDate = LocalDate.now()
    if (day.isNotEmpty() && month.isNotEmpty() && year.isNotEmpty()) {
        try {
            val dayInt = day.toInt()
            val monthInt = month.toInt()
            val yearInt = year.toInt()
            val date = LocalDate.of(yearInt, monthInt, dayInt)
            if (!(date == currentDate || date.isAfter(currentDate))) {
                dateErrorMessage = errorPastDate
                valid = false
            }
        } catch (e: DateTimeParseException) {
            valid = false
            dateErrorMessage = errorInvalidDate
        } catch (e: Exception) {
            valid = false
            dateErrorMessage = errorInvalidDate
        }
    }
    return Pair(valid, dateErrorMessage)
}

@RequiresApi(Build.VERSION_CODES.O)
fun validateTime(
    hour: String,
    minutes: String,
    errorInvalidTime: String
): Pair<Boolean, String> {
    var valid = true
    var timeErrorMessage = ""

    if (hour.isNotEmpty() && minutes.isNotEmpty()) {
        try {
            val hourInt = hour.toInt()
            val minutesInt = minutes.toInt()
            LocalTime.of(hourInt, minutesInt)
        } catch (e: DateTimeParseException) {
            valid = false
            timeErrorMessage = errorInvalidTime
        } catch (e: Exception) {
            valid = false
            timeErrorMessage = errorInvalidTime
        }
    }
    return Pair(valid, timeErrorMessage)
}

@Composable
fun ReservationConfirmationDialog(
    reservationId: Int,
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
                    when (action) {
                        "create" -> {
                            //if reservation is created
                            String.format(
                                stringResource(id = R.string.reservation_sucess_created),
                                reservationId
                            )
                        }

                        "edit" -> {
                            //if we are editing an existing reservation
                            String.format(
                                stringResource(id = R.string.reservation_sucess_updated),
                                reservationId
                            )
                        }

                        else -> {
                            //if we are deleting an existing reservation
                            String.format(
                                stringResource(id = R.string.reservation_sucess_deleted),
                                reservationId
                            )
                        }
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
