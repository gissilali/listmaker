package com.silali.listmaker.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskListDao {
    @Insert
    suspend fun addTaskList(taskList: TaskList) : Long

    @Update
    suspend fun updateTaskList(taskList: TaskList)

    @Delete
    suspend fun deleteTaskList(taskList: TaskList)

    @Query("DELETE  from task_lists_table")
    suspend fun deleteAllTaskLists()

    @Query("SELECT * FROM task_lists_table")
    fun getAllTasksLists() : LiveData<List<TaskList>>
}