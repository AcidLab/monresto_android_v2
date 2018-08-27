package com.monresto.acidlabs.monresto.UI.RestaurantDetails;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.BadgeCountChangeListener;
import com.monresto.acidlabs.monresto.HackViewPager;
import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.UI.Cart.CartActivity;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsActivity extends AppCompatActivity implements RestaurantAsyncResponse {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    HashMap<Menu, ArrayList<Dish>> dishes = new HashMap<Menu, ArrayList<Dish>>();

    Restaurant restaurant;
    int filledDishes; // Used for stability and improvements

    BadgeCountChangeListener badgeCountChangeListener;
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
        toolbar.setNavigationOnClickListener(v -> finish());


        // Get restaurant information from the caller intent
        Intent i = getIntent();
        restaurant = i.getParcelableExtra("restaurant");

        // Assigning details to views
        Picasso.get().load(restaurant.getBackground()).into(storeBg);
        storeName.setText(restaurant.getName());
        if (restaurant.getState().equals("open"))
            storeState.setText("OUVERT");
        else storeState.setText("FERMÉ");

        filledDishes = 0;

        // Get menus
        service = new RestaurantService(this);
        System.out.println("SPECIAL DEBUG: Getting menus...");
        service.getMenus(restaurant.getId());
    }


    void setUpTabs() {
        adapter = new RestaurantDetailsPager(this.getSupportFragmentManager(), Titles, restaurant, dishes);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        badgeCountChangeListener = () -> {
            MenuItem itemCart = menu.findItem(R.id.cart_btn);
            LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
            Utilities.setBadgeCount(this, icon, String.valueOf(ShoppingCart.getInstance().getCount()));
        };

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cart_btn) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
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

        MenusList = new ArrayList<>();
        MenusList.add("Informations");

        for (int j = 0; j < menus.size(); j++) {
            dishes.put(menus.get(j), null);

            MenusList.add(Utilities.decodeUTF(menus.get(j).getTitle()));
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

        for (int i = 0; i < dishes.size(); i++)
            dishes.get(i).setRestoID(restaurant.getId());

        this.dishes.put(fixedMenu, dishes);

        filledDishes++;

        setUpTabs();
    }

    @Override
    public void onComposedDishReceived(Dish dish) {

    }

    @Override
    public void onSpecialitiesReceived(ArrayList<Speciality> specialities) {

    }

    @Override
    public void onServerDown() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(badgeCountChangeListener!=null)
            badgeCountChangeListener.onBadgeCountChanged();
    }
}
