package com.monresto.acidlabs.monresto.UI.Profile.Orders;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Order;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RoundedTransformation;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

        ImageView[] pending_views;
        int [] pending_images;
        int status = 0;
        boolean toggle = false;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            pending_views = new ImageView[5];
            pending_images = new int[5];

            pending_views[0] = pending_status_1;
            pending_views[1] = pending_status_2;
            pending_views[2] = pending_status_3;
            pending_views[3] = pending_status_4;
            pending_views[4] = pending_status_5;


            pending_images[0] = R.drawable.delivery_variant_1;
            pending_images[1] = R.drawable.delivery_variant_2;
            pending_images[2] = R.drawable.delivery_variant_3;
            pending_images[3] = R.drawable.delivery_variant_4;
            pending_images[4] = R.drawable.delivery_variant_5;




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
        order = orders.get(orders.size()-(i+1));
        dishes = order.getDishesString();
        viewHolder.textRestoName.setText(Utilities.decodeUTF(order.getRestoName()));
        viewHolder.textOrderDish.setText(Utilities.decodeUTF(dishes));
        viewHolder.textOrderTotal.setText(String.format("%sDT", String.valueOf(order.getOrderPrice())));
        Picasso.get().load(order.getRestoImagePath()).transform(new RoundedTransformation(80, 0)).into(viewHolder.imageViewLogo);

        switch (order.getStatus()) {
            case "EN_ATTENTE":
                viewHolder.status = 0;
                break;
            default:
                viewHolder.status = 0;
                break;
        }

        for (int j = 0; j < 5; j++) {
            if (j > viewHolder.status){
                Picasso.get().load(viewHolder.pending_images[j]).into(viewHolder.pending_views[j]);
                viewHolder.pending_views[j].setColorFilter(Color.LTGRAY);
            }
            else{
                Picasso.get().load(viewHolder.pending_images[j]).into(viewHolder.pending_views[j]);
                viewHolder.pending_views[j].setColorFilter(Color.parseColor("#33b998"));
            }
        }

        viewHolder.textOrderStatus.setText(order.getStatus().replace("_", " "));
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
