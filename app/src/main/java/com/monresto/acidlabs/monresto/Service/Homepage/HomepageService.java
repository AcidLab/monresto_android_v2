package com.monresto.acidlabs.monresto.Service.Homepage;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.HomepageConfig;
import com.monresto.acidlabs.monresto.Model.HomepageDish;
import com.monresto.acidlabs.monresto.Model.HomepageEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomepageService {
    private Context context;

    public HomepageService(Context context) {
        this.context = context;
    }

    public void getAll(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Config.acidlabsServer + "homepage";

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONObject config = response.getJSONObject("Config");
                        HomepageConfig homepageConfig = new HomepageConfig(config.optInt("id"),config.optString("cover_image"),config.optString("busket_image"), config.optString("snack"), config.optString("delivery"), config.optString("created_at"),config.optString("updated_at"));
                        ((HomepageAsyncResponse)context).onHomepageConfigReceived(homepageConfig);

                        JSONArray dishesJSON = response.getJSONArray("Dishes");
                        ArrayList<HomepageDish> dishes = new ArrayList<>();
                        for (int i=0; i<dishesJSON.length(); i++) {
                            JSONObject dish = dishesJSON.getJSONObject(i);
                            dishes.add(new HomepageDish(dish.optInt("id"), dish.optString("label"), dish.optString("title"), dish.getString("image"), dish.optInt("restoID"), dish.optInt("dishID"), dish.optString("display_date"),dish.getString("created_at"), dish.optString("updated_at")));
                        }
                        ((HomepageAsyncResponse)context).onHomepageDishesReceived(dishes);

                        JSONArray eventsJSON = response.getJSONArray("Events");
                        ArrayList<HomepageEvent> events = new ArrayList<>();
                        for (int i=0; i<eventsJSON.length(); i++) {
                            JSONObject event = eventsJSON.getJSONObject(i);
                            events.add(new HomepageEvent(event.optInt("id"), event.optString("label"), event.optString("title"), event.getString("image"), event.optInt("restoID"), event.getString("restoIcon"), event.optString("display_date"),event.getString("created_at"), event.optString("updated_at")));
                        }
                        ((HomepageAsyncResponse)context).onHomepageEventsReceived(events);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // TODO: Handle error
                });

        queue.add(request);
    }

}
