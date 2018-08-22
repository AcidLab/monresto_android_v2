package com.monresto.acidlabs.monresto.UI.RestaurantDetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.Reviews.ReviewsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class FragmentRestaurantDetails extends Fragment {

    @BindView(R.id.dish_bg_id)
    ImageView dish_bg;
    @BindView(R.id.rating_id)
    TextView rating;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.delivery_id)
    TextView delivery_price;
    @BindView(R.id.listReviews)
    RecyclerView listReviews;

    ArrayList<Review> reviews;
    Restaurant restaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_details, container,false);

        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            restaurant = (Restaurant) getArguments().get("restaurant");
            reviews = (ArrayList<Review>) getArguments().get("reviews");
        }

        // Assigning values
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getContext());
        listReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        listReviews.setAdapter(reviewsAdapter);
        reviewsAdapter.setReviews(reviews);
        reviewsAdapter.notifyDataSetChanged();

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
