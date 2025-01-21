package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.dao.CategoryDao
import com.example.myapplication.data.dao.EmployeeDao
import com.example.myapplication.data.dao.OrderDao
import com.example.myapplication.data.dao.OrderItemDao
import com.example.myapplication.data.dao.ProductDao
import com.example.myapplication.data.dao.ReservationDao
import com.example.myapplication.data.dao.TableCountDao
import com.example.myapplication.data.entity.CategoryEntity
import com.example.myapplication.data.entity.EmployeeEntity
import com.example.myapplication.data.entity.OrderEntity
import com.example.myapplication.data.entity.OrderItemEntity
import com.example.myapplication.data.entity.ProductEntity
import com.example.myapplication.data.entity.TableCount
import com.example.myapplication.data.entity.Reservation



@Database(entities =
[
    EmployeeEntity::class,
],
    version = 21, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gourmetmanager_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
