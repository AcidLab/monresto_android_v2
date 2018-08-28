package com.monresto.acidlabs.monresto.UI.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.RestaurantDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_EMPTY = 2;

    private Context context;
    private List<Restaurant> restaurants;
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds
    private ArrayList<Speciality> specialities;
    ViewHolder header;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public RecyclerViewAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int viewType) {
        View v;
        final ViewHolder viewHolder;
        if (viewType == TYPE_HEADER) {
            if (header == null) {
                v = LayoutInflater.from(context).inflate(R.layout.item_restaurants_header, viewGroup, false);
                header = new HolderHeader(v);
                ((HolderHeader) header).setSearchBarContext(context);
                ((HolderHeader) header).initFilterRecyclerView(context);
                ((HolderHeader) header).setSpecialities(specialities);
            }
            viewHolder = header;


        } else if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.item_store, viewGroup, false);
            viewHolder = new HolderItem(v);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.fragment_unavailable, viewGroup, false);
            viewHolder = new HolderItem(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == TYPE_ITEM) {
            final Restaurant restaurantItem = restaurants.get(i - 1);
            setFadeAnimation(viewHolder.itemView);

            Picasso.get().load(restaurantItem.getBackground()).fit().into(((HolderItem) viewHolder).store_bg, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    ((HolderItem) viewHolder).storeName.setText(restaurantItem.getName());

                    if (restaurantItem.getState().equals("open"))
                        ((HolderItem) viewHolder).storeState.setText("OUVERT");
                    else ((HolderItem) viewHolder).storeState.setText("FERMÉ");
                    ((HolderItem) viewHolder).ratingBar.setRating((float) restaurantItem.getRate());
                    ((HolderItem) viewHolder).ratingBar.setIsIndicator(true);
                    ((HolderItem) viewHolder).restaurant_delivery.setText(String.valueOf(restaurantItem.getEstimatedTime()) + "'");
                    ((HolderItem) viewHolder).restaurantItem.setOnClickListener(e -> {
                        Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                        intent.putExtra("restaurant", restaurantItem);

                        context.startActivity(intent);
                    });
                }

                @Override
                public void onError(Exception ex) {

                }
            });
        } else if (viewHolder.getItemViewType() == TYPE_HEADER) {
            ((HolderHeader) viewHolder).filtersToggle.setOnClickListener(e -> {
                Intent intent = new Intent(context, FilterActivity.class);
                ((MainActivity) context).startActivityForResult(intent, Config.REQUEST_CODE_FILTER_SELECT);
            });
        }
    }

    @Override
    public int getItemCount() {
        if(restaurants.isEmpty())
            return 2;
        return restaurants.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        if (restaurants.isEmpty() && position == 1)
            return TYPE_EMPTY;

        return TYPE_ITEM;
    }

    public static class HolderItem extends ViewHolder {
        TextView storeName;
        TextView storeState;
        ImageView store_bg;
        TextView restaurant_delivery;
        ConstraintLayout restaurantItem;
        RatingBar ratingBar;

        HolderItem(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            storeState = itemView.findViewById(R.id.storeState);
            restaurant_delivery = itemView.findViewById(R.id.restaurant_delivery);
            store_bg = itemView.findViewById(R.id.store_bg);
            restaurantItem = itemView.findViewById(R.id.restaurant_id);
            ratingBar = itemView.findViewById(R.id.item_store_rating);
        }
    }

    public static class HolderHeader extends ViewHolder {
        ImageView filtersToggle;
        MaterialSearchBar searchBar;
        RecyclerView filterRecylcerView;
        ArrayList<Speciality> specialities;
        SpecialityRecyclerViewAdapter specialityRecyclerViewAdapter;

        HolderHeader(@NonNull View itemView) {
            super(itemView);
            filtersToggle = itemView.findViewById(R.id.filtersToggle);
            searchBar = itemView.findViewById(R.id.searchBar);
            filterRecylcerView = itemView.findViewById(R.id.filterRecylcerView);

        }

        void setSearchBarContext(Context context) {
            searchBar.setOnSearchActionListener((MaterialSearchBar.OnSearchActionListener) context);
        }

        void initFilterRecyclerView(Context context) {
            filterRecylcerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            specialityRecyclerViewAdapter = new SpecialityRecyclerViewAdapter(context);
            filterRecylcerView.setAdapter(specialityRecyclerViewAdapter);
        }

        void setSpecialities(ArrayList<Speciality> specialities) {
            this.specialities = specialities;
            specialityRecyclerViewAdapter.setFilters(specialities);
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

    public void setSpecialities(ArrayList<Speciality> specialities) {
        this.specialities = specialities;
        notifyDataSetChanged();
    }
}
