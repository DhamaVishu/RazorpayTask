package com.example.razorpaytask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.razorpaytask.databinding.TaskItemBinding
import com.example.razorpaytask.roomdb.TaskEntity


class TaskAdapter(private val list: ArrayList<TaskEntity>, private val listener: AdapterCallback) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = list[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = list.size

    fun submitList(tasks: ArrayList<TaskEntity>) {
        list.clear()
        list.addAll(tasks)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskEntity) {
            binding.taskTitle.text = task.title
            binding.completedCheckBox.isChecked = task.completed

            binding.edit.setOnClickListener {
                listener.onEdit(adapterPosition,task.title)
            }

            binding.completedCheckBox.setOnClickListener {

                if (!task.completed) {
                    task.completed = true

                    listener.onComplete(task)

                }else{
                    task.completed = true
                    binding.completedCheckBox.isChecked = task.completed
//                    onTaskCompleted(task)
                }
            }

//            binding.completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
//                task.completed = isChecked
//                onTaskCompleted(task)
//            }
        }
    }
}
