package com.example.habitcheck.data.repository

import androidx.lifecycle.LiveData
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.data.entity.dao.HabitDao

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: LiveData<List<HabitEntity>> = habitDao.getAllHabits()

    fun getHabitById(id: Int): LiveData<HabitEntity> = habitDao.getHabitById(id)
    suspend fun insert(habit: HabitEntity){
        habitDao.insert(habit)
    }

    suspend fun updateHabit(id: Int, name: String, description: String) =
        habitDao.updateHabit(id, name, description)


    suspend fun deleteHabitById(id: List<Int>){
        habitDao.deleteHabitById(id)
    }
}