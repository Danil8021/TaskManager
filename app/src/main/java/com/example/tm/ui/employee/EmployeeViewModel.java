package com.example.tm.ui.employee;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tm.employee.Employee;
import com.example.tm.EmployeeRepository;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {

    EmployeeRepository employeeRepository;

    LiveData<List<Employee>> employeeList;

    public EmployeeViewModel ( @NonNull Application application ) {
        super ( application );
        employeeRepository = new EmployeeRepository ( application );
        employeeList = employeeRepository.getEmployeeWithoutDirector ();
    }

    public LiveData<List<Employee>> getEmployeeListWithoutDirector() {
        return employeeRepository.getEmployeeWithoutDirector ();
    }

}