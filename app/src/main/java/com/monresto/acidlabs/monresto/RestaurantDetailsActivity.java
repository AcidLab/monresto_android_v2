package com.monresto.acidlabs.monresto;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Review;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.Service.Review.ReviewAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Review.ReviewService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

        // Uncomment this later after you setup the toolbar
        //setSupportActionBar(toolbar);
        setUpClick();

        // Get restaurant information from the caller intent
        Intent i = getIntent();
        restaurant = (Restaurant) i.getParcelableExtra("restaurant");

        // Assigning details to views
        Picasso.get().load(restaurant.getBackground()).into(storeBg);
        storeName.setText(restaurant.getName());
        storeState.setText(restaurant.getState());

        // Get menus
        service = new RestaurantService(this);
        service.getMenus(restaurant.getId());

        // TODO: 30-Jun-18  GET REVIEWS AND PASS THEM TO PAGER
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
        //getMenuInflater().inflate(R.layout.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {

    }

    @Override
    public void onMenusReceived(ArrayList<Menu> menus) {
        MenusList = new ArrayList<String>();
        MenusList.add("Informations");

        for (int j = 0; j < menus.size(); j++) {
            dishes.put(menus.get(j), null);

            MenusList.add(Utilities.decodeUTF(menus.get(j).getTitle()));

            System.out.println("Just added to the menus: " + menus.get(j).getTitle());

            // Generate dishes according to menu
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


        if (this.dishes.size() == MenusList.size() - 1) {
            reviewService = new ReviewService(this);
            reviewService.getAll(restaurant.getId());
        }
    }

    @Override
    public void onComposedDishReceived(Dish dish) {

    }

    @Override
    public void onReviewsReceived(ArrayList<Review> ReviewList) {
        reviews = ReviewList;
        setUpTabs();
    }
}
