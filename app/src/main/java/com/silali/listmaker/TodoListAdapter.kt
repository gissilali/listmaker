package com.silali.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(private val todoLists: ArrayList<TaskList>) : RecyclerView.Adapter<TodoListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder, parent, false)
        return TodoListViewHolder(view)
    }

    fun addList(list: TaskList) {
        todoLists.add(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(todoLists[position].name)
    }
}