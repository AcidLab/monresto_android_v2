package com.monresto.acidlabs.monresto.UI.Profile.Favorites;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RoundedTransformation;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order.OrderActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishesRecyclerViewAdapter extends RecyclerView.Adapter<DishesRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Dish> dishes;

    public DishesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dish_name_id)
        TextView dish_name_id;
        @BindView(R.id.dish_price_id)
        TextView dish_price_id;
        @BindView(R.id.dish_bg_id)
        ImageView dish_bg;
        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_dish, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Dish dish;
        if (dishes != null && !dishes.isEmpty()) {
            dish = dishes.get(i);
            viewHolder.dish_name_id.setText(dish.getTitle());
            viewHolder.dish_price_id.setText(String.format("%s DT", String.valueOf(dish.getPrice())));
            Picasso.get().load(dish.getImagePath()).transform(new RoundedTransformation(80, 0)).into(viewHolder.dish_bg);
            viewHolder.constraintLayout.setOnClickListener(e -> {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("dish", dish);
                context.startActivity(intent);
            });

        }
    }

    @Override
    public int getItemCount() {
        if (dishes == null)
            return 0;
        return dishes.size();
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }
}
