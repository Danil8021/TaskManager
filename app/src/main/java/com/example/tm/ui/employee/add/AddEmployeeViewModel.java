package com.example.tm.ui.employee.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tm.Repository;
import com.example.tm.employee.Employee;
import com.example.tm.task.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddEmployeeViewModel extends AndroidViewModel {

    Repository repository;
    Employee employee;

    LiveData<List<Task>> taskLive;

    public AddEmployeeViewModel ( @NonNull Application application ) {
        super ( application );
        repository = new Repository ( application );
    }

    public FirebaseAuth getAuth(){
        return repository.getAuth ();
    }
    public FirebaseDatabase getDB() {return repository.getDBFire ();}
    public DatabaseReference getEmployeesRef () {
        return repository.getEmployeesRef ();
    }
    public DatabaseReference getTasksRef () {
        return repository.getTasksRef ();
    }
}