package com.example.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.entity.Reservation

@Dao
interface ReservationDao {
    @Insert
    suspend fun insert(reservation: Reservation): Long

    @Query("SELECT * FROM reservation")
    suspend fun getAllReservations() : List<Reservation>

    //all reservations for a specific day, ordered by time
    @Query("SELECT * FROM reservation WHERE dateReservation BETWEEN :startOfDay AND :endOfDay ORDER BY dateReservation ASC")
    suspend fun getReservationsByDay(startOfDay: Long, endOfDay: Long): List<Reservation>

    @Query("SELECT * FROM reservation WHERE id = :reservationId")
    suspend fun getReservationById(reservationId: Int): Reservation?

    @Query("SELECT * FROM reservation WHERE dateReservation BETWEEN :currentTimestamp AND :endOfDay ORDER BY dateReservation ASC")
    suspend fun getTodayOpenReservations(currentTimestamp: Long = System.currentTimeMillis(), endOfDay: Long): List<Reservation>

    @Query("SELECT * FROM reservation WHERE dateReservation >= :currentTimestamp ORDER BY dateReservation ASC")
    suspend fun getOpenReservations(currentTimestamp: Long = System.currentTimeMillis()): List<Reservation>

    @Query("SELECT * FROM reservation WHERE dateCreated BETWEEN :startOfDay AND :endOfDay ORDER BY dateReservation ASC")
    suspend fun getReservationsCreatedToday(startOfDay: Long, endOfDay: Long): List<Reservation>

    //reservations closer to the current time will be presented first
    @Query("SELECT * FROM reservation WHERE tableNr = :tableNr AND dateReservation >= :currentTimestamp ORDER BY dateReservation ASC")
    suspend fun getReservationsByTable(tableNr: String, currentTimestamp: Long = System.currentTimeMillis()): List<Reservation>

    @Delete
    suspend fun delete(reservation: Reservation)

    @Update
    suspend fun update(reservation: Reservation)

    @Query("UPDATE reservation SET tableNr = :newTable WHERE  id = :reservationId")
    suspend fun updateReservationTable(reservationId: Int, newTable: String)

    @Query("SELECT COUNT(*) FROM reservation WHERE dateReservation BETWEEN :currentTimestamp AND :endOfDay ORDER BY dateReservation ASC")
    suspend fun totalOpenTodayReservations(currentTimestamp: Long = System.currentTimeMillis(), endOfDay: Long): Int

    @Query("SELECT COUNT(*) FROM reservation WHERE dateReservation BETWEEN :startOfDay AND :endOfDay ORDER BY dateReservation ASC")
    suspend fun totalReservationsByDay(startOfDay: Long, endOfDay: Long): Int

    @Query("SELECT COUNT(*) FROM reservation WHERE dateReservation >= :currentTimestamp ORDER BY dateReservation ASC")
    suspend fun totalOpenReservations(currentTimestamp: Long = System.currentTimeMillis()): Int

    @Query("SELECT COUNT(*) FROM reservation WHERE dateCreated BETWEEN :startOfDay AND :endOfDay ORDER BY dateReservation ASC")
    suspend fun totalNewReservations(startOfDay: Long, endOfDay: Long): Int

}