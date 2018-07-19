package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class OrderActivity extends AppCompatActivity {

    private Dish dish;
    CollapsingToolbarLayout toolbar_layout;
    CoordinatorLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get dish information from the caller intent
        Intent i = getIntent();
        dish = i.getParcelableExtra("dish");

        setContentView(R.layout.activity_order);
        getWindow().setLayout(width, height);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(dish.getTitle());
        System.out.println(dish.getImagePath());

        final ImageView img = new ImageView(this);
        Picasso.get().load(dish.getImagePath()).resize(600, 600).centerCrop().into(img, new Callback() {
                    @Override
                    public void onSuccess() {
                        toolbar_layout = findViewById(R.id.toolbar_layout);
                        toolbar_layout.setBackground(img.getDrawable());
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        setSupportActionBar(toolbar);

    }
}
