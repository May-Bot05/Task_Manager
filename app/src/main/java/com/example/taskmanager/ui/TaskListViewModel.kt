package com.example.taskmanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.taskmanager.data.SortColumn
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.TaskListRepository

class TaskListViewModel(application: Application): AndroidViewModel(application){
    private val repo: TaskListRepository =
        TaskListRepository(application)

    private val _sortOrder =  MutableLiveData<SortColumn>(
        SortColumn.Priority
    )

    val tasks: LiveData<List<Task>> = Transformations.switchMap(_sortOrder){
        repo.getTasks(_sortOrder.value!!)
    }

    fun changeSortOrder(sortOrder: SortColumn){
        _sortOrder.value = sortOrder
    }
}