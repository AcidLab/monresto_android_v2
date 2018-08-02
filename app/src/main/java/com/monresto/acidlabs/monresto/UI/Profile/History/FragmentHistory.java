package com.monresto.acidlabs.monresto.UI.Profile.History;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.Model.Order;
import com.monresto.acidlabs.monresto.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHistory extends Fragment {
    @BindView(R.id.recyclerViewHistory)
    RecyclerView recyclerView;

    HistoryRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_history, container, false);
        ButterKnife.bind(this, root);

        adapter = new HistoryRecyclerViewAdapter(getContext());

        return root;
    }

    public void fillHistory(ArrayList<Order> orders){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOrders(orders);
        adapter.notifyDataSetChanged();
    }
}
