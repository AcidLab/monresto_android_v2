package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Reviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Review;
import com.monresto.acidlabs.monresto.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

public class ReviewsAdapter extends BaseAdapter {
    private int nbReviews;
    private Context context;
    private ArrayList<Review> reviews;
    TextView name;
    RatingBar ratingBar;

    public ReviewsAdapter (ArrayList<Review> ReviewList, Context context) {
        reviews = ReviewList;
        nbReviews = reviews.size();
        this.context = context;
    }

    @Override
    public int getCount() {
        return nbReviews;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_review, null);

        name = view.findViewById(R.id.rating_name);
        name.setText(reviews.get(i).getClientname());

        ratingBar = view.findViewById(R.id.ratingBar);

        ratingBar.setRating((float)reviews.get(i).getNote());
        ratingBar.setIsIndicator(true);

        return view;
    }
}
