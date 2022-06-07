package com.example.tm;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repository {

    Application application;
    FirebaseAuth auth;
    FirebaseDatabase dbFire;
    DatabaseReference employees;
    DatabaseReference tasks;

    public Repository( Application application ){
        this.application = application;
        auth = FirebaseAuth.getInstance ();
        dbFire = FirebaseDatabase.getInstance ();
        employees = dbFire.getReference ("Employees");
        tasks = dbFire.getReference ("Tasks");
    }

    public FirebaseAuth getAuth(){
        return auth;
    }
    public DatabaseReference getEmployeesRef () {
        return employees;
    }
    public DatabaseReference getTasksRef () {
        return tasks;
    }
}
