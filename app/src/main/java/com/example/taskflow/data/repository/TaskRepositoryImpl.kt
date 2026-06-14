package com.example.taskflow.data.repository

import com.example.taskflow.domain.model.Holiday
import com.example.taskflow.data.remote.dto.HolidayResponse
import android.util.Log
import com.example.taskflow.data.local.TaskDao
import com.example.taskflow.data.local.entity.TaskEntity
import com.example.taskflow.data.remote.api.HolidayApiService
import com.example.taskflow.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val apiService: HolidayApiService
) : TaskRepository {

    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    override suspend fun getTaskById(id: Int): TaskEntity? = taskDao.getTaskById(id)

    override suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)

    override suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    override suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)

    override suspend fun getNationalHolidays(): List<Holiday> {
        return try {
            val remoteData: List<HolidayResponse> = apiService.getNationalHolidays()

            remoteData.map { dto ->
                Holiday(
                    name = dto.holidayName,
                    date = dto.holidayDate
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            listOf(
                Holiday(name = "Tahun Baru 2026 Masehi", date = "2026-01-01"),
                Holiday(name = "Tahun Baru Imlek 2577 Kongzili", date = "2026-02-17"),
                Holiday(name = "Hari Suci Nyepi (Tahun Baru Saka 1948)", date = "2026-03-19"),
                Holiday(name = "Wafat Yesus Kristus", date = "2026-04-03"),
                Holiday(name = "Hari Raya Idul Fitri 1447 H", date = "2026-03-20")
            )
        }
    }
}