package com.monresto.acidlabs.monresto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.UI.Cart.FragmentCart;
import com.monresto.acidlabs.monresto.UI.Profile.FragmentProfile;
import com.monresto.acidlabs.monresto.UI.Restaurants.FragmentRestaurant;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import java.util.ArrayList;


//Testing fetch information from api

public class MainActivity extends AppCompatActivity implements RestaurantAsyncResponse {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ArrayList<Restaurant> restaurants;
    private FragmentRestaurant fragmentRestaurants;
    private FragmentProfile fragmentProfile;
    private FragmentCart fragmentCart;

    private FusedLocationProviderClient mFusedLocationClient;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewPager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        RestaurantService service = new RestaurantService(this);
        service.getAll(0, 0);

        fragmentRestaurants = new FragmentRestaurant();
        fragmentCart = new FragmentCart();
        fragmentProfile = new FragmentProfile();

        adapter.AddFragment(fragmentRestaurants, "Restaurants");
        adapter.AddFragment(fragmentCart, "Panier");
        adapter.AddFragment(fragmentProfile, "Profil");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.store_light);
        tabLayout.getTabAt(1).setIcon(R.drawable.cart_light);
        tabLayout.getTabAt(2).setIcon(R.drawable.user_light);
    }
    //Intent intent = new Intent(this, LoginActivity.class);
    // startActivity(intent);


    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {
        fragmentRestaurants.updateList(restaurantList);
    }


    @Override
    public void onMenusReceived(ArrayList<Menu> menus) {

    }


    @Override
    public void onDishesReceived(ArrayList<Dish> dishes, Menu menu) {

    }

    @Override
    public void onComposedDishReceived(Dish dish) {

    }

    @Override
    public void onSpecialitiesReceived(ArrayList<Speciality> specialities) {

    }
}
