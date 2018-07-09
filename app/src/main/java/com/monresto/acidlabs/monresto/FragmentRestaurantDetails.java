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
import com.monresto.acidlabs.monresto.Model.Review;
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
    TextView price;
    TextView delivery_price;
    ArrayList<Review> reviews;

    public FragmentRestaurantDetails(Restaurant restaurant, ArrayList<Review> reviews) {
        this.restaurant = restaurant;
        this.reviews = reviews;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_details, container,false);

        // Binding views
        dish_bg = (ImageView) v.findViewById(R.id.dish_bg_id);
        rating = (TextView) v.findViewById(R.id.rating_id);
        delivery_price = (TextView) v.findViewById(R.id.delivery_id);

        // Assigning values
        Picasso.get().load(restaurant.getImage()).into(dish_bg);


        for(int i=0; i<Math.round(restaurant.getRate()); i++) {
            int resID = v.getResources().getIdentifier("avis_id_"+i, "id", v.getContext().getPackageName());
            Picasso.get().load(R.drawable.star_filled).into((ImageView) v.findViewById(resID));
        }
            rating.setText("(" + restaurant.getNbrAvis() + " avis)");
        delivery_price.setText(restaurant.getEstimatedTime() + " minutes | " + Double.toString(restaurant.getDeliveryCost()) + " DT");

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
