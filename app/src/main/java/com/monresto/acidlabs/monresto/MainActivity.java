package com.monresto.acidlabs.monresto;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
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
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private FusedLocationProviderClient mFusedLocationClient;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (TextView)findViewById(R.id.textView);



        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentStores(), "Restaurants");
        adapter.AddFragment(new FragmentCart(), "Panier");
        adapter.AddFragment(new FragmentProfile(), "Profil");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.store_light);
        tabLayout.getTabAt(1).setIcon(R.drawable.cart_light);
        tabLayout.getTabAt(2).setIcon(R.drawable.user_light);


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
