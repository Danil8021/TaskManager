package com.example.tm.employee;

public class Employee {

    public String id;

    public String surname;

    public String firstName;

    public String patronymic;

    public String position;

    public String login;

    public String password;

    public boolean isDirector;

    public String directorId;

    public Employee () {
    }

    public Employee ( String id , String surname , String firstName , String patronymic , String position , String login , String password , boolean isDirector , String directorId ) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.position = position;
        this.login = login;
        this.password = password;
        this.isDirector = isDirector;
        this.directorId = directorId;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getSurname () {
        return surname;
    }

    public void setSurname ( String surname ) {
        this.surname = surname;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }

    public String getPatronymic () {
        return patronymic;
    }

    public void setPatronymic ( String patronymic ) {
        this.patronymic = patronymic;
    }

    public String getPosition () {
        return position;
    }

    public void setPosition ( String position ) {
        this.position = position;
    }

    public String getLogin () {
        return login;
    }

    public void setLogin ( String login ) {
        this.login = login;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword ( String password ) {
        this.password = password;
    }

    public boolean isDirector () {
        return isDirector;
    }

    public void setDirector ( boolean director ) {
        isDirector = director;
    }

    public String getDirectorId () {
        return directorId;
    }

    public void setDirectorId ( String directorId ) {
        this.directorId = directorId;
    }
}
