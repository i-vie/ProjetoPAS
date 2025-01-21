package com.example.myapplication.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repositories.ReservationRepository
import com.example.myapplication.data.repositories.TableRepository
import com.example.myapplication.ui.models.Reservation
import com.example.myapplication.ui.models.Table
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReservationViewModel : ViewModel() {

    private val reservationRepository = ReservationRepository()

    //selected reservation (to view or edit details)
    private val _selectedReservation = MutableStateFlow<Reservation>(Reservation(0, 0, 0, "", 0))
    val selectedReservation: StateFlow<Reservation> = _selectedReservation

    fun selectReservation(reservation: Reservation) {
        _selectedReservation.value = reservation
    }


    //reservations for the current day
    private val _reservationsToday = MutableStateFlow<List<Reservation>>(emptyList())
    val reservationsToday: StateFlow<List<Reservation>> = _reservationsToday

    fun getReservationsToday() {
        viewModelScope.launch {
            val reservations = reservationRepository.getReservationsToday()
            _reservationsToday.value = reservations
        }
    }

    //reservations for a specific day
    private val _reservationsByDay = MutableStateFlow<List<Reservation>>(emptyList())
    val reservationsByDay: StateFlow<List<Reservation>> = _reservationsByDay

    fun getReservationsByDay(day: String) {
        viewModelScope.launch {
            val reservations = reservationRepository.getReservationsByDay(day)
            _reservationsByDay.value = reservations
        }
    }

    //reservations created in the current day
    private val _newReservations = MutableStateFlow<List<Reservation>>(emptyList())
    val newReservations: StateFlow<List<Reservation>> = _newReservations

    fun getNewReservations() {
        viewModelScope.launch {
            val reservations = reservationRepository.getNewReservations()
            _newReservations.value = reservations
        }
    }

    //number of last reservation created
    private val _reservationNr = MutableStateFlow<Int>(0)
    val reservationNr: StateFlow<Int> = _reservationNr

    fun addReservation(reservation: Reservation) {
        viewModelScope.launch {
            Log.d("ReserView", "inserir reserva: $reservation")
            val reservationNr = reservationRepository.createReservation(reservation)
            if (reservationNr != null) {
                _reservationNr.value = reservationNr
            } else {
                // Trate o caso de erro
                Log.e(
                    "ReservationViewModel",
                    "Failed to create reservation: repository returned null"
                )
            }
        }
    }

    fun updateReservation(
        reservation: Reservation,
        newTable: Int,
        newDate: String,
        nrPeople: Int,
        newComment: String
    ) {
        val updatedReservation = reservation.copy(
            tableNr = newTable,
            dateReservation = newDate,
            nrPeople = nrPeople,
            comments = newComment
        )
        viewModelScope.launch {
            reservationRepository.updateReservation(updatedReservation)
        }
    }

    fun deleteReservation(reservation: Reservation) {
        viewModelScope.launch {
            reservationRepository.deleteReservation(reservation)
        }
    }

    private val tableRepository = TableRepository()
    private val _tableList = MutableStateFlow<List<Table>>(emptyList())
    val tableList: StateFlow<List<Table>> = _tableList //list of tables categories

    fun getTables() {
        viewModelScope.launch {
            val tables = tableRepository.getTables()
            _tableList.value = tables
        }
    }

    var selectedTable = mutableIntStateOf(1)
}