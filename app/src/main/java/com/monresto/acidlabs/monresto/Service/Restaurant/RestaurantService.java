package com.monresto.acidlabs.monresto.Service.Restaurant;

import android.content.Context;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.FAQ;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Service.FAQ.FAQAsyncResponse;
import com.monresto.acidlabs.monresto.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantService {
    private Context context;
    private ArrayList<Restaurant> RestaurantList;

    public RestaurantService(Context context) {
        this.context = context;
        RestaurantList = new ArrayList<>();
    }

    public void getAll() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.server + "Restaurant/findRestoGeo.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray resto = jsonResponse.getJSONArray("Resto");
                            JSONObject obj;
                            for (int i = 0; i < resto.length(); i++) {
                                Log.i("RESPONSE", "onResponse: Create obj");
                                obj = resto.getJSONObject(i);
                                RestaurantList.add(Restaurant.createFromJson(obj));
                            }
                            ((RestaurantAsyncResponse) context).processFinish(RestaurantList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                double latitude = 36.8563153;
                double longitude = 10.1935078;
                int type = 0;
                String signature = Utilities.md5("" + longitude + latitude + type + Config.sharedKey);
                params.put("longitude", String.valueOf(longitude));
                params.put("latitude", String.valueOf(latitude));
                params.put("type", String.valueOf(type));
                params.put("signature", signature);

                return params;
            }
        };
        queue.add(postRequest);

    }

}
