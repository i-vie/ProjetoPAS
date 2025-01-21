package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.api.LoginApiService
import com.example.myapplication.data.api.RetrofitClient.getRetrofitInstance
import com.example.myapplication.ui.models.Employee
import com.example.myapplication.ui.models.LoginRequest

class EmployeeRepository {

    private val retrofit = getRetrofitInstance()
    private val apiService: LoginApiService = retrofit.create(LoginApiService::class.java)

    companion object {
        private const val TAG = "EmployeeRepository"
    }

    private var loggedInEmployee: Employee? = null

    suspend fun login(loginRequest: LoginRequest): Employee? {
        val response = apiService.login(loginRequest)

        if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "login Successful: ${apiResponse.toString()}")
            loggedInEmployee = apiResponse?.employee
            return loggedInEmployee
        } else {
            Log.e(TAG, "login Failed: ${response.errorBody()?.string()}")
            return null
        }
    }
}
