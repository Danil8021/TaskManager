package com.example.tm.ui.employee;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tm.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class EmployeeViewModel extends AndroidViewModel {

    Repository repository;

    public EmployeeViewModel ( @NonNull Application application ) {
        super ( application );
        repository = new Repository ( application );
    }

    public FirebaseAuth getAuth(){
        return repository.getAuth ();
    }
    public DatabaseReference getEmployeesRef () {
        return repository.getEmployeesRef ();
    }
}