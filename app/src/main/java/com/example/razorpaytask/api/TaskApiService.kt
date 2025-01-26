package com.example.razorpaytask.api

import com.example.razorpaytask.data.Task
import retrofit2.http.GET

interface   TaskApiService {
    @GET("todos")
    suspend fun getTasks(): List<Task>
}