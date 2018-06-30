package com.monresto.acidlabs.monresto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Service.Review.ReviewService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class FragmentRestaurantDetails extends Fragment {

    Restaurant restaurant;
    ImageView dish_bg;
    TextView rating;
    TextView price;
    TextView delivery_price;

    public FragmentRestaurantDetails(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.restaurant_details_fragment, container,false);

        // Binding views
        dish_bg = (ImageView) v.findViewById(R.id.dish_bg_id);
        rating = (TextView) v.findViewById(R.id.rating_id);
        price = (TextView) v.findViewById(R.id.price_id);
        delivery_price = (TextView) v.findViewById(R.id.delivery_id);

        // Assigning values
        Picasso.get().load(restaurant.getImage()).into(dish_bg);
        rating.setText(restaurant.getRate() + " ★ (" + restaurant.getNbrAvis() + " avis)");
        price.setText(Double.toString(restaurant.getMinimalPrice()) + " DT");
        delivery_price.setText(restaurant.getEstimatedTime() + " minutes à " + Double.toString(restaurant.getDeliveryCost()) + " DT");

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
