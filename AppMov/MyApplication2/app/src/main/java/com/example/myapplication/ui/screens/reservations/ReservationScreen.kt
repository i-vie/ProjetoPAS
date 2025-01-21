package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.models.Reservation
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.theme.Poppins
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.viewmodels.ReservationViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

//reservation screen
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationScreen(
    navController: NavController,
    viewModel: ReservationViewModel,
    modifier: Modifier = Modifier
) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    //tabs for reservations for current day, reservations made today and reservations for a specific day
    val tabTitles = listOf(
        stringResource(id = R.string.today),
        stringResource(id = R.string.new_fem_pl),
        stringResource(id = R.string.choose_date)
    )
    var dateReservation by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm",
        Locale.getDefault()
    )

    val formattedReservationDate = dateFormat.format(dateReservation)
    //gets the reservations for today, created today and by date
    viewModel.getReservationsToday()
    viewModel.getNewReservations()
    viewModel.getReservationsByDay(formattedReservationDate)

    val reservationsToday by viewModel.reservationsToday.collectAsState()
    val newReservations by viewModel.newReservations.collectAsState()
    val reservationsByDay by viewModel.reservationsByDay.collectAsState()

    var isDateValid by remember { mutableStateOf(true) }
    var dateErrorMessage by remember { mutableStateOf("") }
    val errorPastDate = stringResource(id = R.string.error_past_date)
    val errorInvalidDate = stringResource(id = R.string.select_valid_date)

    var day by remember { mutableStateOf(formattedReservationDate.substring(8, 10)) }
    var month by remember { mutableStateOf(formattedReservationDate.substring(5, 7)) }
    var year by remember { mutableStateOf(formattedReservationDate.substring(0, 4)) }




    val reservations = when (selectedTabIndex) {
        0 -> reservationsToday
        1 -> newReservations
        2 -> reservationsByDay
        else -> emptyList()
    }

    val title = when (selectedTabIndex) {
        0 -> stringResource(id = R.string.today_reservations)
        1 -> stringResource(id = R.string.new_reservations)
        2 -> stringResource(id = R.string.reservation_per_day)
        else -> ""
    }

    Column(
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
                        navController.navigate(GourmetManagerScreen.CreateReservation.name)
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
                        text = stringResource(id = R.string.new_reservation),
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
            Title(title)
            Spacer(modifier = Modifier.height(8.dp))
            if (selectedTabIndex == 2) {
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (Modifier.weight(0.85f)) {
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
                    Column (Modifier.weight(0.15f)) {
                        Spacer(modifier = Modifier.height(20.dp))
                        ClickableText(
                            text = AnnotatedString(
                                text = stringResource(id = R.string.ok),
                                spanStyle = SpanStyle(
                                    color = blue,
                                    fontFamily = Poppins
                                )
                            ),
                            onClick = {
                                try {
                                    dateReservation = getMillisForDate(
                                        year.toInt(),
                                        month.toInt(),
                                        day.toInt(),
                                        0,
                                        0
                                    )
                                } catch (_: NumberFormatException) {
                                }
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
            reservations.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardReservation(
                        reservation = item,
                        onClick = ({
                            viewModel.selectReservation(item)
                            navController.navigate(GourmetManagerScreen.ViewReservationDetails.name)
                        }),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }

        }
    }
}


@SuppressLint("NewApi")
@Composable
fun CardReservation(
    reservation: Reservation,
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
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = reservation.id.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                    text = stringResource(id = R.string.table_nr) + reservation.tableNr.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = reservation.dateReservation.substring(0, 10),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = String.format(
                        stringResource(id = R.string.people_number),
                        reservation.nrPeople
                    ),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = reservation.dateReservation.substring(11,16),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

//receives Int day, month, year, hour and minutes and returns the respective date in millis
fun getMillisForDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return calendar.timeInMillis
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ReservationScreenPreview() {
    GourmetManagerTheme {
        ReservationScreen(
            navController = rememberNavController(),
            viewModel = ReservationViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}