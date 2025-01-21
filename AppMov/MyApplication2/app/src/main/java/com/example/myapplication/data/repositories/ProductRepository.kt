package com.example.myapplication.data.repositories

import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.data.api.CategoryApiService
import com.example.myapplication.data.api.ProductApiService
import com.example.myapplication.data.api.RetrofitClient.getRetrofitInstance
import com.example.myapplication.ui.models.Product

class ProductRepository(
) {

    private val retrofit = getRetrofitInstance()
    private val apiService: ProductApiService = retrofit.create(ProductApiService::class.java)

    suspend fun getProductsFromCategory(categoryId: Int): List<Product> {
        val response = apiService.getProductsFromCategory(categoryId)

        if (response.isSuccessful) {
            val apiResponse = response.body()

            return apiResponse?.data ?: emptyList()
        } else {
            return emptyList()
        }
    }

    suspend fun updateProduct(product: Product) : Boolean {
        val response = apiService.updateProduct(product.id, product)

        return response.isSuccessful
    }

    suspend fun createProduct(product: Product) : Int? {

        val response = apiService.createProduct(product)
        if (response.isSuccessful) {
            val createdProduct = response.body()?.data
            return createdProduct?.id
        } else {
            return null
        }
    }

    suspend fun getActiveProducts(): List<Product> {
        val response = apiService.getActiveProducts()

        if (response.isSuccessful) {
            val apiResponse = response.body()

            return apiResponse?.data ?: emptyList()
        } else {
            return emptyList()
        }
    }

    suspend fun getProductById(productId: Int): Product? {
        val response = apiService.getProductById(productId)

        if (response.isSuccessful) {
            val apiResponse = response.body()

            return apiResponse?.data
        } else {
            return null
        }
    }

}