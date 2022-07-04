package com.example.shoppingapp;

public class User {
    public String fullname, email;

    public User() {

    }

    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }
}
