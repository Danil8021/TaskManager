package com.example.tm;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tm.employee.Employee;
import com.example.tm.task.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Repository {

    Application application;

    LiveData<List<Task>> taskLive;

    List<Employee> employeeList;
    Employee employee;

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
    public FirebaseDatabase getDBFire() {return dbFire;}
    public DatabaseReference getEmployeesRef () {
        return employees;
    }
    public DatabaseReference getTasksRef () {
        return tasks;
    }

//    public List<Employee> getEmployee(){
//    }
//
//    public LiveData<List<Employee>> getEmployeeWithoutDirector(){
//    }
//
//    public Employee getEmployeeById(int id){
//    }
//
//    public void insertEmployee ( Employee employee){
//    }
//
//    public void updateEmployee( Employee employee){
//    }
//
//    public void deleteEmployee ( Employee employee){
//    }
//
//
//    public LiveData<List<Task>> getTasks(int id){
//    }
//
//    public void insertTask ( Task task){
//    }
//
//    public void updateTask ( Task task){
//    }
//
//    public void deleteTask ( Task task) { }
}
