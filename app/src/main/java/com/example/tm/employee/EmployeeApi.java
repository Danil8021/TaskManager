package com.example.tm.employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeApi {

    @GET("/employee")
    public Call<List<Employee>> getEmployee();

}
