package com.example.tm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;;import com.example.tm.employee.Employee;
import com.example.tm.employee.EmployeeDao;
import com.example.tm.task.Task;
import com.example.tm.task.TaskDao;

@Database(entities = {Employee.class, Task.class},exportSchema = false, version = 1)
abstract class AppDatabase extends RoomDatabase {

    public abstract EmployeeDao employeeDao();
    public abstract TaskDao taskDao();

    public static AppDatabase db;

    static synchronized AppDatabase getDatabase(Context context){
        if (db==null){
            db = Room.databaseBuilder(context,AppDatabase.class,"db").build();
        }
        return db;
    }
}