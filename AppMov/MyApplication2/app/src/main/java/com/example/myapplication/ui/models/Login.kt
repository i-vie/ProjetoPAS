package com.example.myapplication.ui.models

data class LoginRequest(
    val username: String? = null,
    val email: String? = null,
    val password: String
)