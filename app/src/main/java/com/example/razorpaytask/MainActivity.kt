package com.example.razorpaytask

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.razorpaytask.api.RetrofitClient
import com.example.razorpaytask.databinding.ActivityMainBinding
import com.example.razorpaytask.databinding.AddEditTaskBinding
import com.example.razorpaytask.repository.TaskRepository
import com.example.razorpaytask.roomdb.TaskDatabase
import com.example.razorpaytask.roomdb.TaskEntity
import com.example.razorpaytask.viewmodel.TaskViewModel
import com.example.razorpaytask.viewmodel.TaskViewModelFactory
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    lateinit var binding: ActivityMainBinding
    var taskList = ArrayList<TaskEntity>()
    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize dependencies
        val taskDao = TaskDatabase.getInstance(applicationContext).taskDao()

        // Initialize Retrofit service
        val taskApiService = RetrofitClient.apiService

        // Initialize Repository
        val repository = TaskRepository(taskApiService, taskDao)

        // Initialize ViewModel using the custom factory
        val viewModelFactory = TaskViewModelFactory(repository)


        binding.addTask.setOnClickListener {
            addEditTaskDailog("Add", "", 0)
        }

        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
        taskViewModel.fetchTasksFromApi()


//        taskAdapter = TaskAdapter(ArrayList()) { task ->
//            Toast.makeText(this,"Task marked completed",Toast.LENGTH_SHORT).show()
//            taskViewModel.updateTask(task)
//        }

        taskAdapter = TaskAdapter(ArrayList(), object : AdapterCallback {
            override fun onEdit(position: Int, text: String) {
                addEditTaskDailog("Edit", text, position)
            }

            override fun onComplete(id: TaskEntity) {
                firebaseAnalytics("Task Completed")

                Toast.makeText(this@MainActivity, "Task marked completed", Toast.LENGTH_SHORT)
                    .show()
                taskViewModel.updateTask(id)
            }
        })



        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = taskAdapter

        taskViewModel.tasks.observe(this, Observer { tasks ->
            taskList.clear()
            taskList.addAll(tasks)
            taskAdapter.submitList(ArrayList(tasks))

        })


    }

    fun addEditTaskDailog(type: String, title: String, position: Int) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        val inflater: LayoutInflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding = AddEditTaskBinding.inflate(inflater)
        dialog.setContentView(binding.root)

        val window = dialog.window
        if (type == "Edit") {
            binding.tv1.text = "Edit Task"
            binding.tv3.setText(title)
            binding.tvOk.text = type
        } else {
            binding.tv1.text = "Add Task"
            binding.tvOk.text = type
        }
        binding.tvOk.setOnClickListener {

            if (binding.tv3.text.toString().isEmpty()) {

                Toast.makeText(this@MainActivity, "Please enter your task title", Toast.LENGTH_SHORT)
                    .show()
            }
            else if (binding.tv3.text.toString().length<10) {

                Toast.makeText(this@MainActivity, "Please enter your task title in minimum 10 characters", Toast.LENGTH_SHORT)
                    .show()
            }else{
                if (type == "Edit") {
                    taskList[position].title = binding.tv3.text.toString()
                    taskViewModel.updateTask(taskList[position])
                    firebaseAnalytics("Task Edited")
                }else{
                    val task = TaskEntity(taskList.size,title = binding.tv3.text.toString(), completed = false)
                    taskViewModel.updateTask(task)
                    firebaseAnalytics("Task Added")

                }
                dialog.dismiss()
            }

        }
        dialog.show()
    }


    fun firebaseAnalytics(eventName: String) {
        val bundle = Bundle()
        bundle.putString("Vaibhav Events", eventName)
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}