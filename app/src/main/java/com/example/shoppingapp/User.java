package com.example.shoppingapp;

public class User {
    public String fullname, email,password;
    String profileImg;

    public User() {

    }

    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
