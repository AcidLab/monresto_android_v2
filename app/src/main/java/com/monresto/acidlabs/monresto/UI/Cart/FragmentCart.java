package com.monresto.acidlabs.monresto.UI.Cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container,false);

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


    public void update(){
        ShoppingCart cart = ShoppingCart.getInstance();
        cart_subtotal.setText(String.valueOf(cart.getCartSubTotal()) + " DT");
        cart_delivery.setText(String.valueOf(cart.getCartDelivery()) + " DT");
    }
}
