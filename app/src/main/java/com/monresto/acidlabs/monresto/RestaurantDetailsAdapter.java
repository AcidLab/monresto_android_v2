package com.monresto.acidlabs.monresto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
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

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.name.setText(Utilities.decodeUTF(dishes.get(position).getTitle()));
        viewHolder.price.setText(Double.toString(dishes.get(position).getPrice()));
        Picasso.get().load(dishes.get(position).getImagePath()).into(viewHolder.bg_img);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_name_id) TextView name;
        @BindView(R.id.dish_price_id) TextView price;
        @BindView(R.id.dish_bg_id) ImageView bg_img;
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
