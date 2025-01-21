package com.example.myapplication.data.api

import com.example.myapplication.ui.models.Table
import retrofit2.Response
import retrofit2.http.GET

interface TableApiService {

    @GET("tables")
    suspend fun getTables(): Response<ApiResponse<List<Table>>>
}