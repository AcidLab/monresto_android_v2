package com.monresto.acidlabs.monresto.UI.Profile.Orders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Order;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RoundedTransformation;
import com.monresto.acidlabs.monresto.UI.Restaurants.RecyclerViewAdapter;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orders;

    public OrderRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textOrderDish)
        TextView textOrderDish;
        @BindView(R.id.textOrderTotal)
        TextView textOrderTotal;
        @BindView(R.id.textOrderStatus)
        TextView textOrderStatus;
        @BindView(R.id.imageRestoLogo)
        ImageView imageViewLogo;
        @BindView(R.id.textRestoName)
        TextView textRestoName;
        @BindView(R.id.pending_status_1)
        ImageView pending_status_1;
        @BindView(R.id.pending_status_2)
        ImageView pending_status_2;
        @BindView(R.id.pending_status_3)
        ImageView pending_status_3;
        @BindView(R.id.pending_status_4)
        ImageView pending_status_4;
        @BindView(R.id.pending_status_5)
        ImageView pending_status_5;

        ImageView[] pending_images;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            pending_images = new ImageView[5];
            pending_images[0] = pending_status_1;
            pending_images[1] = pending_status_2;
            pending_images[2] = pending_status_3;
            pending_images[3] = pending_status_4;
            pending_images[4] = pending_status_5;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.fragment_profile_orders_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String dishes = "";
        Order order;
        if (orders != null && !orders.isEmpty()) {
            order = orders.get(i);
            for (int j = 0; j < orders.size(); j++) {
                dishes = order.getDishesString();
            }
            viewHolder.textRestoName.setText(Utilities.decodeUTF(order.getRestoName()));
            viewHolder.textOrderDish.setText(Utilities.decodeUTF(dishes));
            viewHolder.textOrderTotal.setText(String.format("%sDT", String.valueOf(order.getOrderPrice())));
            Picasso.get().load(order.getRestoImagePath()).transform(new RoundedTransformation(80, 0)).into(viewHolder.imageViewLogo);

            int status = 0;
            switch (order.getStatus()) {
                default:
                    status = 0;
                    break;
            }
            for (int j = 0; j < 5; j++) {
                if(j>status)
                    viewHolder.pending_images[j].setColorFilter(Color.LTGRAY);
                else
                    viewHolder.pending_images[j].setColorFilter(Color.GREEN);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (orders == null)
            return 0;
        return orders.size();
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
