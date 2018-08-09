package com.monresto.acidlabs.monresto.UI.Restaurants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
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
                Pair<View, String> p2 = Pair.create((View)viewHolder.store_bg, "store_bg");
                Pair<View, String> p3 = Pair.create((View)viewHolder.storeState, "storeState");

                //ActivityOptionsCompat options = ActivityOptionsCompat.
                  //      makeSceneTransitionAnimation((Activity) context, p1, p2, p3);

                context.startActivity(intent);//, options.toBundle());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Restaurant restaurantItem = restaurants.get(i);

        // Set the view to fade in
        setFadeAnimation(viewHolder.itemView);

        Picasso.get().load(restaurantItem.getBackground()).fit().transform(new ColorFilterTransformation(Color.argb(120, 0, 0, 0))).into(viewHolder.store_bg, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                viewHolder.storeName.setText(restaurantItem.getName());

                if (restaurantItem.getState().equals("open"))
                    viewHolder.storeState.setText("OUVERT");
                else viewHolder.storeState.setText("FERMÉ");
                viewHolder.ratingBar.setRating((float)restaurantItem.getRate());
                viewHolder.ratingBar.setIsIndicator(true);
                viewHolder.restaurant_delivery.setText(String.valueOf(restaurantItem.getEstimatedTime())+"'");

            }

            @Override
            public void onError(Exception ex) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName;
        private TextView storeState;
        private ImageView store_bg;
        private TextView restaurant_delivery;
        private ConstraintLayout restaurantItem;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            storeState = itemView.findViewById(R.id.storeState);
            restaurant_delivery = itemView.findViewById(R.id.restaurant_delivery);
            store_bg = itemView.findViewById(R.id.store_bg);
            restaurantItem = itemView.findViewById(R.id.restaurant_id);
            ratingBar = itemView.findViewById(R.id.item_store_rating);
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
