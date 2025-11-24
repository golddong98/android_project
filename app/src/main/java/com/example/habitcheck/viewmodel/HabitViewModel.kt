package com.example.habitcheck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.data.repository.HabitRepository
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository): ViewModel() {

    // Repository의 Flow를 LiveData로 변환하여 UI에 노출
    val allHabits: LiveData<List<HabitEntity>> = repository.allHabits.asLiveData()

    // 새로운 습관 추가 (비동기 처리)
    fun insert(habit: HabitEntity) = viewModelScope.launch {
        repository.insert(habit)
    }

    // 습관 업데이트 (비동기 처리)
    fun update(habit: HabitEntity) = viewModelScope.launch {
        repository.update(habit)
    }

    // 습관 삭제 (비동기 처리)
    fun delete(id: Int) = viewModelScope.launch {
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