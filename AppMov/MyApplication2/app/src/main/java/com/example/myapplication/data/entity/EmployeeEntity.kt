package com.example.myapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val name: String,
        val username: String,
        val email: String,
        val admin: Int
)