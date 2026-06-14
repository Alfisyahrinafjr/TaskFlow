package com.example.taskflow.data.local

import androidx.room.*
import com.example.taskflow.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY deadline ASC")
    fun getAllTasks(): Flow<List<TaskEntity>> // BROWSE

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity? // READ

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity) // ADD

    @Update
    suspend fun updateTask(task: TaskEntity) // EDIT

    @Delete
    suspend fun deleteTask(task: TaskEntity) // DELETE
}