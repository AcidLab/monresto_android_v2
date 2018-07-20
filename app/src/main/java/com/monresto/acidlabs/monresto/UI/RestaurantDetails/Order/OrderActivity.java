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
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {

    private Dish dish;
    CollapsingToolbarLayout toolbar_layout;

    @BindView(R.id.dish_name)
    TextView dish_name;

    @BindView(R.id.dish_description)
    TextView dish_description;

    @BindView(R.id.dish_quantity)
    TextView dish_quantity;

    @BindView(R.id.dish_quantity_add)
    ImageView dish_quantity_add;

    @BindView(R.id.dish_quantity_reduce)
    ImageView dish_quantity_reduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get dish information from the caller intent
        Intent i = getIntent();
        dish = i.getParcelableExtra("dish");

        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        getWindow().setLayout(width, height);

        final Toolbar toolbar = findViewById(R.id.toolbar);

        dish_name.setText(dish.getTitle());
        dish_description.setText(dish.getDescription());

        dish_quantity_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish_quantity.setText(Integer.toString(Integer.valueOf(dish_quantity.getText().toString()) + 1));
            }
        });

        dish_quantity_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(dish_quantity.getText().toString()) > 0)
                    dish_quantity.setText(Integer.toString(Integer.valueOf(dish_quantity.getText().toString()) - 1));
            }
        });

        final ImageView img = new ImageView(this);
        Picasso.get().load(dish.getImagePath()).resize(600, 600).centerInside().into(img, new Callback() {
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
