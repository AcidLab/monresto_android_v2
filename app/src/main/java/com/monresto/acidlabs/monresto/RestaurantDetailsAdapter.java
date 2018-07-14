package com.monresto.acidlabs.monresto;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsAdapter extends RecyclerView.Adapter<RestaurantDetailsAdapter.ViewHolder> {

    private ArrayList<Dish> dishes;
    private RestaurantService service;
    private Context context;

    public RestaurantDetailsAdapter(ArrayList<Dish> dishes, Context context) {
        this.dishes = dishes;
        this.context = context;
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

        System.out.println("SPECIAL DEBUG: DISH FAVORITE: " + dishes.get(position).isFavorite());

        // Checks if dish is favorite
        if (dishes.get(position).isFavorite()) {
            Picasso.get().load(R.drawable.heart_filled).into(viewHolder.favorite_btn);
            System.out.println("Dish " + dishes.get(position).getId() + " is favorite");
        }

        service = new RestaurantService(context);

        viewHolder.favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dishes.get(position).setFavorite(true);
                service.setFavorite(dishes.get(position).getId(), true);
                Snackbar.make(view, "Ce plat a été ajouté aux favoris.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Picasso.get().load(R.drawable.heart_filled).into(viewHolder.favorite_btn);

                /*
                ///// To be re-enabled when they add "remove from favorite" to their webservice...
                if(dishes.get(position).isFavorite()) {
                    service.setFavorite(dishes.get(position).getId(), false);
                    dishes.get(position).setFavorite(false);
                    Snackbar.make(view, "Dish ma3adech favorite :(", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Picasso.get().load(R.drawable.heart_empty).into(viewHolder.favorite_btn);
                }
                else {
                }*/
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
