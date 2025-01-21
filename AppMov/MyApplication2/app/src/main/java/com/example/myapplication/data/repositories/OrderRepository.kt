package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.api.OrderApiService
import com.example.myapplication.data.api.RetrofitClient.getRetrofitInstance
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.models.OrderItem

class OrderRepository {
    private val retrofit = getRetrofitInstance()
    private val apiService: OrderApiService = retrofit.create(OrderApiService::class.java)

    companion object {
        private const val TAG = "OrderRepository"
    }

    suspend fun getAllOrders(): List<Order> {
        val response = apiService.getAllOrders()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getAllOrders Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getAllOrders Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getOrderById(id: Int): Order? {
        val response = apiService.getOrderById(id)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getOrderById Successful: ${apiResponse.toString()}")
            apiResponse?.data
        } else {
            Log.e(TAG, "getOrderById Failed: ${response.errorBody()?.string()}")
            null
        }
    }

    suspend fun getOrdersByEmployeeId(employeeId: Int): List<Order> {
        val response = apiService.getOrdersByEmployeeId(employeeId)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getOrdersByEmployeeId Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getOrdersByEmployeeId Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun createOrder(order: Order): Int? {
        return try {
            val response = apiService.createOrder(order)
            if (response.isSuccessful) {
                val createdOrder = response.body()?.data
                Log.d(TAG, "createOrder Successful: ${createdOrder.toString()}")
                createdOrder?.id
            } else {
                Log.e(TAG, "createOrder Failed: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "createOrder Exception: ${e.message}")
            null
        }
    }

    suspend fun updateOrder(order: Order): Boolean {
        val response = apiService.updateOrder(order.id, order)
        return if (response.isSuccessful) {
            Log.d(TAG, "updateOrder Successful")
            true
        } else {
            Log.e(TAG, "updateOrder Failed: ${response.errorBody()?.string()}")
            false
        }
    }

    suspend fun getOrdersToday(): List<Order> {
        val response = apiService.getOrdersToday()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getOrdersToday Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getOrdersToday Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getTotalOrdersToday(): Int {
        val response = apiService.getTotalOrdersToday()
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getTotalOrdersToday Successful: $apiResponse")
            apiResponse ?.total ?: 0
        } else {
            Log.e(TAG, "getTotalOrdersToday Failed: ${response.errorBody()?.string()}")
            0
        }
    }

    suspend fun getOrdersByStatus(isOpen: Int): List<Order> {
        val response = apiService.getOrdersByStatus(isOpen)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getOrdersByStatus Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getOrdersByStatus Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getTotalOrdersByStatus(isOpen: Int): Int {
        val response = apiService.getTotalOrdersByStatus(isOpen)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getTotalOrdersByStatus Successful: $apiResponse")
            apiResponse?.total ?: 0
        } else {
            Log.e(TAG, "getTotalOrdersByStatus Failed: ${response.errorBody()?.string()}")
            0
        }
    }

    suspend fun getTotalAmountByStatus(isOpen: Int): Double {
        val response = apiService.getTotalAmountByStatus(isOpen)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getTotalAmountByStatus Successful: $apiResponse")
            apiResponse?.total ?: 0.0
        } else {
            Log.e(TAG, "getTotalAmountByStatus Failed: ${response.errorBody()?.string()}")
            0.0
        }
    }

    suspend fun deleteOrder(order: Order): Boolean {
        return try {
            val response = apiService.deleteOrder(order.id, order)
            if (response.isSuccessful) {
                Log.d(TAG, "deleteOrder Successful")
                true
            } else {
                Log.e(TAG, "deleteOrder Failed: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteOrder Exception: ${e.message}")
            false
        }
    }

    suspend fun insertOrderItem(orderItem: OrderItem) {
        try {
            apiService.insertOrderItem(orderItem)
            Log.d(TAG, "insertOrderItem Successful: $orderItem")
        } catch (e: Exception) {
            Log.e(TAG, "insertOrderItem Exception: ${e.message}")
        }
    }

    suspend fun deleteOrderItem(orderItem: OrderItem): Boolean {
        return try {
            val response = apiService.deleteOrderItem(orderItem.orderId, orderItem.productId)
            if (response.isSuccessful) {
                Log.d(TAG, "deleteOrderItem Successful: $orderItem")
                true
            } else {
                Log.e(TAG, "deleteOrderItem Failed: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteOrderItem Exception: ${e.message}")
            false
        }
    }

    suspend fun getOrderItemsByOrderId(orderId: Int): List<OrderItem> {
        val response = apiService.getOrderItemsByOrderId(orderId)
        return if (response.isSuccessful) {
            val apiResponse = response.body()
            Log.d(TAG, "getOrderItemsByOrderId Successful: ${apiResponse.toString()}")
            apiResponse?.data ?: emptyList()
        } else {
            Log.e(TAG, "getOrderItemsByOrderId Failed: ${response.errorBody()?.string()}")
            emptyList()
        }
    }
}
