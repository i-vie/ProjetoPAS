package com.example.myapplication.data.api

import android.annotation.SuppressLint
import com.example.myapplication.ui.models.Reservation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservationApiService {

    @GET("reservations")
    suspend fun getAllReservations(): Response<ApiResponse<List<Reservation>>>

    @GET("reservations/{id}")
    suspend fun getReservationById(
        @Path("id") id: Int
    ): Response<ApiResponse<Reservation>>

    @POST("reservations")
    suspend fun createReservation(@Body reservation: Reservation): Response<ApiResponse<Reservation>>

    @PUT("reservations/{id}")
    suspend fun updateReservation(
        @Path("id") id: Int,
        @Body reservation: Reservation
    ): Response<ApiResponse<Reservation>>

    @GET("reservations/by-day/{day}")
    suspend fun getReservationsByDay(
        @Path("day") day: String
    ): Response<ApiResponse<List<Reservation>>>

    @GET("reservations/by-day/{day}")
    suspend fun getReservationsToday(
        @Path("day") day: String = getCurrentDate()
    ): Response<ApiResponse<List<Reservation>>>

    @GET("reservations/total/by-day/{day}")
    suspend fun getTotalReservationsToday(
        @Path("day") day: String = getCurrentDate()
    ): Response<TotalApiResponse<Int>>

    @GET("reservations/created/{day}")
    suspend fun getReservationsCreatedToday(
        @Path("day") day: String = getCurrentDate()
    ) : Response<ApiResponse<List<Reservation>>>

    @GET("reservations/total/created/{day}")
    suspend fun getTotalReservationsCreatedToday(
        @Path("day") day: String = getCurrentDate()
    ) : Response<TotalApiResponse<Int>>

    @DELETE("reservation/{id}")
    suspend fun delete(
        @Path("id") id: Int,
        @Body reservation: Reservation
    ): Response<ApiResponse<Unit>>

    @SuppressLint("NewApi")
    private fun getCurrentDate(): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return java.time.LocalDate.now().format(formatter)
    }
}