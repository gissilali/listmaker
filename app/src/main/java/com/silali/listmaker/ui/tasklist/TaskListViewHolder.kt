package com.silali.listmaker.ui.tasklist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.silali.listmaker.R
import com.silali.listmaker.data.model.TaskList

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: TaskList) {
        val titleTextView = itemView.findViewById<TextView>(R.id.task_list_title)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.task_list_description)
        titleTextView.text = item.title
        descriptionTextView.text = item.description
    }
}