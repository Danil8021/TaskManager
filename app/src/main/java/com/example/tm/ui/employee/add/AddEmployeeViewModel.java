package com.example.tm.ui.employee.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tm.employee.Employee;
import com.example.tm.employee.EmployeeAdapter;
import com.example.tm.EmployeeRepository;
import com.example.tm.task.Task;
import com.example.tm.TaskRepository;

import java.util.List;

import retrofit2.Call;

public class AddEmployeeViewModel extends AndroidViewModel {

    EmployeeRepository employeeRepository;
    TaskRepository taskRepository;
    Employee employee;

    LiveData<List<Task>> taskLive;

    public AddEmployeeViewModel ( @NonNull Application application ) {
        super ( application );
        employeeRepository = new EmployeeRepository ( application );
        taskRepository = new TaskRepository ( application );
        taskLive = taskRepository.getTasks ( EmployeeAdapter.Employee.id );
    }

    public LiveData<List<Task>> getTaskList(){
        return taskLive;
    }

    public boolean addEmployee(String surname, String firstName, String patronymic, String position, String login, String password){
        if (surname.isEmpty () || firstName.isEmpty () || patronymic.isEmpty () || position.isEmpty () || login.isEmpty () || password.isEmpty ()){
            return false;
        }
        employee = new Employee();
        employee.surname = surname.trim();
        employee.firstName = firstName.trim();
        employee.patronymic = patronymic.trim();
        employee.position = position.toString();
        employee.login = login.trim();
        employee.password = password.trim();
        employee.isDirector = false;
        employeeRepository.insert ( employee );
        return true;
    }

    public boolean updateEmployee( Employee employee, String surname, String firstName, String patronymic, String position, String login, String password){
        if (surname.isEmpty () || firstName.isEmpty () || patronymic.isEmpty () || position.isEmpty () || login.isEmpty () || password.isEmpty ()){
            return false;
        }employee.surname = surname.trim();
        employee.firstName = firstName.trim();
        employee.patronymic = patronymic.trim();
        employee.position = position.toString();
        employee.login = login.trim();
        employee.password = password.trim();
        employeeRepository.update ( employee );
        return true;
    }
}