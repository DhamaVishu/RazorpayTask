package com.example.razorpaytask.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey val id: Int,
    var title: String,
    var completed: Boolean
)