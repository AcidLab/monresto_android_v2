package com.monresto.acidlabs.monresto.UI.RestaurantDetails;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.monresto.acidlabs.monresto.HackViewPager;
import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Review;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.Reviews.ReviewsAdapter;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.Service.Review.ReviewAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Review.ReviewService;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity implements RestaurantAsyncResponse, ReviewAsyncResponse {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pager)
    HackViewPager pager;

    @BindView(R.id.storeBg)
    ImageView storeBg;
    @BindView(R.id.storeName)
    TextView storeName;
    @BindView(R.id.storeState)
    TextView storeState;


    RestaurantDetailsPager adapter;
    List<String> MenusList;
    CharSequence Titles[];

    RestaurantService service;
    ReviewService reviewService;

    HashMap<Menu, ArrayList<Dish>> dishes = new HashMap<Menu, ArrayList<Dish>>();

    Restaurant restaurant;
    ArrayList<Review> reviews;

    int filledDishes; // Used for stability and improvements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

        // Setting up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        setUpClick();

        // Get restaurant information from the caller intent
        Intent i = getIntent();
        restaurant = i.getParcelableExtra("restaurant");

        // Assigning details to views
        Picasso.get().load(restaurant.getBackground()).into(storeBg);
        storeName.setText(restaurant.getName());
        storeState.setText(restaurant.getState());

        filledDishes = 0;

        // Get menus
        service = new RestaurantService(this);
        System.out.println("SPECIAL DEBUG: Getting menus...");
        service.getMenus(restaurant.getId());
    }

    void setUpClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    void setUpTabs() {
        adapter = new RestaurantDetailsPager(this.getSupportFragmentManager(), Titles, Titles.length, restaurant, dishes, reviews);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cart_btn) {
            System.out.println("CART BUTTON PRESSED");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {

    }

    @Override
    public void onMenusReceived(ArrayList<Menu> menus) {
        System.out.println("SPECIAL DEBUG: Menus received !");

        MenusList = new ArrayList<>();
        MenusList.add("Informations");

        for (int j = 0; j < menus.size(); j++) {
            dishes.put(menus.get(j), null);

            MenusList.add(Utilities.decodeUTF(menus.get(j).getTitle()));

            // Generate dishes according to menu
            System.out.println("SPECIAL DEBUG: Menu added to tab, getting dishes...");

            service.getDishes(restaurant.getId(), menus.get(j));
        }

        Titles = MenusList.toArray(new CharSequence[MenusList.size()]);
    }

    @Override
    public void onDishesReceived(ArrayList<Dish> dishes, Menu menu) {

        // Fixing special characters for MENUS
        Menu fixedMenu = menu;
        fixedMenu.setTitle(Utilities.decodeUTF(menu.getTitle()));
        fixedMenu.setDescription(Utilities.decodeUTF(menu.getDescription()));

        this.dishes.put(fixedMenu, dishes);

        filledDishes++;

        setUpTabs();

        if (filledDishes == MenusList.size() - 1) {
            System.out.println("SPECIAL DEBUG: Getting reviews for the restaurant...");
            reviewService = new ReviewService(this);
            reviewService.getAll(restaurant.getId());
        }

    }

    @Override
    public void onComposedDishReceived(Dish dish) {

    }

    @Override
    public void onSpecialitiesReceived(ArrayList<Speciality> specialities) {

    }

    @Override
    public void onReviewsReceived(ArrayList<Review> ReviewList) {
        System.out.println("SPECIAL DEBUG: Reviews received, setting tabs...");
        ListView reviewsList = findViewById(R.id.listReviews);
        reviews = ReviewList;

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviews, this);
        if (reviewsList != null)
            reviewsList.setAdapter(reviewsAdapter);
    }
}