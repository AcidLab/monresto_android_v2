package com.monresto.acidlabs.monresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;

import java.util.ArrayList;


//Testing fetch information from api

public class MainActivity extends AppCompatActivity implements RestaurantAsyncResponse {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        text = new TextView(this);
        text.setText("Hello world!");
        setContentView(text);

        RestaurantService service = new RestaurantService(this);
        service.getAll();
    }

    @Override
    public void processFinish(ArrayList<Restaurant> RestaurantList) {
        text.setText(RestaurantList.get(0).toString());
    }
}
