package com.example.habitcheck.data.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitcheck.data.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit_table ORDER BY id ASC")
    fun getAllHabits(): Flow<List<HabitEntity>>  //Flow를 사용하여 LiveData/ViewModel과 쉽게 연결

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: HabitEntity){}

    @Update
    suspend fun update(habit: HabitEntity){}

    @Query("DELETE FROM habit_table")
    suspend fun deleteAll(){}

    @Query("DELETE FROM habit_table WHERE id = :habitId")
    suspend fun deleteHabitById(habitId: Int)


}