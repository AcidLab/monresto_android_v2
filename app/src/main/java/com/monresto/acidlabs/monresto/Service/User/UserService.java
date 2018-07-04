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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO: get Firebase token

public class UserService {
    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public void register(final String login, final String password, final String password_confirm, final String email, final String fname, final String lname,
                         final String civility, final String phone, final String mobile, final String comment, final ArrayList<Address> addresses) {
        final JSONArray addressesArray = new JSONArray();
        for (Address A : addresses) {
            addressesArray.put(A.toJson());
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "User/Register.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject clientObject = jsonResponse.getJSONObject("Client");
                            int status = clientObject.getInt("Status");
                            if (status != 0) {
                                int id = clientObject.optInt("userID");
                                User user = new User(id, login, email, fname, lname, civility, phone, mobile, comment, addresses);
                                ((UserAsyncResponse) context).onUserLogin(user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                JSONObject userObject = User.registerJson(login, password, password_confirm, fname, lname, civility, email, phone, mobile, comment, addresses);
                String token = "kento";//FirebaseInstanceId.getInstance().getToken();
                String signature = Utilities.md5(userObject.toString() + token + "android" + Config.sharedKey);

                params.put("user", userObject.toString());
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);
    }

    /**
     *
     * @param login user login
     * @param password user password
     *
     * onUserLogin should call for getDetails with login parameter true
     */
    public void login(final String login, final String password) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "User/Login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject clientObject = jsonResponse.getJSONObject("Client");
                            int status = clientObject.getInt("Status");
                            if (status != 0) {
                                int id = clientObject.optInt("userID");
                                User user = new User(id, login, "", "", "", "", "", "", "", null);
                                ((UserAsyncResponse) context).onUserLogin(user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                Map<String, String> params = new HashMap<>();
                String token = "kento";//FirebaseInstanceId.getInstance().getToken();

                String signature = Utilities.md5(login + password + token + "android" + Config.sharedKey);
                params.put("login", login);
                params.put("password", password);
                params.put("token", password);
                params.put("device", "android");
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void facebookLogin(final String socialID, final String email, final String fname, final String lname) {
        final int socialNetworkID = 1;
        final int deviceID = 1;

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "User/socialConnect.php"; //doesn't work
        url = "http://41.231.54.20/jibly/api/User/socialConnect.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject clientObject = jsonResponse.getJSONObject("Client");
                            int status = clientObject.getInt("Status");
                            if (status != 0) {
                                int id = clientObject.optInt("userID");
                                User user = new User(id, email, fname, lname);
                                ((UserAsyncResponse) context).onUserLogin(user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                Map<String, String> params = new HashMap<>();
                String token = "azerty77";//FirebaseInstanceId.getInstance().getToken();

                String signature = Utilities.md5(socialID + email + fname + lname + socialNetworkID + token + deviceID + "vZ!m@73@tH*c2jPV4Z2");
                params.put("socialID", socialID);
                params.put("email", email);
                params.put("firstName", fname);
                params.put("lastName", lname);
                params.put("socialNetworkID", String.valueOf(socialNetworkID));
                params.put("token", token);
                params.put("deviceID", String.valueOf(deviceID));
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);

    }

    /**
     *
     * @param id user's id
     * @param login is true when user details are requested after login
     */
    public void getDetails(final int id, final boolean login) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "User/userDetails.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("Status");
                            if (status != 0) {
                                JSONObject clientObject = jsonResponse.optJSONObject("User");
                                User user = new User(id, clientObject.optString("login"), clientObject.optString("email"), clientObject.optString("firstName"), clientObject.optString("lastName"), clientObject.optString("civility"), clientObject.optString("phone"), clientObject.optString("mobile"), "", null);
                                if(login)
                                    User.setInstance(user);
                                ((UserAsyncResponse) context).onUserDetailsReceived(user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                Map<String, String> params = new HashMap<>();
                String token = "kento";//FirebaseInstanceId.getInstance().getToken();

                String signature = Utilities.md5(id + Config.sharedKey);
                params.put("userID", String.valueOf(id));
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void addAddress(final Address address) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "Address/addAddress.php";
        final int userID = User.getInstance().getId();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                Map<String, String> params = new HashMap<>();
                String signature = Utilities.md5(address.toJson().toString() + userID + Config.sharedKey);

                params.put("address", address.toJson().toString());
                params.put("userID", String.valueOf(userID));
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);
    }
}
