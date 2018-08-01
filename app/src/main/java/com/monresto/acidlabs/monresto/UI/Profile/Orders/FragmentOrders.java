package com.monresto.acidlabs.monresto.UI.Profile.Orders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.Model.Order;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Restaurants.RecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentOrders extends Fragment {
    @BindView(R.id.layoutOrdersContainer)
    RecyclerView recyclerView;

    OrderRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_orders, container, false);
        ButterKnife.bind(this, root);

        adapter = new OrderRecyclerViewAdapter(getContext());

        UserService userService = new UserService(getContext());
        userService.getOrders(0);
        System.out.println("FragmentOrders.onCreateView");
        return root;
    }

    public void fillPending(ArrayList<Order> orders){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOrders(orders);
        adapter.notifyDataSetChanged();
    }
}
