package com.example.myapplication.data.api

import com.example.myapplication.ui.models.Category
import com.example.myapplication.ui.models.LoginRequest
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.models.OrderItem
import com.example.myapplication.ui.models.Product
import com.example.myapplication.ui.models.Reservation
import com.example.myapplication.ui.models.Table
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryApiService {

    @GET("categories")
    suspend fun getCategories(): Response<ApiResponse<List<Category>>>

    @PUT("categories/{id}")
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body category: Category
    ): Response<ApiResponse<Category>>

    @GET("categories/{id}")
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): Response<ApiResponse<Category>>

    @POST("categories")
    suspend fun insert(
        @Body category: Category
    ) : Response<ApiResponse<Category>>
}