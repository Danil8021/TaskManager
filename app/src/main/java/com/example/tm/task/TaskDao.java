package com.example.tm.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tm.task.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("Select * from Task where employee_id = :employee_id order by done, date")
    LiveData<List<Task>> getTask( int employee_id);

    @Insert
    void insert(Task task);

    @Update
    void update( Task task);

    @Delete
    void delete(Task task);
}
