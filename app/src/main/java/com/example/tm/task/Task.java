package com.example.tm.task;


public class Task {

    public String id;

    public String heading;

    public String description;

    public String date;

    public boolean done;

    public String idEmployee;

    public Task () {
    }

    public Task ( String id , String heading , String description , String date , boolean done , String idEmployee ) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.date = date;
        this.done = done;
        this.idEmployee = idEmployee;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getHeading () {
        return heading;
    }

    public void setHeading ( String heading ) {
        this.heading = heading;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public String getDate () {
        return date;
    }

    public void setDate ( String date ) {
        this.date = date;
    }

    public boolean isDone () {
        return done;
    }

    public void setDone ( boolean done ) {
        this.done = done;
    }

    public String getIdEmployee () {
        return idEmployee;
    }

    public void setIdEmployee ( String idEmployee ) {
        this.idEmployee = idEmployee;
    }
}
