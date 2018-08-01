package com.monresto.acidlabs.monresto.UI.Profile.Orders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.monresto.acidlabs.monresto.UI.Restaurants.RecyclerViewAdapter;

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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
        if(orders!=null && !orders.isEmpty()){
            order = orders.get(i);
            for(int j=0; j<orders.size();j++){
                dishes = order.getDishesString();
            }
            viewHolder.textOrderDish.setText(dishes);
            viewHolder.textOrderTotal.setText(String.valueOf(order.getOrderPrice()));
        }
    }

    @Override
    public int getItemCount() {
        if(orders==null)
            return 0;
        return orders.size();
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
