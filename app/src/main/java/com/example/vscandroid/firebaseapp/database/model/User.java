package com.example.vscandroid.firebaseapp.database.model;

public class User {

    String userID;
    String userName;
    String userPhone;
    String userEmail;

    public User() {

    }

    public User(String userID, String userName, String userPhone,String userEmail) {
        this.userID = userID;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }
}