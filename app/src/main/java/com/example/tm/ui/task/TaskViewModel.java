package com.example.tm.ui.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tm.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskViewModel extends AndroidViewModel {

    Repository repository;

    public TaskViewModel ( @NonNull Application application ) {
        super ( application );
        repository = new Repository ( application );
    }
    public FirebaseAuth getAuth(){return repository.getAuth ();}
    public FirebaseDatabase getDB() {return repository.getDBFire ();}
    public DatabaseReference getEmployeesRef () {return repository.getEmployeesRef ();}
    public DatabaseReference getTasksRef () {return repository.getTasksRef ();}
}