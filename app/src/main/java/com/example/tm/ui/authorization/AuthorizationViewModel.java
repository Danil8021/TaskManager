package com.example.tm.ui.authorization;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tm.Repository;
import com.example.tm.employee.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AuthorizationViewModel extends AndroidViewModel {

    Repository repository;

    List<Employee> employeeList;

    Employee authorizationEmployee;

    boolean result;

    public AuthorizationViewModel ( @NonNull Application application ) {
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
}
