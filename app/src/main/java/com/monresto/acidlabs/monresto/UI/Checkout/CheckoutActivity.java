package com.monresto.acidlabs.monresto.UI.Checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.PaymentMode;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {

    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.imageProfileBack)
    ImageView imageProfileBack;
    @BindView(R.id.paymentMethods)
    Spinner paymentMethods;
    @BindView(R.id.cart_subtotal)
    TextView cart_subtotal;
    @BindView(R.id.cart_delivery)
    TextView cart_delivery;
    @BindView(R.id.cart_total)
    TextView cart_total;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);

        ButterKnife.bind(this);

        address.setText(User.getInstance().getAddresses().get(0).getAdresse());
        imageProfileBack.setOnClickListener(e -> {
            finish();
        });
        cart_subtotal.setText(String.valueOf(getIntent().getExtras().getDouble("sub-total")) + " DT");
        cart_delivery.setText(String.valueOf(getIntent().getExtras().getDouble("delivery")) + " DT");
        cart_total.setText(String.valueOf(getIntent().getExtras().getDouble("total")) + " DT");
        ArrayList<PaymentMode> paymentModes = Monresto.getInstance().findRestaurant(ShoppingCart.getInstance().getCurrentRestaurant()).getPaymentModes();
        ArrayList<String> paymentModesString = new ArrayList<>();
        for (int i=0; i< paymentModes.size();i++)
            paymentModesString.add(paymentModes.get(i).getTitle());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentModesString);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        paymentMethods.setAdapter(adapter);
        //paymentMethods.setSelection(0);

    }
}

