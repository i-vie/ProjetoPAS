package com.example.myapplication.ui.models

import com.google.gson.annotations.SerializedName

data class Table(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("seats")
    val seats: Int,

    @SerializedName("available")
    val active: Int = 1
)