package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.EmployeeEntity

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employee: EmployeeEntity)

    @Delete
    suspend fun delete(employee: EmployeeEntity)

    @Query("SELECT * FROM employee LIMIT 1")
    suspend fun getEmployee(): EmployeeEntity?

    /*
    @Update
    suspend fun update(employee: EmployeeEntity)

    @Query("SELECT * FROM employee WHERE username = :username OR email = :username LIMIT 1")
    suspend fun getEmployeeByUsernameOrEmail(username: String): EmployeeEntity?

    @Query("SELECT * FROM employee WHERE username = :username LIMIT 1")
    suspend fun getEmployeeByUsername(username: String): EmployeeEntity?

    @Query("SELECT * FROM employee WHERE active = 1")
    suspend fun getActiveEmployees(): List<EmployeeEntity>

    @Query("UPDATE employee SET active = 0 WHERE id = :employeeId")
    suspend fun deactivateEmployee(employeeId: Int)

    @Query("SELECT COUNT(*) FROM Employee WHERE username = :username")
    suspend fun usernameExists(username: String): Boolean

    @Query("SELECT COUNT(*) FROM Employee WHERE email = :email")
    suspend fun emailExists(email: String): Boolean

    @Query("UPDATE employee SET admin = :isAdmin WHERE id = :employeeId")
    suspend fun updateEmployeeAdminStatus(employeeId: Int, isAdmin: Boolean)

    @Query("UPDATE employee SET password = :newPassword WHERE id = :employeeId")
    suspend fun updatePasswordById(employeeId: Int, newPassword: String)

     */
}