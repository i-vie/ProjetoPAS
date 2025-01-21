package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.TableCount

@Dao
interface TableCountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tableCount: TableCount)

    @Query("SELECT totalTables FROM table_count WHERE id = 1")
    suspend fun getTotalTables(): Int

}