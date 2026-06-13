package com.example.taskflow.data.repository

import com.example.taskflow.domain.repository.TaskRepository
import com.example.taskflow.data.local.TaskDao
import com.example.taskflow.domain.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun getTaskById(id: String): Task? {
        return taskDao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}