package com.monresto.acidlabs.monresto.UI.Homepage.Snacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.HomepageConfig;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnacksActivity extends AppCompatActivity implements RestaurantAsyncResponse {
    RestaurantService restaurantService;
    @BindView(R.id.snacksLayout)
    LinearLayout snacksLayout;
    @BindView(R.id.loader)
    ConstraintLayout loader;
    @BindView(R.id.storeBg)
    ImageView storeBg;
    @BindView(R.id.storeName)
    TextView storeName;
    @BindView(R.id.cart_quantity)
    TextView cart_quantity;
    @BindView(R.id.cart_total)
    TextView cart_total;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.couffin)
    ImageView couffin;

    @BindView(R.id.cart_frame)
    FrameLayout cart_frame;
    private int counter = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_snacks);
        ButterKnife.bind(this);

        // Setting up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(v -> finish());

        restaurantService = new RestaurantService(this);
        restaurantService.getMenus(251);
        restaurantService.getDetails(251);

        Picasso.get().load(HomepageConfig.getInstance().getBusket_image()).into(couffin);
        cart_frame.setOnClickListener(e -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onDetailsReceived(Restaurant restaurant) {
        Picasso.get().load(restaurant.getBackground()).into(storeBg);
        storeName.setText(restaurant.getName());
    }

    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {
    }

    @Override
    public void onMenusReceived(ArrayList<Menu> menus) {
        counter = menus.size();
        for(int i=0; i<menus.size(); i++) {
            restaurantService.getDishes(251,menus.get(i));
        }
    }

    @Override
    public void onDishesReceived(ArrayList<Dish> dishes, Menu menu) {
        counter--;
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
        if(counter == 0){
            ConstraintLayout footer = (ConstraintLayout) View.inflate(this, R.layout.item_footer, null);
            snacksLayout.addView(footer);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartInfo();
    }

    public void updateCartInfo() {
        cart_quantity.setText(String.valueOf(ShoppingCart.getInstance().getItems().size()));
        DecimalFormat dec = new DecimalFormat("#0.00");
        cart_total.setText(dec.format(ShoppingCart.getInstance().getCartSubTotal()) + " DT");
    }
}
