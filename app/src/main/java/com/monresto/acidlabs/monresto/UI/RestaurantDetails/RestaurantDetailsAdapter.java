package com.monresto.acidlabs.monresto.UI.RestaurantDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order.OrderActivity;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsAdapter extends RecyclerView.Adapter<RestaurantDetailsAdapter.ViewHolder> {

    private ArrayList<Dish> dishes;
    private Context context;
    private RestaurantService service;


    public RestaurantDetailsAdapter(ArrayList<Dish> dishes, Context context) {
        this.dishes = dishes;
        this.context = context;
    }

    @Override
    public RestaurantDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, null);
        return new ViewHolder(itemLayoutView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(Utilities.decodeUTF(dishes.get(position).getTitle()));

        // Checks if price is unavailable
        if (Double.isNaN(dishes.get(position).getPrice()))
            viewHolder.price.setText("Prix indisponible");
        else
            viewHolder.price.setText("Prix: " + Double.toString(dishes.get(position).getPrice()) + " DT");

        // Loads background image
        Picasso.get().load(dishes.get(position).getImagePath()).into(viewHolder.bg_img);

        // Checks if dish is favorite
        if (dishes.get(position).isFavorite()) {
            Picasso.get().load(R.drawable.heart_filled).into(viewHolder.heart);
        }
        viewHolder.heart.setOnClickListener(view -> {
            if (dishes.get(position).isFavorite()) {
                service = new RestaurantService(context);
                service.setFavorite(dishes.get(position).getId(), false);
                dishes.get(position).setFavorite(false);
                Toast.makeText(context, "Le plat n'est plus dans vos favoris.", Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.heart_empty).into(viewHolder.heart);
            } else {
                service = new RestaurantService(context);
                dishes.get(position).setFavorite(true);
                service.setFavorite(dishes.get(position).getId(), true);
                Toast.makeText(context, "Le plat a été ajouté aux favoris.", Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.heart_filled).into(viewHolder.heart);
            }
        });


        // On click event
        viewHolder.constraintLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra("dish", (Parcelable) dishes.get(viewHolder.getAdapterPosition()));
            context.startActivity(intent);
        });

        // Re-setting layout width because somehow it changes automatically
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        viewHolder.constraintLayout.setLayoutParams(lp);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_name_id)
        TextView name;
        @BindView(R.id.dish_price_id)
        TextView price;
        @BindView(R.id.dish_bg_id)
        ImageView bg_img;
        @BindView(R.id.heart)
        ImageView heart;
        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

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
