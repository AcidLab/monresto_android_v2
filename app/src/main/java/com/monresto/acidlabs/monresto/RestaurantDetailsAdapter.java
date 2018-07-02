package com.monresto.acidlabs.monresto;

import android.support.design.widget.Snackbar;
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(Utilities.decodeUTF(dishes.get(position).getTitle()));

        // Checks if price is unavailable
        if (Double.isNaN(dishes.get(position).getPrice()))
            viewHolder.price.setText("Prix indisponible");
        else viewHolder.price.setText("Prix: " + Double.toString(dishes.get(position).getPrice()) + " DT");

        Picasso.get().load(dishes.get(position).getImagePath()).into(viewHolder.bg_img);

        // Checks if dish is favorite
        if (dishes.get(position).isFavorite())
            Picasso.get().load(R.drawable.heart_filled).into(viewHolder.favorite_btn);

        viewHolder.favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dishes.get(position).isFavorite()) {
                    //dishes.get(position).setFavorite(false);
                    Snackbar.make(view, "Dish ma3adech favorite :(", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Picasso.get().load(R.drawable.heart_empty).into(viewHolder.favorite_btn);
                }
                else {
                    //dishes.get(position).setFavorite(true);
                    Snackbar.make(view, "Dish normalement added to favorite ;D", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Picasso.get().load(R.drawable.heart_filled).into(viewHolder.favorite_btn);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_name_id) TextView name;
        @BindView(R.id.dish_price_id) TextView price;
        @BindView(R.id.dish_bg_id) ImageView bg_img;
        @BindView(R.id.favorite_btn) ImageView favorite_btn;
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
