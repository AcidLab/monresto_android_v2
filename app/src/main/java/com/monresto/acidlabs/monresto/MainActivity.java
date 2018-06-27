package com.monresto.acidlabs.monresto;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;

import java.util.ArrayList;


//Testing fetch information from api

public class MainActivity extends AppCompatActivity implements RestaurantAsyncResponse {
    TextView test;

    private FusedLocationProviderClient mFusedLocationClient;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (TextView)findViewById(R.id.textView);
        final ImageView Stores = (ImageView) findViewById(R.id.stores);
        final ImageView Cart = (ImageView) findViewById(R.id.cart);
        final ImageView Profile = (ImageView) findViewById(R.id.profile);
        Stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stores.setImageResource(R.drawable.store_light);
                Cart.setImageResource(R.drawable.cart_light_disabled);
                Profile.setImageResource(R.drawable.user_light_disabled);
            }
        });
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stores.setImageResource(R.drawable.store_light_disabled);
                Cart.setImageResource(R.drawable.cart_light);
                Profile.setImageResource(R.drawable.user_light_disabled);
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stores.setImageResource(R.drawable.store_light_disabled);
                Cart.setImageResource(R.drawable.cart_light_disabled);
                Profile.setImageResource(R.drawable.user_light);
            }
        });

        final RestaurantService service = new RestaurantService(this);
        service.getDetails(245);

    }

    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {
    }

    @Override
    public void onDetailsReceived(Restaurant restaurant) {
        test.setText(restaurant.toString());
    }

    @Override
    public void onMenusReceived(ArrayList<Menu> menus) {

    }

    @Override
    public void onDishesReceived(ArrayList<Dish> dishes) {

    }
}
