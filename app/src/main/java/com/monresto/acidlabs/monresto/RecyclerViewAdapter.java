package com.monresto.acidlabs.monresto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<Restaurant> restaurants;

    public RecyclerViewAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_store, viewGroup , false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.storeName.setText(restaurants.get(i).getName());
        viewHolder.storeState.setText(restaurants.get(i).getState());
        Picasso.get().load(restaurants.get(i).getBackground()).into(viewHolder.storeBg);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName;
        private TextView storeState;
        private ImageView storeBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = (TextView) itemView.findViewById(R.id.storeName);
            storeState = (TextView) itemView.findViewById(R.id.storeState);
            storeBg = (ImageView) itemView.findViewById(R.id.storeBg);
        }
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
