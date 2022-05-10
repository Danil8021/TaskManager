package com.example.tm;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tm.task.GetTaskListDeserializer;
import com.example.tm.task.Task;
import com.example.tm.task.TaskApi;
import com.example.tm.task.TaskDao;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {

    Application application;

    TaskDao taskDao;

    LiveData<List<Task>> taskLive;

//    TaskApi taskApi = NetworkService
//            .getInstance (new TypeToken<LiveData<List<Task>>> () {}.getType (), new GetTaskListDeserializer ())
//            .create ( TaskApi.class );

    public TaskRepository( Application application ){
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase ( application );
        taskDao = db.taskDao ( );
    }

    public LiveData<List<Task>> getTasks(int id){
        Thread thread = new Thread ( () -> taskLive =  taskDao.getTask ( id ));
        thread.start ();
        try {
            thread.join ();
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }
        return taskLive;
    }

    public void insert(Task task){
        new Thread ( () -> taskDao.insert ( task ) ).start ();
    }

    public void update(Task task){
        new Thread ( () -> taskDao.update ( task ) ).start ();
    }

    public void delete(Task task){
        new Thread ( () -> taskDao.delete ( task ) ).start ();
    }

}
