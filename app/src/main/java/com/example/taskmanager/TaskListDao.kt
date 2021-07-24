package com.example.taskmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskListDao {
    @Query("SELECT * FROM task WHERE status = :status ORDER BY task_priority DESC")
    fun getTaskByPriority(status: Int): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE status = :status ORDER BY title")
    fun getTaskByTitle(status: Int): LiveData<List<Task>>
}