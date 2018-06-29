package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    private static User instance;

    private int id;
    private String login;
    private String password;
    private String password_confirm;
    private String email;
    private String fname;
    private String lname;
    private String civility;
    private String phone;
    private String mobile;
    private String comment;
    private ArrayList<Address> addresses;

    public User(String login, String password, String password_confirm, String email, String fname, String lname, String civility, String phone, String mobile, String comment, ArrayList<Address> addresses) {
        this.login = login;
        this.password = password;
        this.password_confirm = password_confirm;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.civility = civility;
        this.phone = phone;
        this.mobile = mobile;
        this.comment = comment;
        this.addresses = addresses;
    }

    public static User setInstance(User user) {
        instance = user;
        return instance;
    }

    public static User getInstance() {
        return instance;
    }

    public static JSONObject registerJson(String login, String password, String password_confirm, String email, String fname, String lname,
                                     String civility, String phone, String mobile, String comment, ArrayList<Address> addresses) {

        JSONObject obj = new JSONObject();
        JSONArray addressesArray = new JSONArray();
        for(Address A : addresses){
            addressesArray.put(A.toJson());
        }

        try {
            obj.put("login", login);
            obj.put("password", password);
            obj.put("confirmPassword", password_confirm);
            obj.put("firstName", fname);
            obj.put("lastName", lname);
            obj.put("civility", String.valueOf(civility));
            obj.put("email", phone);
            obj.put("phone",phone );
            obj.put("mobile", mobile);
            obj.put("commentaire", comment);
            obj.put("Address", addressesArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword_confirm() {
        return password_confirm;
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

    public String getCivility() {
        return civility;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getComment() {
        return comment;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }
}
