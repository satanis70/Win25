package com.example.win25.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.win25.model.QuizModel

@Database(entities = [QuizModel::class], version = 1)
abstract class RoomDatabase:RoomDatabase() {
    abstract fun betDao():Dao
    companion object{
        @Volatile
        private var INSTANCE: RoomDatabase? = null
        fun getDatabase(context: Context): RoomDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "database"
                ).build()
            }
        }
    }
}