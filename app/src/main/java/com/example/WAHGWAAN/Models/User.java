package com.example.WAHGWAAN.Models;

import androidx.lifecycle.MutableLiveData;

public class User extends MutableLiveData<User> {

    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private Double phonenumber;
    private String Email;
    private Boolean IsOnline;
    private Boolean IsLoggedIn;
    private String gender;

    public void User(Integer id, String firstname, String lastname,String username, Double phonenumber, String Email, Boolean IsOnline, String gender){
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.Email = Email;
        this.IsOnline = IsOnline;
        this.gender = gender;

    }

    public void User(){
        //this.IsLoggedIn = false;
    }

    public User getUser(){
        return this;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String Val){
        this.username = Val;
    }

    public void loginUser(Boolean state){
        this.IsLoggedIn = state;
    }

    public Boolean LoginState(){
        return this.IsLoggedIn;
    }
}
