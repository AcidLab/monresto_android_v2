package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
