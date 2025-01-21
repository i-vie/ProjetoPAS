package com.example.myapplication.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repositories.OrderRepository
import com.example.myapplication.data.repositories.ReservationRepository
import com.example.myapplication.ui.models.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val reservationRepository = ReservationRepository()
    private val orderRepository = OrderRepository()


    //totals (number of orders, total revenue)
    private val _totalOrdersToday = MutableStateFlow<Int>(0)
    val totalOrdersToday: StateFlow<Int> = _totalOrdersToday

    fun getTotalOrdersToday() {
        Log.d("HomeScreen", "getTotalOrdersToday called")
        viewModelScope.launch {
            _totalOrdersToday.value = orderRepository.getTotalOrdersToday()
        }
    }

    private val _totalOpenOrdersToday = MutableStateFlow<Int>(0)
    val totalOpenOrdersToday: StateFlow<Int> = _totalOpenOrdersToday

    //total open order in the current day
    fun getTotalOpenOrdersToday() {
        Log.d("HomeScreen", "getTotalOpenOrdersToday called")
        viewModelScope.launch {
            _totalOpenOrdersToday.value = orderRepository.getTotalOrdersByStatus(isOpen = 1)
        }
    }


    private val _totalClosedOrdersToday = MutableStateFlow<Int>(0)
    val totalClosedOrdersToday: StateFlow<Int> = _totalClosedOrdersToday

    //total closed order in the current day
    fun getTotalClosedOrdersToday() {
        Log.d("HomeScreen", "getTotalClosedOrdersToday called")
        viewModelScope.launch {
            _totalClosedOrdersToday.value = orderRepository.getTotalOrdersByStatus(isOpen = 0)
        }
    }

    private val _totalOrdersPriceToday = MutableStateFlow<Double>(0.0)
    val totalOrdersPriceToday: StateFlow<Double> = _totalOrdersPriceToday

    //total revenue of the current day (closed orders only)
    fun getTotalRevenueToday() {
        Log.d("HomeScreen", "getTotalRevenueToday called")
        viewModelScope.launch {
            var revenue = 0.0
            if (_totalClosedOrdersToday.value != 0) {
                revenue = orderRepository.getTotalAmountByStatus(isOpen = 0)
            }
            _totalOrdersPriceToday.value = revenue
        }
    }

    //total reservations for current day
    private val _totalReservationsToday = MutableStateFlow<Int>(0)
    val totalReservationsToday: StateFlow<Int> = _totalReservationsToday

    fun getTotalReservationsToday() {
        Log.d("HomeScreen", "getTotalReservationsToday called")
        viewModelScope.launch {
            val totalReservations = reservationRepository.getTotalReservationsToday()
            _totalReservationsToday.value = totalReservations
        }
    }

    //total reservations created in the current day
    private val _totalNewReservations = MutableStateFlow<Int>(0)
    val totalNewReservations: StateFlow<Int> = _totalNewReservations

    fun getTotalNewReservations() {
        Log.d("HomeScreen", "getTotalNewReservations called")
        viewModelScope.launch {
            val totalReservations = reservationRepository.getTotalNewReservations()
            _totalNewReservations.value = totalReservations
        }
    }
}