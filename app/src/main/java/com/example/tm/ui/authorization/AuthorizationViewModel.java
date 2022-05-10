package com.example.tm.ui.authorization;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tm.employee.Employee;
import com.example.tm.EmployeeRepository;
import com.example.tm.R;

import java.util.List;

public class AuthorizationViewModel extends AndroidViewModel {

    EmployeeRepository employeeRepository;

    List<Employee> employeeList;
    Employee authorizedEmployee;

    public AuthorizationViewModel ( @NonNull Application application ) {
        super ( application );
        employeeRepository = new EmployeeRepository ( application );
    }

    public Integer getIdEmployee() {
        if (authorizedEmployee == null)
            return -1;
        return authorizedEmployee.id;
    }
    public boolean EmployeeVerification(String login, String password) {
        if (login.equals("") && password.equals("")){
            Toast.makeText(getApplication (), R.string.fields_are_not_filled, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (login.equals("")){
            Toast.makeText(getApplication (),R.string.login_not_entered,Toast.LENGTH_SHORT).show();
            return false;
        }


//        Employee emp = new Employee ();
//        emp.firstName = "Данил";
//        emp.surname = "Чанков";
//        emp.patronymic = "Андреевич";
//        emp.position = "Директор";
//        emp.login = "abc";
//        emp.password = "abc";
//        emp.isDirector = true;
//
//        employeeRepository.insert ( emp );


        employeeList = employeeRepository.getEmployee ();

        boolean l = true;
        if(employeeList != null){
            for (Employee e:employeeList) {
                if(e.login.equals(login)){
                    authorizedEmployee = e;
                    l = false;
                }
            }
        }
        if (l){
            Toast.makeText(getApplication (), R.string.there_is_no_user_with_this_username,Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.equals("")){
            Toast.makeText(getApplication (),R.string.password_not_entered,Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean p = !authorizedEmployee.password.equals(password);
        if (p){
            Toast.makeText(getApplication (), R.string.invalid_password,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
