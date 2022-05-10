package com.example.tm;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tm.employee.Employee;
import com.example.tm.employee.EmployeeDao;

import java.util.List;

public class EmployeeRepository {

    Application application;
    EmployeeDao employeeDao;

    List<Employee> employeeList;
    Employee employee;

    public EmployeeRepository( Application application ){
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase ( application );
        employeeDao = db.employeeDao ( );
    }

    public List<Employee> getEmployee(){
        Thread thread = new Thread ( () -> employeeList = employeeDao.getEmployee ( ) );
        thread.start ();
        try {
            thread.join ();
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }
        return employeeList;
    }

    public LiveData<List<Employee>> getEmployeeWithoutDirector(){
        return employeeDao.getEmployeeWithoutDirector ( ) ;
    }

    public Employee getEmployeeById(int id){
        Thread thread = new Thread ( () -> employee = employeeDao.getEmployeeById ( id ) );
        thread.start ();
        try {
            thread.join ();
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }
        return employee;
    }

    public void insert(Employee employee){
        new Thread ( () -> employeeDao.insert ( employee ) ).start ();
    }

    public void update( Employee employee){
        new Thread ( () -> employeeDao.update ( employee ) ).start ();
    }

    public void delete(Employee employee){
        new Thread ( () -> employeeDao.delete ( employee ) ).start ();
    }

}
