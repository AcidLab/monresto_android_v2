package com.monresto.acidlabs.monresto.UI.Checkout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dd.processbutton.iml.ActionProcessButton;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.PaymentMode;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RadioListAdapter;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Profile.ProfileActivity;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity implements UserAsyncResponse, RestaurantAsyncResponse {
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
    @BindView(R.id.livraison)
    RadioButton livraison;
    @BindView(R.id.emporter)
    RadioButton emporter;

    @BindView(R.id.address)
    TextView textAddress;
    @BindView(R.id.paymentMethods)
    RecyclerView paymentMethods;
    @BindView(R.id.paymentItemUnavailable)
    RecyclerView paymentItemUnavailable;
    @BindView(R.id.deliveryDate)
    RecyclerView deliveryDate;
    @BindView(R.id.timePicker)
    TimePicker timePicker;
    @BindView(R.id.linearLayout11)
    LinearLayout addressLayout;
    @BindView(R.id.checkout_scroll_view)
    NestedScrollView checkout_scroll_view;
    @BindView(R.id.verify_btn)
    Button verify_btn;
    @BindView(R.id.promo_text)
    TextView promo_text;


    private Address address;
    private int type = 0;
    private String hour = "";
    private UserService userService;

    private final int TYPE_PAYMENT_MODE = 1, TYPE_UNAVAILABLE_OPTION = 2, TYPE_DELIVERY_DATE = 3;
    private ViewPagerAdapter adapter;

    private int deliveryStart = 12;
    private int deliveryEnd = 22;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        //Declare
        userService = new UserService(this);

        //Init
        orderLoading.setMode(ActionProcessButton.Mode.ENDLESS);
        orderLoading.setProgress(0);

        DecimalFormat dec = new DecimalFormat("#0.00");
        cart_delivery.setText(String.valueOf(getIntent().getExtras().getDouble("delivery")) + " DT");
        cart_total.setText(String.valueOf(dec.format(getIntent().getExtras().getDouble("total"))) + " DT");

        address = User.getInstance().getSelectedAddress();
        textAddress.setText(address.getAdresse());

        initRecyclerViews();


        //Event handlers
        imageProfileBack.setOnClickListener(e -> {

            if (ShoppingCart.getInstance().getRestoID() == 370)
                ShoppingCart.getInstance().clear();

            finish();
        });

        livraison.setOnCheckedChangeListener((e1, e2) -> {
            if (e1.isChecked()) {
                addressLayout.setVisibility(View.VISIBLE);
                cart_delivery.setText(String.valueOf(getIntent().getExtras().getDouble("delivery")) + " DT");
                cart_total.setText(String.valueOf(dec.format(getIntent().getExtras().getDouble("total"))) + " DT");
                type = 0;
            } else {
                addressLayout.setVisibility(View.GONE);
                cart_delivery.setText("0 DT");
                double deliveryCost = getIntent().getExtras().getDouble("delivery");
                cart_total.setText(String.valueOf(dec.format(getIntent().getExtras().getDouble("total") - deliveryCost)) + " DT");
                type = 1;
            }
        });

        orderLoading.setOnClickListener(e -> {
            int paymentMethod = getItemIdByType(paymentMethods, TYPE_PAYMENT_MODE);
            int orderOptionID = getItemIdByType(paymentItemUnavailable, TYPE_UNAVAILABLE_OPTION);
            int deliveryTime = getItemIdByType(deliveryDate, TYPE_DELIVERY_DATE);
            if (deliveryTime > 1) {
                hour = timePicker.getCurrentHour() < 12 ? "0" : "" + timePicker.getCurrentHour() + timePicker.getCurrentMinute();
            }
            String promo = promo_text.getText().toString();

            orderLoading.setProgress(1);

            if (User.getInstance() != null)
                userService.submitOrders(User.getInstance().getId(), 0, User.getInstance().getSelectedAddress().getId(), ShoppingCart.getInstance().getCurrentRestaurant(), promo, paymentMethod, orderOptionID, deliveryTime, hour, ShoppingCart.getInstance().getOrdersJson());
        });

        RestaurantService restaurantService = new RestaurantService(this);
        verify_btn.setOnClickListener(e -> restaurantService.verifyPromo(promo_text.getText().toString()));
    }

    void initRecyclerViews() {
        RadioListAdapter radioListAdapter;
        CharSequence[] subjects;

        //1
        subjects = getResources().getStringArray(R.array.payment_methods);

        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), this,1);

        if (ShoppingCart.getInstance().getRestoID() == 370) {
            radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), this,1,0);

        }


        paymentMethods.setLayoutManager(new LinearLayoutManager(this));
        paymentMethods.setAdapter(radioListAdapter);

        //2
        subjects = getResources().getStringArray(R.array.purchase_unavailable_options);
        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), this,0);
        paymentItemUnavailable.setLayoutManager(new LinearLayoutManager(this));
        paymentItemUnavailable.setAdapter(radioListAdapter);

        //3
        subjects = getResources().getStringArray(R.array.delivery_time_options);
        List<CharSequence> timeList = new LinkedList<>(Arrays.asList(subjects));
        int hour = Calendar.getInstance().getTime().getHours();

        if (hour < deliveryStart) {
            timeList.remove(0);
            timePicker.setVisibility(View.VISIBLE);
            //checkout_scroll_view.postDelayed(() -> checkout_scroll_view.fullScroll(ScrollView.FOCUS_DOWN),100);
        } else if (hour > deliveryEnd) {
            timeList.remove(0);
            timeList.remove(0);
            timePicker.setVisibility(View.VISIBLE);
            //checkout_scroll_view.postDelayed(() -> checkout_scroll_view.fullScroll(ScrollView.FOCUS_DOWN),100);
        }
        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(timeList), this,0);
        deliveryDate.setLayoutManager(new LinearLayoutManager(this));
        deliveryDate.setAdapter(radioListAdapter);

        if (deliveryDate.getAdapter() != null) {
            deliveryDate.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    if (((RadioListAdapter) deliveryDate.getAdapter()).getSelectedItem() > 1) {
                        timePicker.setVisibility(View.VISIBLE);
                        checkout_scroll_view.postDelayed(() -> checkout_scroll_view.fullScroll(ScrollView.FOCUS_DOWN), 100);
                    } else {
                        timePicker.setVisibility(View.GONE);
                    }
                }
            });
            timePicker.setOnTimeChangedListener((timePicker, i, i1) -> {
                int cur_hour = Calendar.getInstance().getTime().getHours();
                int cur_min = Calendar.getInstance().getTime().getMinutes();
                if (timePicker.getCurrentHour() == cur_hour) {
                    if (timePicker.getCurrentMinute() < cur_min)
                        timePicker.setCurrentMinute(cur_min);
                } else if (timePicker.getCurrentHour() < cur_hour) {
                    timePicker.setCurrentHour(cur_hour);
                    timePicker.setCurrentMinute(cur_min);
                }
                if (timePicker.getCurrentHour() < deliveryStart) {
                    timePicker.setCurrentHour(deliveryStart);
                    timePicker.setCurrentMinute(0);
                }
                if (timePicker.getCurrentHour() > deliveryEnd) {
                    timePicker.setCurrentHour(deliveryEnd);
                    timePicker.setCurrentMinute(0);
                }
            });
        }
    }


    int getItemIdByType(RecyclerView view, int type) {
        int option = ((RadioListAdapter) (Objects.requireNonNull(view.getAdapter()))).getSelectedItem();
        CharSequence optionTitle = ((RadioListAdapter) (Objects.requireNonNull(view.getAdapter()))).getSelectedOption();
        switch (type) {
            case TYPE_PAYMENT_MODE:
                return option;
            case TYPE_UNAVAILABLE_OPTION:
                return option;
            case TYPE_DELIVERY_DATE:
                if (optionTitle.equals("Plus tard"))
                    return 2;
                if (optionTitle.equals("Demain"))
                    return 3;
                return 1;
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
            SharedPreferences sharedPreferences = getSharedPreferences("itemsList", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
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
            startActivityForResult(intent, Config.REQUEST_CODE_PAYMENT);
            ShoppingCart.getInstance().clear();
            finish();
        } else {
            orderLoading.setProgress(-1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Config.REQUEST_CODE_PAYMENT:
                if (resultCode == Activity.RESULT_OK) {
                    //todo: call for check service
                }
        }
    }

    @Override
    public void onPromoResponse() {

    }

}
