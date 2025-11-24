package com.example.habitcheck.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val frequency: String = "Daily", // 예: "Daily", "Weekly"
    val isCompletedToday: Boolean = false
)