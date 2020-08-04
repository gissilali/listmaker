package com.silali.listmaker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.OffsetDateTime

@Entity(tableName = "task_lists_table")
@TypeConverters(TaskListTypeConverter::class)
data class TaskList(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String?,
    val tasks: ArrayList<String> = ArrayList(),
    val completedOn: OffsetDateTime?,
    val dueOn: OffsetDateTime?
)