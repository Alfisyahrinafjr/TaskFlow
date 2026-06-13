package com.example.taskflow.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val deadline: String,
    val isCompleted: Boolean = false
)