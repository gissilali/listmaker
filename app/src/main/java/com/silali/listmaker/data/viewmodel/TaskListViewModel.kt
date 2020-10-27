package com.silali.listmaker.data.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silali.listmaker.data.model.TaskList
import com.silali.listmaker.data.repository.TaskListRepository
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskListRepository: TaskListRepository) : ViewModel(), Observable {
    val taskLists = taskListRepository.taskLists

    lateinit var currentTaskList: LiveData<TaskList>

    @Bindable
    val listTitle = MutableLiveData<String>()

    @Bindable
    val listDescription = MutableLiveData<String?> ()

    fun saveOrUpdate() {
        val title = listTitle.value!!
        val description = listDescription.value

        add(TaskList(0, title, description, ArrayList(), null, null))
    }

    fun add(taskList: TaskList) = viewModelScope.launch {
        taskListRepository.addTaskList(taskList)
    }

    fun getList(listId: Int) = viewModelScope.launch{
        currentTaskList = taskListRepository.getTaskList(listId)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}