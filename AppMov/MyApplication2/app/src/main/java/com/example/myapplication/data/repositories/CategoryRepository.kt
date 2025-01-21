package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.api.CategoryApiService
import com.example.myapplication.data.api.RetrofitClient.getRetrofitInstance

import com.example.myapplication.ui.models.Category


class CategoryRepository(
) {
    private val retrofit = getRetrofitInstance()
    private val apiService: CategoryApiService = retrofit.create(CategoryApiService::class.java)

    companion object {
        private const val TAG = "CategoryRepository"
    }

    suspend fun getCategories(): List<Category> {
        val response = apiService.getCategories()

        if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getCategories Response: ${apiResponse.toString()}")
            return apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getCategories Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    suspend fun updateCategory(category: Category): Boolean {
        val response = apiService.updateCategory(category.id, category)
        if (response.isSuccessful) {
            Log.d(TAG, "updateCategory Successful: Category ID ${category.id}")
        } else {
            Log.e(TAG, "updateCategory Failed: ${response.errorBody()?.string()}")
        }
        return response.isSuccessful
    }

    suspend fun getCategoryById(id: Int): Category? {
        val response = apiService.getCategoryById(id)

        if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getCategoryById Response: ${apiResponse.toString()}")
            return apiResponse?.data
        } else {
            Log.e(TAG, "getCategoryById Error: ${response.errorBody()?.string()}")
            return null
        }
    }

    suspend fun insert(category: Category): Int? {
        val response = apiService.insert(category)

        if (response.isSuccessful) {
            val createdCategory = response.body()?.data
            Log.d(TAG, "insert Successful: Created Category ID ${createdCategory?.id}")
            return createdCategory?.id
        } else {
            Log.e(TAG, "insert Failed: ${response.errorBody()?.string()}")
            return null
        }
    }
}
