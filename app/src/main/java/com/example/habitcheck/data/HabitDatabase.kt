package com.example.habitcheck.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.data.entity.dao.HabitDao


@Database(entities = [HabitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()   //
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}