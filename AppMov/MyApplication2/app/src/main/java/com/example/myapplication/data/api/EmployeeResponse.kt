package com.example.myapplication.data.api

import com.example.myapplication.ui.models.Employee
import com.google.gson.annotations.SerializedName

class EmployeeResponse {
    @SerializedName("employee")
    lateinit var employee: Employee
}