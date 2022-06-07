package com.example.tm.ui.task.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tm.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AddTaskViewModel extends AndroidViewModel {

    static public Boolean bAdd;
    Repository repository;

    public AddTaskViewModel ( @NonNull Application application ) {
        super ( application );
        repository = new Repository ( application );
    }

    public FirebaseAuth getAuth(){
        return repository.getAuth ();
    }
    public DatabaseReference getEmployeesRef () { return repository.getEmployeesRef (); }
    public DatabaseReference getTasksRef () {
        return repository.getTasksRef ();
    }
}