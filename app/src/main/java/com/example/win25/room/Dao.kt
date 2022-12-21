package com.example.win25.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.win25.model.BetModel

@androidx.room.Dao
interface Dao {
    @Query("SELECT * FROM tableRoom")
    fun getAll():List<BetModel>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(betModel: BetModel)
    @Delete()
    suspend fun delete(betModel: BetModel)
    @Query("DELETE FROM tableRoom")
    suspend fun deleteDatabase()
    @Query("UPDATE tableRoom SET status=:status WHERE position LIKE:position")
    suspend fun update(status: String, position: Int)
    @Query("UPDATE tableRoom SET capital=:capital WHERE position LIKE:position")
    suspend fun updateCapital(capital:String, position: Int)
}