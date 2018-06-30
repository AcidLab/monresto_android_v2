package com.monresto.acidlabs.monresto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

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
                Toast.makeText(context,"Restaurant number: "+viewHolder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, RestaurantDetails.class);
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

        viewHolder.storeName.setText(restaurants.get(i).getName());
        viewHolder.storeState.setText(restaurants.get(i).getState());
        Picasso.get().load(restaurants.get(i).getBackground()).into(viewHolder.storeBg);

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
        private ConstraintLayout restaurantItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = (TextView) itemView.findViewById(R.id.storeName);
            storeState = (TextView) itemView.findViewById(R.id.storeState);
            storeBg = (ImageView) itemView.findViewById(R.id.storeBg);
            restaurantItem = (ConstraintLayout) itemView.findViewById(R.id.restaurant_id);
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
