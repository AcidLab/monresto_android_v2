package com.monresto.acidlabs.monresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.squareup.picasso.Picasso;

public class RestaurantDetails extends AppCompatActivity {

    Restaurant restaurant;
    ImageView storeBg;
    TextView storeName;
    TextView storeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Bundle extras = getIntent().getExtras();
        restaurant = new Restaurant(extras.getInt("Id"),
                extras.getString("Name"),
                extras.getString("Background"),
                extras.getString("State"),
                extras.getInt("Opinion")
                );

        storeBg = (ImageView) findViewById(R.id.storeBg);
        Picasso.get().load(restaurant.getBackground()).into(storeBg);

        storeName = (TextView) findViewById(R.id.storeName);
        storeName.setText(restaurant.getName());

        storeState = (TextView) findViewById(R.id.storeState);
        storeState.setText(restaurant.getState());
    }
}
