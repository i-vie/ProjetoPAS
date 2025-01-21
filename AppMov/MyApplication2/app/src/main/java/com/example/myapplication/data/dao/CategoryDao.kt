package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: CategoryEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)
    @Query("SELECT * FROM category WHERE active = 1")
    suspend fun getAllCategories(): List<CategoryEntity>
    @Delete
    suspend fun delete(category: CategoryEntity)
    @Update
    suspend fun update(category: CategoryEntity)
    @Query("SELECT * FROM category WHERE id = :selectedCategoryID")
    suspend fun getCategoryById(selectedCategoryID: Int): CategoryEntity?
    @Query("UPDATE category SET active = 0 WHERE id = :categoryId")
    suspend fun deactivateCategory(categoryId: Int)
}
