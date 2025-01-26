package com.example.razorpaytask.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.razorpaytask.repository.TaskRepository
import com.example.razorpaytask.roomdb.TaskEntity
import kotlinx.coroutines.launch
import android.util.Log

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val tasks = MutableLiveData<List<TaskEntity>>()

    fun fetchTasksFromApi() {
        viewModelScope.launch {
            try {
                val tasksFromApi = taskRepository.fetchTasksFromApi()
                taskRepository.saveTasksToLocalDatabase(tasksFromApi)
                fetchTasksFromDatabase()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error fetching tasks from API", e)
            }
        }
    }

    fun fetchTasksFromDatabase() {
        viewModelScope.launch {
            val tasksFromDb = taskRepository.getAllTasksFromDb()
            tasks.postValue(tasksFromDb)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
            fetchTasksFromDatabase()
        }
    }

    fun trackTaskAdded(taskId: Int) {
        val bundle = Bundle().apply {
            putInt("task_id", taskId)
        }
        // firebaseAnalytics.logEvent("task_added", bundle)
    }

    fun triggerCrash() {
        // FirebaseCrashlytics.getInstance().log("Simulating crash")
        throw RuntimeException("Test Crash")
    }
}
