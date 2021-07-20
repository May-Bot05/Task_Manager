package com.example.roomlecture;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\'\u00a8\u0006\t"}, d2 = {"Lcom/example/roomlecture/TaskListDao;", "", "getTaskByPriority", "Landroidx/lifecycle/LiveData;", "", "Lcom/example/roomlecture/Task;", "status", "", "getTaskByTitle", "app_debug"})
public abstract interface TaskListDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM task WHERE status = :status ORDER BY task_priority DESC")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.example.roomlecture.Task>> getTaskByPriority(int status);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM task WHERE status = :status ORDER BY title")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.example.roomlecture.Task>> getTaskByTitle(int status);
}