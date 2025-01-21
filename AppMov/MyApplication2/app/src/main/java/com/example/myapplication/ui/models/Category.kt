
package com.example.myapplication.ui.models

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String,

    @SerializedName("active")
    val active: Int = 1
)



