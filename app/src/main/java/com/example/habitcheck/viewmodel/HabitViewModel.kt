package com.example.habitcheck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.data.repository.HabitRepository
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository): ViewModel() {


    val allHabits: LiveData<List<HabitEntity>> = repository.allHabits

    fun saveHabit(name: String, description: String?) = viewModelScope.launch{
        val habitEntity = HabitEntity(
            name = name,
            description = description?: "good luck!"
        )
        repository.insert(habitEntity)
    }


    fun updateHabit(habit: HabitEntity) = viewModelScope.launch {
        repository.update(habit)
    }
    fun deleteHabitById (id: List<Int>) = viewModelScope.launch {
        repository.deleteHabitById(id)
    }
}

// ViewModel Factory (의존성 주입을 위한 보일러플레이트)
class HabitViewModelFactory(private val repository: HabitRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}