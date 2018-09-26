package com.monresto.acidlabs.monresto.UI.Cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.ObjectSerializer;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order.OrderActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemRecyclerViewAdapter extends RecyclerView.Adapter<CartItemRecyclerViewAdapter.ViewHolder> {

    private ArrayList mData;
    private Context context;


    public CartItemRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_title)
        TextView cart_title;
        @BindView(R.id.cart_option)
        TextView cart_option;
        @BindView(R.id.cart_components)
        TextView cart_components;
        @BindView(R.id.cart_price)
        TextView cart_price;
        @BindView(R.id.cart_quantity)
        TextView cart_quantity;
        @BindView(R.id.cart_picture)
        ImageView cart_picture;
        @BindView(R.id.cart_remove_btn)
        ImageView cart_remove_btn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_cart, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Map.Entry<Dish, ShoppingCart.Options> item = (Map.Entry<Dish, ShoppingCart.Options>) mData.get(position);

        viewHolder.cart_title.setText(item.getKey().getTitle());
        viewHolder.cart_quantity.setText("Quantité: " + item.getValue().getQuantity());
        if (item.getValue().getDimension() != null)
            viewHolder.cart_option.setText(item.getValue().getDimension().getTitle());
        else viewHolder.cart_option.setText("Pas d'options");

        Picasso.get().load(item.getKey().getImagePath()).into(viewHolder.cart_picture);

        int compSize = 0;
        double compPrice = 0;
        for (int i = 0; i < item.getValue().getComponents().size(); i++) {
            compSize += item.getValue().getComponents().get(i).getOptions().size();
            for (int j = 0; j < item.getValue().getComponents().get(i).getOptions().size(); j++) {
                compPrice += item.getValue().getComponents().get(i).getOptions().get(j).getPrice();
            }
        }
        viewHolder.cart_components.setText("+ " + compSize + " suppléments");

        double price = 0;
        price = (item.getKey().getPrice() + compPrice) * item.getValue().getQuantity();

        DecimalFormat dec = new DecimalFormat("#0.00");
        viewHolder.cart_price.setText(dec.format(price) + " DT");
        viewHolder.cart_remove_btn.setOnClickListener(view -> {
            ShoppingCart.getInstance().removeFromCart(item.getKey());
            mData.remove(mData.get(position));
            ((CartActivity) context).update();
            SharedPreferences sharedPreferences = context.getSharedPreferences("itemsList", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        });
        viewHolder.cart_picture.setOnClickListener(e -> {
            //String serialDish = ObjectSerializer.serialize(item.getKey());
            //String serialOptions = ObjectSerializer.serialize(item.getValue());
            //Intent intent = new Intent(context, OrderActivity.class);
            //intent.putExtra("serialDish", serialDish);
            //intent.putExtra("serialOptions", serialOptions);
            //context.startActivity(intent);
        });
    }

    public void setCartItems(Map<Dish, ShoppingCart.Options> items) {
        mData = new ArrayList();
        mData.addAll(items.entrySet());
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }
}
