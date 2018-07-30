package com.monresto.acidlabs.monresto.UI.Cart;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemAdapter extends BaseAdapter {

    private final ArrayList mData;

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

    public CartItemAdapter(Map<Dish, ShoppingCart.Options> items) {
        mData = new ArrayList();
        mData.addAll(items.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final View result;

        if (view == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
            ButterKnife.bind(this, result);
        } else {
            result = view;
        }

        Map.Entry<Dish, ShoppingCart.Options> item = (Map.Entry<Dish, ShoppingCart.Options>) getItem(position);

        // TODO replace findViewById by ViewHolder
        cart_title.setText(item.getKey().getTitle());
        if (item.getValue().getDimension() != null)
            cart_option.setText(item.getValue().getDimension().getTitle());
        else cart_option.setText("Pas d'options");
        cart_components.setText("+ " + String.valueOf(item.getValue().getComponents().size()) + " suppléments");
        Picasso.get().load(item.getKey().getImagePath()).into(cart_picture);
        cart_price.setText(String.valueOf(item.getKey().getPrice()) + " DT");


        return result;
    }
}
