package com.example.razorpaytask

import com.example.razorpaytask.roomdb.TaskEntity

interface AdapterCallback {
    fun onEdit(position:Int,text: String)
    fun onComplete(id:TaskEntity)
}