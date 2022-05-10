package com.example.tm.employee;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Employee {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    public int id;

    @ColumnInfo
    @SerializedName("surname")
    @Expose
    public String surname;

    @ColumnInfo
    @SerializedName("firstname")
    @Expose
    public String firstName;

    @ColumnInfo
    @SerializedName("patronymic")
    @Expose
    public String patronymic;

    @ColumnInfo
    @SerializedName("position")
    @Expose
    public String position;

    @ColumnInfo
    @SerializedName("login")
    @Expose
    public String login;

    @ColumnInfo
    @SerializedName("password")
    @Expose
    public String password;

    @ColumnInfo
    @SerializedName("isdirector")
    @Expose
    public boolean isDirector;

}
