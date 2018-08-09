package com.monresto.acidlabs.monresto.UI.Cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.MainActivity;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;
import com.monresto.acidlabs.monresto.Utilities;

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
            if(User.getInstance()==null){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            else{
                //TODO: Checkout
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
        cart_subtotal.setText(String.valueOf(cart.getCartSubTotal()) + " DT");
        cart_delivery.setText(String.valueOf(cart.getCartDelivery()) + " DT");
        cart_total.setText(String.valueOf(cart.getCartDelivery() + cart.getCartSubTotal()) + " DT");

        if (ShoppingCart.getInstance().getItems().isEmpty()) {
            Utilities.statusChangerUnavailable(this,"Le panier est vide",cart_empty,cart_items_list);
            cart_items_list.setVisibility(View.VISIBLE);
        } else cart_empty.setVisibility(View.INVISIBLE);

        cartItemAdapter.setCartItems(ShoppingCart.getInstance().getItems());
        cartItemAdapter.notifyDataSetChanged();


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
    }
}
