package com.example.myapplication.ui.models

import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("description")
    val description: String = "",

    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("active")
    val active: Int
)