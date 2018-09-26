package com.monresto.acidlabs.monresto.UI.Homepage.Snacks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnacksActivity extends AppCompatActivity implements RestaurantAsyncResponse {
    RestaurantService restaurantService;
    @BindView(R.id.snacksLayout)
    LinearLayout snacksLayout;
    @BindView(R.id.loader)
    ConstraintLayout loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_snacks);
        ButterKnife.bind(this);

        restaurantService = new RestaurantService(this);
        restaurantService.getMenus(251);

    }


    @Override
    public void onDetailsReceived(Restaurant restaurant) {
    }

    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {
    }

    @Override
    public void onMenusReceived(ArrayList<Menu> menus) {
        for(int i=0; i<menus.size(); i++) {
            restaurantService.getDishes(251,menus.get(i));
        }
    }

    @Override
    public void onDishesReceived(ArrayList<Dish> dishes, Menu menu) {
        ConstraintLayout container = (ConstraintLayout) View.inflate(this, R.layout.fragment_snacks, null);
        TextView snacksTitle = container.findViewById(R.id.snacksTitle);
        snacksTitle.setText(Utilities.decodeUTF(menu.getTitle()));
        RecyclerView snacksRecycler = container.findViewById(R.id.snacksRecycler);
        if (dishes.isEmpty()) {
            snacksTitle.setVisibility(View.GONE);
            snacksRecycler.setVisibility(View.GONE);
        }
        SnacksAdapter snacksAdapter = new SnacksAdapter(this);
        snacksRecycler.setAdapter(snacksAdapter);
        snacksAdapter.setSnacks(dishes);
        snacksAdapter.notifyDataSetChanged();
        snacksRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loader.setVisibility(View.GONE);
        snacksLayout.addView(container);
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
    public void onServerHighDemand() {

    }

    @Override
    public void onNoRestaurantsFound() {

    }
}
