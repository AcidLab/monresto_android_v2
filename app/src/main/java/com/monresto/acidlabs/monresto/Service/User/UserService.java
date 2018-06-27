package com.monresto.acidlabs.monresto.Service.User;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Utilities;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public void register(final String email, final String password, final String fname, final String lname) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "User/Register.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Treat response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //TODO: password encryption
                Map<String, String> params = new HashMap<>();
                String signature = Utilities.md5("" + email + password + fname + lname + Config.sharedKey);
                params.put("email", email);
                params.put("password", password);
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void login(final String email, final String password) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "User/Login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO: set user instance
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //TODO: password encryption
                Map<String, String> params = new HashMap<>();
                String signature = Utilities.md5("" + email + password + Config.sharedKey);
                params.put("email", email);
                params.put("password", password);
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);
    }
}
