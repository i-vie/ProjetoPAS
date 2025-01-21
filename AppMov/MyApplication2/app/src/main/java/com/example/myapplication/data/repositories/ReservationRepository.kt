package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.api.ReservationApiService
import com.example.myapplication.data.api.RetrofitClient.getRetrofitInstance
import com.example.myapplication.ui.models.Reservation

class ReservationRepository {

    private val retrofit = getRetrofitInstance()
    private val apiService: ReservationApiService = retrofit.create(ReservationApiService::class.java)

    companion object {
        private const val TAG = "ReservationRepository"
    }

    suspend fun getAllReservations(): List<Reservation> {
        val response = apiService.getAllReservations()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getAllReservations Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getAllReservations Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getReservationById(id: Int): Reservation? {
        val response = apiService.getReservationById(id)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getReservationById Successful: ${apiResponse.toString()}")
            apiResponse?.data
        } else {
            Log.e(TAG, "getReservationById Failed: ${response.errorBody()?.string()}")
            null
        }
    }

    suspend fun createReservation(reservation: Reservation): Int? {
        return try {
            val response = apiService.createReservation(reservation)
            if (response.isSuccessful) {
                val createdReservation = response.body()?.data
                Log.d(TAG, "createReservation Successful: ${createdReservation.toString()}")
                createdReservation?.id
            } else {
                Log.e(TAG, "createReservation Failed: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "createReservation Exception: ${e.message}")
            null
        }
    }

    suspend fun updateReservation(reservation: Reservation): Boolean {
        val response = apiService.updateReservation(reservation.id, reservation)
        return if (response.isSuccessful) {
            Log.d(TAG, "updateReservation Successful")
            true
        } else {
            Log.e(TAG, "updateReservation Failed: ${response.errorBody()?.string()}")
            false
        }
    }

    suspend fun getReservationsByDay(day: String): List<Reservation> {
        val response = apiService.getReservationsByDay(day)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getReservationsByDay Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getReservationsByDay Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getReservationsToday(): List<Reservation> {
        val response = apiService.getReservationsToday()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getReservationsToday Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getReservationsToday Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getTotalReservationsToday(): Int {
        val response = apiService.getTotalReservationsToday()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getTotalReservationsToday Successful: $apiResponse")
            apiResponse?.total ?: 0
        } else {
            Log.e(TAG, "getTotalReservationsToday Failed: ${response.errorBody()?.string()}")
            0
        }
    }

    suspend fun getNewReservations(): List<Reservation> {
        val response = apiService.getReservationsCreatedToday()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getNewReservations Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getNewReservations Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getTotalNewReservations(): Int {
        val response = apiService.getTotalReservationsCreatedToday()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getTotalNewReservations Successful: $apiResponse")
            apiResponse?.total ?: 0
        } else {
            Log.e(TAG, "getTotalNewReservations Failed: ${response.errorBody()?.string()}")
            0
        }
    }

    suspend fun deleteReservation(reservation: Reservation): Boolean {
        return try {
            val response = apiService.delete(reservation.id, reservation)
            if (response.isSuccessful) {
                Log.d(TAG, "deleteReservation Successful: $reservation")
                true
            } else {
                Log.e(TAG, "deleteReservation Failed: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteReservation Exception: ${e.message}")
            false
        }
    }
}
