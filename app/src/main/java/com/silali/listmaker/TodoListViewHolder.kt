package com.silali.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item : String) {
        val textView = itemView.findViewById<TextView>(R.id.todo_text)
        textView.text = item
    }
}