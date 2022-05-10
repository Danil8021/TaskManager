package com.example.tm.task;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetTaskListDeserializer implements JsonDeserializer<LiveData<List<Task>>> {

    @Override
    public LiveData<List<Task>> deserialize ( JsonElement json , Type typeOfT , JsonDeserializationContext context ) throws JsonParseException {
        List<Task> tasks = new ArrayList (  );

        final JsonArray itemsJsonArray = json.getAsJsonArray ();

        for (JsonElement itemsJsonElement : itemsJsonArray) {
            final JsonObject itemJsonObject = itemsJsonElement.getAsJsonObject();
            final int id = itemJsonObject.get("id").getAsInt ();
            final String heading = itemJsonObject.get("heading").getAsString();
            final String description = itemJsonObject.get("description").getAsString();
            final String date = itemJsonObject.get("date").getAsString();
            final boolean done = itemJsonObject.get("done").getAsBoolean ();
            final int employee_id = itemJsonObject.get("employee_id").getAsInt();
            Task task = new Task ();
            task.id = id;
            task.heading = heading;
            task.description = description;
            task.date = date;
            task.done = done;
            task.employee_id = employee_id;
            tasks.add(task);
        }
        LiveData<List<Task>> tasksLive = ( LiveData<List<Task>> ) tasks;
        return tasksLive;
    }
}
