package com.example.habitcheck

import android.app.Application
import com.example.habitcheck.data.HabitDatabase
import com.example.habitcheck.data.repository.HabitRepository

class HabitApplication : Application() {

    val database: HabitDatabase by lazy {
        HabitDatabase.getDatabase(this)
    }

    val repository: HabitRepository by lazy {
        HabitRepository(database.habitDao())
    }
}





