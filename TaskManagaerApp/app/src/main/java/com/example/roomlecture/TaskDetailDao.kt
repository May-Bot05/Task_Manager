package com.example.roomlecture

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDetailDao {
    @Query("SELECT * FROM task WHERE `id` = :id")
    fun getTask(id: Int): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}