package com.silali.listmaker.data.repository

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
}