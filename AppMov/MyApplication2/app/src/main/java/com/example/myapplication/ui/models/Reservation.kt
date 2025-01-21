package com.example.myapplication.ui.models

import com.google.gson.annotations.SerializedName

data class Reservation (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("employeeId") val employeeId: Int,
    @SerializedName("table_id") val tableNr: Int,
    @SerializedName("dateReservation") val dateReservation: String,
    @SerializedName("nrPeople") val nrPeople: Int,
    @SerializedName("comments") val comments: String = "",
    @SerializedName("created_at") val dateCreated: String? = null

)