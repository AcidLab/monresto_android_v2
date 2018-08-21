package com.monresto.acidlabs.monresto.UI.Checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.PaymentMode;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RadioListAdapter;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {
    @BindView(R.id.address)
    TextView textAddress;
    @BindView(R.id.imageProfileBack)
    ImageView imageProfileBack;
    @BindView(R.id.paymentMethods)
    RecyclerView paymentMethods;
    @BindView(R.id.cart_delivery)
    TextView cart_delivery;
    @BindView(R.id.cart_total)
    TextView cart_total;
    @BindView(R.id.orderBtn)
    ConstraintLayout orderBtn;
    @BindView(R.id.paymentItemUnavailable)
    RecyclerView paymentItemUnavailable;
    @BindView(R.id.deliveryDate)
    RecyclerView deliveryDate;

    private Address address;
    private UserService userService;

    private final int TYPE_PAYMENT_MODE = 1, TYPE_UNAVAILABLE_OPTION = 2, TYPE_DELIVERY_DATE = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);

        ButterKnife.bind(this);

        address = Monresto.getInstance().getAddress();
        textAddress.setText(address.getAdresse());
        textAddress.setOnClickListener(e -> {
            Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivity(intent);
        });

        imageProfileBack.setOnClickListener(e -> {
            finish();
        });

        cart_delivery.setText(String.valueOf(getIntent().getExtras().getDouble("delivery")) + " DT");
        DecimalFormat dec = new DecimalFormat("#0.00");
        cart_total.setText(String.valueOf(dec.format(getIntent().getExtras().getDouble("total"))) + " DT");

        initRecyclerViews();

        userService = new UserService(this);
        int paymentMethod = getItemIdByType(paymentMethods, TYPE_PAYMENT_MODE);
        int orderOptionID = getItemIdByType(paymentMethods, TYPE_UNAVAILABLE_OPTION);
        int deliveryTime = getItemIdByType(paymentMethods, TYPE_DELIVERY_DATE);
        orderBtn.setOnClickListener(e -> {
            userService.submitOrders(User.getInstance().getId(), Monresto.getInstance().getAddress().getId(), ShoppingCart.getInstance().getCurrentRestaurant(), paymentMethod, orderOptionID, deliveryTime);
        });

    }

    void initRecyclerViews() {
        RadioListAdapter radioListAdapter;
        CharSequence[] subjects;
        //1
        ArrayList<PaymentMode> paymentModes = Monresto.getInstance().findRestaurant(ShoppingCart.getInstance().getCurrentRestaurant()).getPaymentModes();
        ArrayList<CharSequence> paymentModesString = new ArrayList<>();

        for (int i = 0; i < paymentModes.size(); i++)
            paymentModesString.add(paymentModes.get(i).getTitle());

        radioListAdapter = new RadioListAdapter(paymentModesString, this);
        paymentMethods.setLayoutManager(new LinearLayoutManager(this));
        paymentMethods.setAdapter(radioListAdapter);

        //2
        subjects = getResources().getStringArray(R.array.purchase_unavailable_options);
        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), this);
        paymentItemUnavailable.setLayoutManager(new LinearLayoutManager(this));
        paymentItemUnavailable.setAdapter(radioListAdapter);

        //3
        subjects = getResources().getStringArray(R.array.delivery_time_options);
        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), this);
        deliveryDate.setLayoutManager(new LinearLayoutManager(this));
        deliveryDate.setAdapter(radioListAdapter);
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
        address = Monresto.getInstance().getAddress();
        textAddress.setText(address.getAdresse());
    }
}
