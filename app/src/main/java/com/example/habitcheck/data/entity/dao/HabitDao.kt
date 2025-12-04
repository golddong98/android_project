package com.example.habitcheck.data.entity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitcheck.data.entity.HabitEntity

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits ORDER BY id ASC")
    fun getAllHabits(): LiveData<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Int): LiveData<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: HabitEntity)


    @Query("""
    UPDATE habits
    SET
      name = COALESCE(:name, name),
      description = COALESCE(:description, description)
    WHERE id = :id
    """)
    suspend fun updateHabit(id: Int, name: String, description: String)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

    @Query("DELETE FROM habits WHERE id IN (:habitId)")
    suspend fun deleteHabitById(habitId: List<Int>)


}