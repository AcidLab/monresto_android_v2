package com.monresto.acidlabs.monresto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Review;
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
        System.out.println("SPECIAL DEBUG: CURRENT REVIEW IS MADE BY - " + reviews.get(i).getClientname() + " & " + reviews.get(i).getNote() + " & " + reviews.get(i).getReview());

        starsFilled = 0;
        name = (TextView)view.findViewById(R.id.rating_name);
        name.setText(reviews.get(i).getClientname());

        stars = (TextView)view.findViewById(R.id.rating_stars);

        for(int j=0; j<5; j++) {
            if (starsFilled < reviews.get(i).getNote())
            {
                stars.setText(stars.getText() + "★");
                starsFilled++;
            }
            else stars.setText(stars.getText() + "☆");

        }

        return view;
    }
}
