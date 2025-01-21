package com.example.myapplication.ui.models

import com.google.gson.annotations.SerializedName

class OrderItem (
    @SerializedName("orderId")
    var orderId: Int,

    @SerializedName("productId")
    val productId: Int,

    @SerializedName("quantity")
    var quantity: Int
)
