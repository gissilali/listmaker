package com.silali.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item : String) {
        val textView = itemView.findViewById<TextView>(R.id.list_item_title)
        textView.text = item
    }
}