package com.silali.listmaker.ui.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silali.listmaker.R
import com.silali.listmaker.data.model.TaskList

class TaskAdapter(private val taskList: TaskList) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view_holder, parent, false)

        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList.tasks[position])
    }
}