package com.monresto.acidlabs.monresto.UI.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Profile.Address.NewAddressActivity;
import com.monresto.acidlabs.monresto.UI.User.AddressRecyclerViewAdapter;
import com.monresto.acidlabs.monresto.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectAddressActivity extends AppCompatActivity implements UserAsyncResponse, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerview_address)
    RecyclerView recyclerview_address;
    @BindView(R.id.address_pull_to_refresh)
    TextView address_pull_to_refresh;
    @BindView(R.id.status_address)
    ConstraintLayout status_address;
    @BindView(R.id.swiper_address)
    SwipeRefreshLayout swiper_address;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.buttonNewAddress)
    Button buttonNewAddress;

    AddressRecyclerViewAdapter adapter;
    UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_address_select);
        ButterKnife.bind(this);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
        getWindow().setLayout(width, height);


        swiper_address.setOnRefreshListener(this);

        userService = new UserService(this);
        adapter = new AddressRecyclerViewAdapter(this);
        recyclerview_address.setLayoutManager(new LinearLayoutManager(this));

        userService.getAddress(User.getInstance().getId());
        btnClose.setOnClickListener(e -> finish());
        buttonNewAddress.setOnClickListener(e -> {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), Config.REQUEST_PLACE_PICKER);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void updateList(ArrayList<Address> addresses) {
        if (addresses.size() == 0) {
            Utilities.statusChangerUnavailable(this, "Aucune adresse trouvée", status_address, swiper_address);
            return;
        }

        adapter.setAddresses(addresses);
        recyclerview_address.setAdapter(adapter);

        status_address.setVisibility(View.INVISIBLE);
        swiper_address.setVisibility(View.VISIBLE);
        address_pull_to_refresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddressListReceived(ArrayList<Address> addresses) {
        updateList(addresses);
        if (User.getInstance() != null)
            User.getInstance().setAddresses(addresses);
    }


    @Override
    public void onRefresh() {
        userService.getAddress(User.getInstance().getId());
        swiper_address.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_CODE_ADRESS_SELECT) {
            onRefresh();
        } else if (requestCode == Config.REQUEST_PLACE_PICKER) {
            Place place = PlacePicker.getPlace(data, this);
            Intent intent = new Intent(this, NewAddressActivity.class);
            intent.putExtra("lat", place.getLatLng().latitude);
            intent.putExtra("lng", place.getLatLng().longitude);
            intent.putExtra("address", place.getAddress());
            startActivityForResult(intent, Config.REQUEST_CODE_ADRESS_SELECT);
        }
    }
}
