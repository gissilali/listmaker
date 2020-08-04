package com.silali.listmaker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silali.listmaker.data.repository.TaskListRepository
import java.lang.IllegalArgumentException

class TaskListViewModelFactory(private val taskListRepository: TaskListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            return TaskListViewModel(taskListRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class bitch!!!")
    }
}