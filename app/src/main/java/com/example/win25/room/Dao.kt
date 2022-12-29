package com.example.win25.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.win25.model.BetModel

@androidx.room.Dao
interface Dao {
    @Query("SELECT * FROM tableRoom")
    fun getAll():LiveData<List<BetModel>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(betModel: BetModel)
    @Delete()
    suspend fun delete(betModel: BetModel)
    @Query("DELETE FROM tableRoom")
    suspend fun deleteDatabase()
    @Query("UPDATE tableRoom SET betStatus=:betStatus WHERE betPosition LIKE:betPosition")
    suspend fun update(betStatus: String, betPosition: Int)
    @Query("UPDATE tableRoom SET bankCapital=:bankCapital WHERE betPosition LIKE:betPosition")
    suspend fun updateCapital(bankCapital:String, betPosition: Int)
}