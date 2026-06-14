package com.example.taskflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.data.local.entity.TaskEntity
import com.example.taskflow.domain.model.Holiday
import com.example.taskflow.domain.model.Task
import com.example.taskflow.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks.asStateFlow()

    private val _holidays = MutableStateFlow<List<Holiday>>(emptyList())
    val holidays: StateFlow<List<Holiday>> = _holidays.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask.asStateFlow()

    init {
        observeTasks()
        fetchNationalHolidays()
    }

    private fun mapEntityToDomain(entity: TaskEntity): Task {
        return Task(
            id = entity.id.toString(),
            title = entity.title,
            description = entity.description,
            category = entity.category,
            deadline = entity.deadline,
            priority = entity.priorityLevel,
            isCompleted = entity.isCompleted
        )
    }

    private fun mapDomainToEntity(domain: Task): TaskEntity {
        return TaskEntity(
            id = domain.id.toIntOrNull() ?: 0,
            title = domain.title,
            description = domain.description,
            category = domain.category,
            deadline = domain.deadline,
            priorityLevel = domain.priority,
            isCompleted = domain.isCompleted
        )
    }

    // 1. BROWSE
    private fun observeTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { entities ->
                _allTasks.value = entities.map { mapEntityToDomain(it) }
            }
        }
    }

    // 2. REMOTE API
    fun fetchNationalHolidays() {
        viewModelScope.launch {
            _holidays.value = repository.getNationalHolidays()
        }
    }

    // 3. READ
    fun getTaskById(id: String) {
        viewModelScope.launch {
            val taskIdInt = id.toIntOrNull() ?: 0
            val entity = repository.getTaskById(taskIdInt)
            _selectedTask.value = entity?.let { mapEntityToDomain(it) }
        }
    }

    // 4. ADD
    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(mapDomainToEntity(task))
        }
    }
    fun insertTask(
        title: String,
        description: String,
        category: String,
        deadline: String,
        priorityLevel: String
    ) {
        viewModelScope.launch {
            val newEntity = TaskEntity(
                title = title,
                description = description,
                category = category,
                deadline = deadline,
                priorityLevel = priorityLevel
            )
            repository.insertTask(newEntity)
        }
    }

    // 5. EDIT
    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(mapDomainToEntity(task))
        }
    }

    // 6. DELETE
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(mapDomainToEntity(task))
        }
    }

    fun clearSelectedTask() {
        _selectedTask.value = null
    }
}