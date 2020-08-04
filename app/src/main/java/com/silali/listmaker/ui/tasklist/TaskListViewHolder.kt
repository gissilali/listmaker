package com.silali.listmaker.ui.tasklist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.silali.listmaker.R

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item : String) {
        val textView = itemView.findViewById<TextView>(R.id.list_item_title)
        textView.text = item
    }
}