package com.example.taskmanager.data

import android.app.Application
import androidx.lifecycle.LiveData

class TaskListRepository(context: Application){
    private val taskListDao: TaskListDao = TaskDatabase.getDatabase(context).taskListDao()

    fun getTasks(sort: SortColumn = SortColumn.Priority
    ): LiveData<List<Task>> {
        return if(sort == SortColumn.Priority){
            taskListDao.getTaskByPriority(TaskStatus.Open.ordinal)
        } else{
            taskListDao.getTaskByTitle(TaskStatus.Open.ordinal)
        }
    }
}