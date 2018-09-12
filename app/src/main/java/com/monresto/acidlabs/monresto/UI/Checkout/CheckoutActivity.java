package com.monresto.acidlabs.monresto.UI.Checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.PaymentMode;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RadioListAdapter;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Profile.ProfileActivity;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity implements UserAsyncResponse {
    @BindView(R.id.imageProfileBack)
    ImageView imageProfileBack;
    @BindView(R.id.cart_delivery)
    TextView cart_delivery;
    @BindView(R.id.cart_total)
    TextView cart_total;
    @BindView(R.id.orderBtn)
    ConstraintLayout orderBtn;
    @BindView(R.id.orderLoading)
    ActionProcessButton orderLoading;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private Address address;
    private UserService userService;

    private final int TYPE_PAYMENT_MODE = 1, TYPE_UNAVAILABLE_OPTION = 2, TYPE_DELIVERY_DATE = 3;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);

        ButterKnife.bind(this);

        orderLoading.setMode(ActionProcessButton.Mode.ENDLESS);
        orderLoading.setProgress(0);


        imageProfileBack.setOnClickListener(e -> {
            finish();
        });

        cart_delivery.setText(String.valueOf(getIntent().getExtras().getDouble("delivery")) + " DT");
        DecimalFormat dec = new DecimalFormat("#0.00");
        cart_total.setText(String.valueOf(dec.format(getIntent().getExtras().getDouble("total"))) + " DT");

        //initRecyclerViews();

        userService = new UserService(this);

        orderLoading.setOnClickListener(e -> {
            int paymentMethod = getItemIdByType(((FragmentDelivery)adapter.getItem(0)).getPaymentMethods(), TYPE_PAYMENT_MODE);
            int orderOptionID = getItemIdByType(((FragmentDelivery)adapter.getItem(0)).getItemUnavailable(), TYPE_UNAVAILABLE_OPTION);
            int deliveryTime = getItemIdByType(((FragmentDelivery)adapter.getItem(0)).getDeliveryDate(), TYPE_DELIVERY_DATE);

            orderLoading.setProgress(1);
            userService.submitOrders(User.getInstance().getId(), User.getInstance().getSelectedAddress().getId(), ShoppingCart.getInstance().getCurrentRestaurant(), paymentMethod, orderOptionID, deliveryTime);
            //onSubmitOrder(true, 89551);
        });

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentDelivery(), "Delivery");
        view_pager.setAdapter(adapter);

    }



    int getItemIdByType(RecyclerView view, int type) {
        int option = ((RadioListAdapter) (Objects.requireNonNull(view.getAdapter()))).getSelectedItem();
        switch (type) {
            case TYPE_PAYMENT_MODE:
                return option;
            case TYPE_UNAVAILABLE_OPTION:
                return option;
            case TYPE_DELIVERY_DATE:
                return option;
            default:
                return -1;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //address = User.getInstance().getSelectedAddress();
        //textAddress.setText(address.getAdresse());
    }

    @Override
    public void onSubmitOrder(boolean success) {
        if (success) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            ShoppingCart.getInstance().clear();
            finish();
        } else {
            orderLoading.setProgress(-1);
        }
    }

    @Override
    public void onSubmitOrder(boolean success, int orderID) {
        if (success) {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("orderID", orderID);
            startActivity(intent);
            ShoppingCart.getInstance().clear();
            finish();
        } else {
            orderLoading.setProgress(-1);
        }
    }
}
