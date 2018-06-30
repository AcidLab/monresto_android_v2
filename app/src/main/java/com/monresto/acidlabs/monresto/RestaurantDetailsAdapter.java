package com.monresto.acidlabs.monresto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsAdapter extends RecyclerView.Adapter<RestaurantDetailsAdapter.ViewHolder> {

    private ArrayList<Dish> dishes;

    public RestaurantDetailsAdapter(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public RestaurantDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.text.setText(dishes.get(position).getTitle());

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text) TextView text;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
}
