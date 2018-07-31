package com.monresto.acidlabs.monresto.UI.Cart;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.MainActivity;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCart extends Fragment {
    @BindView(R.id.cart_subtotal)
    TextView cart_subtotal;
    @BindView(R.id.cart_delivery)
    TextView cart_delivery;
    @BindView(R.id.cart_total)
    TextView cart_total;
    @BindView(R.id.cart_items_list)
    ListView cart_items_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, v);


        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);

        if ((getFragmentManager() != null) && isVisible) {
            update();
        }
    }


    public void update() {
        ShoppingCart cart = ShoppingCart.getInstance();
        cart_subtotal.setText(String.valueOf(cart.getCartSubTotal()) + " DT");
        cart_delivery.setText(String.valueOf(cart.getCartDelivery()) + " DT");
        cart_total.setText(String.valueOf(cart.getCartDelivery() + cart.getCartSubTotal()) + " DT");

        CartItemAdapter cartItemAdapter = new CartItemAdapter(ShoppingCart.getInstance().getItems());
        cart_items_list.setAdapter(cartItemAdapter);

        /*AlertDialog alertDialog = new AlertDialog.Builder(FragmentCart.this.getContext()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();*/
    }
}
