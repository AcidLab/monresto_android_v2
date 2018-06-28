package com.monresto.acidlabs.monresto.Service.User;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public void register(final String login, final String password, final String password_confirm, final String email, final String fname, final String lname,
                         final String civility, final String phone, final String mobile, final String comment, final ArrayList<Address> addresses) {
        final JSONArray addressesArray = new JSONArray();
        for(Address A : addresses){
            addressesArray.put(A.toJson());
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        //String url = Config.server + "User/Register.php";
        final String url = "http://41.231.54.20/jibly/api/User/register.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("---- REGISTED ----", "onResponse: "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "onErrorResponse: "+error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                JSONObject userObject = User.registerJson(login, password, password_confirm, fname, lname, civility, email, phone, mobile, comment, addresses);
                String token = FirebaseInstanceId.getInstance().getToken();
                String signature = Utilities.md5(userObject.toString() + token + "android" + "vZ!m@73@tH*c2jPV4Z2");

                params.put("userPost", userObject.toString());
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
                //TODO: password encryption ?
                Map<String, String> params = new HashMap<>();
                String token = FirebaseInstanceId.getInstance().getToken();

                String signature = Utilities.md5(email + password + token + "android" + Config.sharedKey);
                params.put("email", email);
                params.put("password", password);
                params.put("token", password);
                params.put("device", "android");
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);
    }
}
