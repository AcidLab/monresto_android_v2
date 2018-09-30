package com.monresto.acidlabs.monresto.UI.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Checkout.CheckoutActivity;
import com.monresto.acidlabs.monresto.UI.Profile.Address.NewAddressActivity;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;
import com.monresto.acidlabs.monresto.Utilities;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {
    @BindView(R.id.cart_subtotal)
    TextView cart_subtotal;
    @BindView(R.id.cart_delivery)
    TextView cart_delivery;
    @BindView(R.id.cart_total)
    TextView cart_total;
    @BindView(R.id.back_button)
    ImageView back_button;
    @BindView(R.id.cart_items_list)
    RecyclerView cart_items_list;
    @BindView(R.id.cart_empty)
    ConstraintLayout cart_empty;
    @BindView(R.id.orderBtn)
    LinearLayout orderBtn;

    private CartItemRecyclerViewAdapter cartItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);

        back_button.setOnClickListener(view -> {
            finish();
        });

        cart_items_list.setLayoutManager(new LinearLayoutManager(this));
        cartItemAdapter = new CartItemRecyclerViewAdapter(this);
        cart_items_list.setAdapter(cartItemAdapter);
        orderBtn.setOnClickListener(e -> {
            if (User.getInstance() == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("ask_for_select", true);
                startActivityForResult(intent, Config.REQUEST_CODE_CHECKOUT);
            } else {
                if (ShoppingCart.getInstance().isEmpty()) {
                    Toast.makeText(this, "Votre panier est vide", Toast.LENGTH_LONG).show();
                } else if (ShoppingCart.getInstance().getCartSubTotal() < ShoppingCart.getInstance().getMinCartTotal()) {
                    Toast.makeText(this, "Votre panier est inférieur à " + ShoppingCart.getInstance().getMinCartTotal() + " DT", Toast.LENGTH_LONG).show();
                } else {
                    if(!User.getInstance().getAddresses().isEmpty() && User.getInstance().getSelectedAddress()!=null){
                        Intent intent = new Intent(this, CheckoutActivity.class);
                        intent.putExtra("sub-total", ShoppingCart.getInstance().getCartSubTotal());
                        intent.putExtra("delivery", ShoppingCart.getInstance().getCartDelivery());
                        intent.putExtra("total", ShoppingCart.getInstance().getCartSubTotal() + ShoppingCart.getInstance().getCartDelivery());
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(this, SelectAddressActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if ((getSupportFragmentManager() != null)) {
            update();
        }
    }


    public void update() {
        ShoppingCart cart = ShoppingCart.getInstance();
        DecimalFormat dec = new DecimalFormat("#0.00");
        cart_subtotal.setText(dec.format(cart.getCartSubTotal()) + " DT");
        cart_delivery.setText(dec.format(cart.getCartDelivery()) + " DT");
        cart_total.setText(dec.format(cart.getCartDelivery() + cart.getCartSubTotal()) + " DT");

        if (ShoppingCart.getInstance().isEmpty()) {
            Utilities.statusChangerUnavailable(this, "Le panier est vide", cart_empty, cart_items_list);
            cart_items_list.setVisibility(View.VISIBLE);
        } else cart_empty.setVisibility(View.INVISIBLE);

        cartItemAdapter.setCartItems(ShoppingCart.getInstance().getItems());
        cartItemAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_CODE_CHECKOUT) {
            if(User.getInstance()!=null && !User.getInstance().getAddresses().isEmpty() && User.getInstance().getSelectedAddress()!=null)
            {
                Intent intent = new Intent(this, CheckoutActivity.class);
                intent.putExtra("sub-total", ShoppingCart.getInstance().getCartSubTotal());
                intent.putExtra("delivery", ShoppingCart.getInstance().getCartDelivery());
                intent.putExtra("total", ShoppingCart.getInstance().getCartSubTotal() + ShoppingCart.getInstance().getCartDelivery());
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, NewAddressActivity.class);
                startActivity(intent);
            }
        }
    }
}
