package com.silali.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private val todoLists: ArrayList<TaskList>, val clickListener: TodoListClickListener) : RecyclerView.Adapter<TaskListViewHolder>() {
    interface TodoListClickListener {
        fun itemClicked(list: TaskList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view_holder, parent, false)
        return TaskListViewHolder(view)
    }

    fun addList(list: TaskList) {
        todoLists.add(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val currentListItem = todoLists[position];
        holder.bind(currentListItem.name)
        holder.itemView.setOnClickListener {
            clickListener.itemClicked(currentListItem)
        }
    }
}