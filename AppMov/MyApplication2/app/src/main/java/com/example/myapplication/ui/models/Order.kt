package com.example.myapplication.ui.models

import androidx.compose.runtime.mutableStateListOf
import com.google.gson.annotations.SerializedName

data class Order (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("open") val open: Int,
    @SerializedName("employeeId") val employeeId: Int,
    @SerializedName("table_id") val tableNr: Int,
    @SerializedName("total") val total: Double,
    @SerializedName("created_at") val dateCreated: String? = null

)



