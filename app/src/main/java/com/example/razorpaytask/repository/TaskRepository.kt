package com.example.razorpaytask.repository

import com.example.razorpaytask.api.TaskApiService
import com.example.razorpaytask.data.Task
import com.example.razorpaytask.roomdb.TaskDao
import com.example.razorpaytask.roomdb.TaskEntity

class TaskRepository(
    private val taskApiService: TaskApiService,
    private val taskDao: TaskDao
) {

    suspend fun fetchTasksFromApi(): List<Task> {
        return taskApiService.getTasks()
    }

    suspend fun saveTasksToLocalDatabase(tasks: List<Task>) {
        tasks.forEach { task ->
            taskDao.insert(TaskEntity(task.id, task.title, task.completed))
        }
    }

    suspend fun getAllTasksFromDb(): List<TaskEntity> {
        return taskDao.getAllTasks()
    }
    suspend fun updateTask(task: TaskEntity) {
        taskDao.update(task)
    }
}