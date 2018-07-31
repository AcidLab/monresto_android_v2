package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Reviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Review;
import com.monresto.acidlabs.monresto.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

public class ReviewsAdapter extends BaseAdapter {
    private int nbReviews;
    private int starsFilled;
    private Context context;
    private ArrayList<Review> reviews;
    TextView name;
    TextView stars;

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

        starsFilled = 0;
        name = view.findViewById(R.id.rating_name);
        name.setText(reviews.get(i).getClientname());

        stars = view.findViewById(R.id.rating_stars);

        for(int j=0; j<5; j++) {
            if (starsFilled < reviews.get(i).getNote())
            {
                stars.setText(String.format("%s★", stars.getText()));
                starsFilled++;
            }
            else stars.setText(String.format("%s✩", stars.getText()));
        }
        return view;
    }
}
