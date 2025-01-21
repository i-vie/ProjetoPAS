package com.example.myapplication.data.api

import com.example.myapplication.ui.models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<EmployeeResponse>
}