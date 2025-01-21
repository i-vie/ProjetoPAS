package com.example.myapplication.data.api

import android.annotation.SuppressLint
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.models.OrderItem
import com.example.myapplication.ui.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApiService {


    @GET("orders")
    suspend fun  getAllOrders(): Response<ApiResponse<List<Order>>>

    @GET("orders/{id}")
    suspend fun getOrderById(
        @Path("id") id: Int
    ): Response<ApiResponse<Order>>

    @GET("orders/employee/{employeeId}")
    suspend fun getOrdersByEmployeeId(
        @Path("employeeId") employeeId: Int
    ): Response<ApiResponse<List<Order>>>

    @POST("orders")
    suspend fun createOrder(@Body order: Order): Response<ApiResponse<Order>>

    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Path("id") id: Int,
        @Body order: Order
    ): Response<ApiResponse<Order>>

    @GET("orders/by-day/{day}")
    suspend fun getOrdersToday(
        @Path("day") day: String = getCurrentDate()
    ): Response<ApiResponse<List<Order>>>

    @GET("orders/total/by-day/{day}")
    suspend fun getTotalOrdersToday(
        @Path("day") day: String = getCurrentDate()
    ): Response<TotalApiResponse<Int>>

    @GET("orders/status/{status}")
    suspend fun getOrdersByStatus(
        @Path("status") open: Int
    ): Response<ApiResponse<List<Order>>>

    @GET("orders/total/by-status/{status}")
    suspend fun getTotalOrdersByStatus(
        @Path("status") open: Int
    ): Response<TotalApiResponse<Int>>

    @GET("orders/total-amount/{status}")
    suspend fun getTotalAmountByStatus(
        @Path("status") open:Int
    ): Response<TotalApiResponse<Double>>

    @DELETE("order/{orderId}")
    suspend fun deleteOrder(
        @Path("orderId") orderId: Int,
        @Body order: Order
    ): Response<ApiResponse<Unit>>

    @POST("orderItems")
    suspend fun insertOrderItem(
        @Body orderItem: OrderItem
    ): Response<ApiResponse<OrderItem>>

    @DELETE("orderItems/{orderId}/{productId}")
    suspend fun deleteOrderItem(
        @Path("orderId") orderId: Int,
        @Path("productId") productId: Int
    ): Response<ApiResponse<Unit>>

    @GET("orderItems/{orderId}")
    suspend fun getOrderItemsByOrderId(
        @Path("orderId") orderId: Int
    ) : Response<ApiResponse<List<OrderItem>>>

    @SuppressLint("NewApi")
    private fun getCurrentDate(): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return java.time.LocalDate.now().format(formatter)
    }
}