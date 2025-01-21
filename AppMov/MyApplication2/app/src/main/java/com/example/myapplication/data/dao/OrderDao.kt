package com.example.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.entity.OrderEntity

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: OrderEntity): Long

    @Query("SELECT * FROM orders ORDER BY open DESC")
    suspend fun getAllOrders(): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE orderNr = :orderNr")
    suspend fun getOrderById(orderNr: Int): OrderEntity?

    @Query("SELECT * FROM orders WHERE open = 1")
    suspend fun getOpenOrders(): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE employeeId = :employeeId ORDER BY open DESC")
    suspend fun getOrdersByEmployeeId(employeeId: Int): List<OrderEntity>

    @Delete
    suspend fun delete(order: OrderEntity)

    @Update
    suspend fun update(order: OrderEntity)

    @Query("UPDATE orders SET total = :newTotal WHERE orderNr = :orderNr")
    suspend fun updateOrderTotal(orderNr: Int, newTotal: Float)

    @Query("UPDATE orders SET tableNr = :newTable WHERE orderNr = :orderNr")
    suspend fun updateOrderTable(orderNr: Int, newTable: Int)

    @Query("SELECT COUNT(*) FROM orders")
    suspend fun totalOrders(): Int

    @Query("SELECT COUNT(*) FROM orders WHERE open = 1")
    suspend fun totalOpenOrders(): Int

    @Query("SELECT COUNT(*) FROM orders WHERE open = 0")
    suspend fun totalClosedOrders(): Int

    @Query("SELECT SUM(total) FROM orders WHERE open = 0")
    suspend fun totalClosedOrdersPrice(): Double

    @Query("UPDATE orders SET open = 0 WHERE orderNr = :orderNr")
    suspend fun closeOrder(orderNr: Int)

    @Query("SELECT COUNT(*) FROM orders WHERE date >= :startOfDay AND date < :endOfDay")
    suspend fun totalOrdersToday(startOfDay: Long, endOfDay: Long): Int

    @Query("SELECT COUNT(*) FROM orders WHERE open = 1 AND date >= :startOfDay AND date < :endOfDay")
    suspend fun totalOpenOrdersToday(startOfDay: Long, endOfDay: Long): Int

    @Query("SELECT COUNT(*) FROM orders WHERE open = 0 AND date >= :startOfDay AND date < :endOfDay")
    suspend fun totalClosedOrdersToday(startOfDay: Long, endOfDay: Long): Int

    @Query("SELECT SUM(total) FROM orders WHERE open = 0 AND date >= :startOfDay AND date < :endOfDay")
    suspend fun totalOrdersPriceToday(startOfDay: Long, endOfDay: Long): Double
}