package com.monresto.acidlabs.monresto.UI.Restaurants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RoundedTransformation;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.RestaurantDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.ColorFilterTransformation;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Restaurant> restaurants;
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds
    private int starsFilled;

    public RecyclerViewAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_store, viewGroup , false);

        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.restaurantItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                // Pass data object in the bundle and populate details activity.
                intent.putExtra("restaurant",restaurants.get(viewHolder.getAdapterPosition()));

                Pair<View, String> p1 = Pair.create((View)viewHolder.storeName, "storeName");
                Pair<View, String> p2 = Pair.create((View)viewHolder.storeBg, "storeBg");
                Pair<View, String> p3 = Pair.create((View)viewHolder.storeState, "storeState");

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2, p3);

                context.startActivity(intent, options.toBundle());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Restaurant restaurantItem = restaurants.get(i);

        System.out.println("STORE: " + restaurants.get(i).getName());

        viewHolder.storeName.setText(restaurants.get(i).getName());

        if (restaurants.get(i).getState().equals("open"))
            viewHolder.storeState.setText("OUVERT");
        else viewHolder.storeState.setText("FERMÉ");

        Picasso.get().load(restaurants.get(i).getBackground()).fit().transform(new RoundedTransformation(50,0)).transform(new ColorFilterTransformation(Color.argb(120, 0, 0, 0))).into(viewHolder.storeBg);

        starsFilled = 0;
        viewHolder.restaurant_rating.setText("");
        for(int j=0; j<5; j++) {
            if (starsFilled < restaurants.get(i).getRate())
            {
                viewHolder.restaurant_rating.setText(String.format("%s★", viewHolder.restaurant_rating.getText()));
                starsFilled++;
            }
            else viewHolder.restaurant_rating.setText(String.format("%s✩", viewHolder.restaurant_rating.getText()));
        }

        viewHolder.restaurant_delivery.setText(String.valueOf(restaurants.get(i).getEstimatedTime())+"'");
        // Set the view to fade in
        setFadeAnimation(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName;
        private TextView storeState;
        private ImageView storeBg;
        private TextView restaurant_rating;
        private TextView restaurant_delivery;
        private ConstraintLayout restaurantItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            storeState = itemView.findViewById(R.id.storeState);
            restaurant_delivery = itemView.findViewById(R.id.restaurant_delivery);
            storeBg = itemView.findViewById(R.id.storeBg);
            restaurant_rating = itemView.findViewById(R.id.restaurant_rating);
            restaurantItem = itemView.findViewById(R.id.restaurant_id);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
