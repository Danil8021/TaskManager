package com.example.tm.ui.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tm.MainActivity;
import com.example.tm.task.Task;
import com.example.tm.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    TaskRepository taskRepository;

    LiveData<List<Task>> taskList;

    public TaskViewModel ( @NonNull Application application ) {
        super ( application );
        taskRepository = new TaskRepository ( application );
    }

    public LiveData<List<Task>> getTaskList(){
        return taskList = taskRepository.getTasks ( MainActivity.AuthorizationEmployee.id );
    }
}