package com.example.myapplication.data.repositories

import com.example.myapplication.data.api.RetrofitClient.getRetrofitInstance
import com.example.myapplication.data.api.TableApiService
import com.example.myapplication.ui.models.Table
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class TableRepository (
    ) {
        private val retrofit = getRetrofitInstance()
        private val apiService: TableApiService = retrofit.create(TableApiService::class.java)

        private val _selectedTable = MutableStateFlow<Int>(0)
        val selectedTable : StateFlow<Int> = _selectedTable

        suspend fun getTables(): List<Table> {
            val response = apiService.getTables()

            if (response.isSuccessful) {
                val apiResponse = response.body()

                return apiResponse?.data ?: emptyList()
            } else {
                return emptyList()
            }
        }


    }
