package com.example.tm.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
@Entity()
public class Task {

    @PrimaryKey(autoGenerate = true)
    @SerializedName( "id" )
    @Expose
    public int id;

    @ColumnInfo
    @SerializedName ( "heading" )
    @Expose
    public String heading;

    @ColumnInfo
    @SerializedName ( "description" )
    @Expose
    public String description;

    @ColumnInfo
    @SerializedName ( "date" )
    @Expose
    public String date;

    @ColumnInfo
    @SerializedName ( "done" )
    @Expose
    public boolean done;

    @ColumnInfo
    @SerializedName ( "employee_id" )
    @Expose
    public int employee_id;

}
