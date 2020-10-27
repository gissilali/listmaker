package com.silali.listmaker.data.repository

import androidx.lifecycle.LiveData
import com.silali.listmaker.data.model.TaskList
import com.silali.listmaker.data.model.TaskListDao

class TaskListRepository(private val dao: TaskListDao) {
    val taskLists = dao.getAllTasksLists()

    suspend fun addTaskList(taskList: TaskList) {
        dao.addTaskList(taskList)
    }

    suspend fun updateTaskList(taskList: TaskList) {
        dao.updateTaskList(taskList)
    }

    fun getTaskList(listId: Int): LiveData<TaskList> {
        return dao.getTaskList(listId)
    }
}