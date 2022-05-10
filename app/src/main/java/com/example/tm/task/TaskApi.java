package com.example.tm.task;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TaskApi {

    @GET("/task/{id}")
    Call<LiveData<List<Task>>> getTask( @Path("id") int id);

    @POST("/api/task/insert")
    static Call<Task> insert ( @Body Task task ) {
        return null;
    }

}
