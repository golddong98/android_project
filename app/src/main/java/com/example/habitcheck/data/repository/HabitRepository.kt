package com.example.habitcheck.data.repository

import androidx.lifecycle.LiveData
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.data.entity.dao.HabitDao

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: LiveData<List<HabitEntity>> = habitDao.getAllHabits()

    suspend fun insert(habit: HabitEntity){
        habitDao.insert(habit)
    }

    suspend fun update(habit: HabitEntity) {
        habitDao.update(habit)
    }

    suspend fun deleteHabitById(id: List<Int>){
        habitDao.deleteHabitById(id)
    }
}