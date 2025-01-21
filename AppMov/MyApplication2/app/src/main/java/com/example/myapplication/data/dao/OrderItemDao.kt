package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.OrderItemEntity

@Dao
interface OrderItemDao {
    @Query("""
        SELECT COUNT(*) 
        FROM order_item 
        WHERE orderId = :orderId AND productId = :productId
    """)
    suspend fun getItemCount(orderId: Int, productId: Int): Int

    @Query("SELECT * FROM order_item")
    suspend fun getAllOrderItems(): List<OrderItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(orderItem: OrderItemEntity)

    @Update
    suspend fun update(orderItem: OrderItemEntity)

    @Query("SELECT * FROM order_item WHERE orderId = :orderId")
    suspend fun getOrderItemsByOrderId(orderId: Int): List<OrderItemEntity>

    @Query("""
        SELECT productId, quantity 
        FROM order_item 
        WHERE orderId = :orderId
    """)
    fun getProductIdsAndQuantitiesByOrderId(orderId: Int): List<ProductQuantity>

    @Delete
    suspend fun delete(orderItem: OrderItemEntity)

    @Query("DELETE FROM order_item WHERE orderId = :orderNr")
    suspend fun deleteOrderItems(orderNr: Int)

}

//class to get the productid and quantity of each order item
data class ProductQuantity(
    val productId: Int,
    val quantity: Int
)