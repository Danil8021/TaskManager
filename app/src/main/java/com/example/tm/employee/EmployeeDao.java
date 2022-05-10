package com.example.tm.employee;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("Select * from Employee")
    List<Employee> getEmployee();

    @Query("Select * from Employee where isDirector = 0")
    LiveData<List<Employee>> getEmployeeWithoutDirector();

    @Query ( "Select * from Employee where id = :id" )
    Employee getEmployeeById(int id);

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);
}
