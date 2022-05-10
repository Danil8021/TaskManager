package com.example.tm.ui.task.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tm.employee.EmployeeAdapter;
import com.example.tm.MainActivity;
import com.example.tm.task.Task;
import com.example.tm.TaskRepository;

public class AddTaskViewModel extends AndroidViewModel {

    static public Boolean bAdd;
    TaskRepository taskRepository;

    public AddTaskViewModel ( @NonNull Application application ) {
        super ( application );
        taskRepository = new TaskRepository ( application );
    }

    public boolean addTask(String heading, String description, String date){
        if (heading.isEmpty () || description.isEmpty () || date.isEmpty ()){
            return false;
        }
        Task task = new Task();
        task.heading = heading;
        task.description = description;
        task.date = date;
        task.done = false;
        if (bAdd){
            task.employee_id = EmployeeAdapter.Employee.id;
        }
        else {
            task.employee_id = MainActivity.AuthorizationEmployee.id;
        }
        taskRepository.insert(task);
        return true;
    }
    public boolean updateTask(Task task, String heading, String description, String date) {
        if (heading.isEmpty ( ) || description.isEmpty ( ) || date.isEmpty ( )) {
            return false;
        }
        task.heading = heading;
        task.description = description;
        task.date = date;
        task.done = false;
        taskRepository.update ( task );
        return true;
    }
}