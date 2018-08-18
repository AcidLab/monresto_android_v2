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
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
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

    AddressRecyclerViewAdapter adapter;
    UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);
        ButterKnife.bind(this);

        swiper_address.setOnRefreshListener(this);

        userService = new UserService(this);
        adapter = new AddressRecyclerViewAdapter(this);
        recyclerview_address.setLayoutManager(new LinearLayoutManager(this));

        userService.getAddress(User.getInstance().getId());
        btnClose.setOnClickListener(e -> finish());
    }

    public void updateList(ArrayList<Address> addresses) {
        if (addresses.size() == 0) {
            Utilities.statusChangerUnavailable(this,"Aucune adresse trouvée", status_address,swiper_address);
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
        System.out.println("addresses = [" + addresses + "]");updateList(addresses);
    }


    @Override
    public void onRefresh() {
        userService.getAddress(User.getInstance().getId());
        swiper_address.setRefreshing(false);
    }
}
