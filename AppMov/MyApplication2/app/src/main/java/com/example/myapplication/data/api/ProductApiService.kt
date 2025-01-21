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

interface ProductApiService {


    @GET("products")
    suspend fun getProducts(): Response<ApiResponse<List<Product>>>

    @GET("products/category/{categoryId}")
    suspend fun getProductsFromCategory(@Path("categoryId") categoryId: Int): Response<ApiResponse<List<Product>>>

    @PUT("products/{product}")
    suspend fun updateProduct(
        @Path("product") productId: Int,
        @Body product: Product): Response<ApiResponse<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ApiResponse<Product>>

    @POST("products")
    suspend fun createProduct(
        @Body product: Product
    ) : Response<ApiResponse<Product>>

    @GET("products/status/{status}")
    suspend fun getActiveProducts(@Path("status") status: Int = 1): Response<ApiResponse<List<Product>>>


}