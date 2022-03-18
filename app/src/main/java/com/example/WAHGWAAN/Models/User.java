package com.example.WAHGWAAN.Models;

import com.google.gson.annotations.SerializedName;

import androidx.lifecycle.MutableLiveData;

public class User extends MutableLiveData<User> {

    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private Double phonenumber;

    @SerializedName("access_token")
    private String token;

    private Boolean has_profile;
    private String refresh_token;

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
    //////////////Getter methods
    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Double getPhonenumber() {
        return phonenumber;
    }

    public String getToken() {
        return token;
    }

    public Boolean getHas_profile() {
        return has_profile;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getEmail() {
        return Email;
    }

    public Boolean getOnline() {
        return IsOnline;
    }

    public Boolean getLoggedIn() {
        return IsLoggedIn;
    }

//////////////Setter methods

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhonenumber(Double phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setHas_profile(Boolean has_profile) {
        this.has_profile = has_profile;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setOnline(Boolean online) {
        IsOnline = online;
    }

    public void setLoggedIn(Boolean loggedIn) {
        IsLoggedIn = loggedIn;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
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
