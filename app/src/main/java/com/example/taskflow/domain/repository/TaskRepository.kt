package com.example.taskflow.domain.repository

import com.example.taskflow.data.local.entity.TaskEntity
import com.example.taskflow.domain.model.Holiday
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun getTaskById(id: Int): TaskEntity?
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun getNationalHolidays(): List<Holiday>
}