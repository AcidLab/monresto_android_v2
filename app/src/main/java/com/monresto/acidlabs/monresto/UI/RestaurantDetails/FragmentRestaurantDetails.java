package com.monresto.acidlabs.monresto.UI.RestaurantDetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Review;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Review.ReviewAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Review.ReviewService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class FragmentRestaurantDetails extends Fragment {

    Restaurant restaurant;
    ImageView dish_bg;
    TextView rating;
    RatingBar ratingBar;
    TextView price;
    TextView delivery_price;
    ArrayList<Review> reviews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_details, container,false);

        if (getArguments() != null) {
            restaurant = (Restaurant) getArguments().get("restaurant");
            reviews = (ArrayList<Review>) getArguments().get("reviews");
        }

        // Binding views
        dish_bg = v.findViewById(R.id.dish_bg_id);
        rating = v.findViewById(R.id.rating_id);
        delivery_price = v.findViewById(R.id.delivery_id);
        ratingBar = v.findViewById(R.id.ratingBar);

        // Assigning values
        Picasso.get().load(restaurant.getImage()).into(dish_bg);

        ratingBar.setRating((float)restaurant.getRate());
        ratingBar.setIsIndicator(true);

        rating.setText("(" + restaurant.getNbrAvis() + " avis)");
        delivery_price.setText(restaurant.getEstimatedTime() + " minutes | " + Double.toString(restaurant.getDeliveryCost()) + " DT");

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
