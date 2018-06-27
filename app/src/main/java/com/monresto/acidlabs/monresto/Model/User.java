package com.monresto.acidlabs.monresto.Model;

public class User {
    private static User instance;

    private String email;
    private String fname;
    private String lname;

    public User(String email, String fname, String lname) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
    }

    public static User setInstance(User user) {
        instance = user;
        return instance;
    }

    public static User getInstance() {
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
