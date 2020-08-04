package com.silali.listmaker.ui.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silali.listmaker.R
import com.silali.listmaker.TaskList

class TaskListAdapter(private val todoLists: ArrayList<TaskList>, private val clickListener: TodoListClickListener) : RecyclerView.Adapter<TaskListViewHolder>() {
    interface TodoListClickListener {
        fun itemClicked(list: TaskList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view_holder, parent, false)
        return TaskListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val currentListItem = todoLists[position]
        holder.bind(currentListItem.name)
        holder.itemView.setOnClickListener {
            clickListener.itemClicked(currentListItem)
        }
    }
}