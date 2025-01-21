package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: ProductEntity)

    @Query("SELECT * FROM product WHERE categoryId = :categoryId AND active = 1")
    suspend fun getProductsFromCategory(categoryId: Int): List<ProductEntity>

    @Query("SELECT * FROM product WHERE id = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Delete
    suspend fun delete(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Query("SELECT * FROM product WHERE active = 1")
    suspend fun getActiveProducts(): List<ProductEntity>

    @Query("UPDATE product SET active = 0 WHERE id = :productId")
    suspend fun deactivateProduct(productId: Int)

}