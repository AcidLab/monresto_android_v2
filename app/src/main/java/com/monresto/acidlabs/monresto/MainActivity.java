package com.monresto.acidlabs.monresto;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.HomepageConfig;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Cart.CartActivity;
import com.monresto.acidlabs.monresto.UI.Profile.ProfileActivity;
import com.monresto.acidlabs.monresto.UI.Restaurants.RecyclerViewAdapter;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.monresto.acidlabs.monresto.UI.Maps.MapsActivity.MY_PERMISSIONS_REQUEST_LOCATION;


//Testing fetch information from api

public class MainActivity extends AppCompatActivity implements RestaurantAsyncResponse, UserAsyncResponse, SwipeRefreshLayout.OnRefreshListener, MaterialSearchBar.OnSearchActionListener {
    @BindView(R.id.home_profile_icon)
    ImageView home_profile_icon;
    @BindView(R.id.home_close)
    ImageView home_close;
    @BindView(R.id.couffin)
    ImageView couffin;

    @BindView(R.id.stores_recyclerview)
    RecyclerView stores_recyclerview;
    @BindView(R.id.restaurants_swiper)
    SwipeRefreshLayout restaurants_swiper;
    @BindView(R.id.status_restaurants)
    ConstraintLayout status_restaurants;
    @BindView(R.id.cart_frame)
    FrameLayout cart_frame;
    @BindView(R.id.cart_quantity)
    TextView cart_quantity;
    @BindView(R.id.cart_total)
    TextView cart_total;
    @BindView(R.id.deliveryLabel)
    TextView deliveryLabel;

    private ArrayList<Restaurant> searchList;
    private ArrayList<Speciality> specialities;
    private boolean firstResume;
    private FusedLocationProviderClient mFusedLocationClient;
    private UserService userService;
    private RestaurantService service;
    private RecyclerViewAdapter recyclerViewAdapter;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        restaurants_swiper.setOnRefreshListener(this);
        stores_recyclerview.setNestedScrollingEnabled(false);
        firstResume = true;

        init();

    }

    public void init() {
        gpsTracker = new GPSTracker(this);
        service = new RestaurantService(this);
        userService = new UserService(this);
        List<android.location.Address> addresses;
        Geocoder geocoder = new Geocoder(this);
        try {
            if(User.getInstance()!=null && User.getInstance().getSelectedAddress()!=null)
                deliveryLabel.setText(User.getInstance().getSelectedAddress().getAdresse());
            else{
                addresses = geocoder.getFromLocation(Monresto.getLat(), Monresto.getLon(), 3);
                if (addresses != null && !addresses.isEmpty())
                    deliveryLabel.setText(addresses.get(0).getLocality());
                else
                    deliveryLabel.setText("Adresse inconnue");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Picasso.get().load(HomepageConfig.getInstance().getBusket_image()).into(couffin);

        service.getAll(Monresto.getLat(), Monresto.getLon());
        service.getSpecialities();

        //TODO
        //filterRecylcerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        stores_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        //filtersToggle.setOnClickListener(view -> {
            /*if (filterRecylcerView.getVisibility() == View.VISIBLE)
                filterRecylcerView.setVisibility(View.GONE);
            else filterRecylcerView.setVisibility(View.VISIBLE);*/
        //});

        home_profile_icon.setOnClickListener(view -> {
            Intent intent;
            if (User.getInstance() == null)
                intent = new Intent(this, LoginActivity.class);
            else
                intent = new Intent(this, ProfileActivity.class);

            startActivity(intent);
        });

        cart_frame.setOnClickListener(view -> {
            Intent intent;
            intent = new Intent(this, CartActivity.class);

            startActivity(intent);
        });
        couffin.setOnClickListener(view -> {
            Intent intent;
            intent = new Intent(this, CartActivity.class);

            startActivity(intent);
        });

        home_close.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {
        Monresto.getInstance().setRestaurants(restaurantList);
        if (!restaurantList.isEmpty()) {
            restaurants_swiper.setVisibility(View.VISIBLE);
            status_restaurants.setVisibility(View.INVISIBLE);
            populateRecyclerView(restaurantList);
        } else {
            Utilities.statusChangerUnavailable(this, "Aucun restaurant trouvé", status_restaurants, restaurants_swiper);
        }
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
        this.specialities = specialities;
        FiltersRecyclerViewAdapter filtersRecyclerViewAdapter = new FiltersRecyclerViewAdapter(this);
        //TODO
        //recyclerViewAdapter.
        //filterRecylcerView.setAdapter(filtersRecyclerViewAdapter);
        //filtersRecyclerViewAdapter.setFilters(specialities);
        if (recyclerViewAdapter == null)
            recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerViewAdapter.setSpecialities(specialities);
    }

    @Override
    public void onServerDown() {
        Utilities.statusChanger(this, R.layout.fragment_breakdown, status_restaurants, restaurants_swiper);
    }

    public void populateRecyclerView(ArrayList<Restaurant> restaurantList) {
        if (recyclerViewAdapter == null)
            recyclerViewAdapter = new RecyclerViewAdapter(this, restaurantList);
        else recyclerViewAdapter.setRestaurants(restaurantList);

        stores_recyclerview.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        service.getAll(Monresto.getLat(), Monresto.getLon());
        restaurants_swiper.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        finish();
        /*
        //// SCROLL TO TOP, APPARENTLY NOT LIKED BY EVERYONE...
        LinearLayoutManager layoutManager = (LinearLayoutManager) stores_recyclerview.getLayoutManager();
        assert layoutManager != null;
        if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
            super.onBackPressed();
        } else {
            stores_recyclerview.smoothScrollToPosition(0);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstResume)
            firstResume = false;
        else {
            if (Monresto.locationChanged) {
                Monresto.locationChanged = false;
                service.getAll(Monresto.getLat(), Monresto.getLon());
            }
            updateHomeCart();
            /*Utilities.statusChanger(this,R.layout.fragment_loading, status_restaurants, restaurants_swiper);
              onRefresh();*/
        }

    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if (!enabled)
            onRefresh();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        searchList = new ArrayList<>();
        for (Restaurant restaurant : Monresto.getInstance().getRestaurants()) {
            if (restaurant.getName().toLowerCase().contains(text.toString().toLowerCase())) {
                searchList.add(restaurant);
            }
        }
        if (!searchList.isEmpty()) {
            populateRecyclerView(searchList);
            Utilities.hideKeyboard(this);
        } else
            Toast.makeText(this, "Aucune donnée trouvée", Toast.LENGTH_SHORT).show(); //TODO: change this
    }

    public void searchWithFilters(Speciality speciality) {
        ArrayList<Restaurant> searchList = new ArrayList<>();
        for (Restaurant restaurant : Monresto.getInstance().getRestaurants()) {
            for (Speciality currSpeciality : restaurant.getSpecialities()) {
                if (currSpeciality.getTitle().equals(speciality.getTitle()))
                    searchList.add(restaurant);
            }
        }
        populateRecyclerView(searchList);
    }

    public void resetRecyclerView() {
        populateRecyclerView(Monresto.getInstance().getRestaurants());
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    public void updateHomeCart() {
        cart_quantity.setText(String.valueOf(ShoppingCart.getInstance().getItems().size()));
        cart_total.setText(String.valueOf(ShoppingCart.getInstance().getCartSubTotal()) + " TND");
    }


    @Override
    public void onUserLogin(User user) {
        userService.getDetails(user.getId(), true);
    }

    @Override
    public void onUserDetailsReceived(User user) {
        userService.getAddress(User.getInstance().getId());
    }

    @Override
    public void onAddressListReceived(ArrayList<Address> addresses) {
        if (User.getInstance() != null)
            User.getInstance().setAddresses(addresses);
        Intent intent = new Intent(this, SelectAddressActivity.class);
        startActivity(intent);
    }

}
