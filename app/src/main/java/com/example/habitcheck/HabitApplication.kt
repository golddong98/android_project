package com.example.habitcheck

import android.app.Application
import com.example.habitcheck.data.HabitDatabase
import com.example.habitcheck.data.repository.HabitRepository


class HabitApplication: Application() {
    // 지연 초기화(lazy)를 사용하여 필요한 시점에만 데이터베이스를 생성
    val database by lazy { HabitDatabase.getDatabase(this) }
    val repository by lazy { HabitRepository(database.habitDao()) }
}

// AndroidManifest.xml의 <application> 태그에 name=".HabitApplication" 추가 필요