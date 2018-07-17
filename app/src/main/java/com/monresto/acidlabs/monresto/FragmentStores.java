package com.monresto.acidlabs.monresto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.Model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class FragmentStores extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private List<Restaurant> restaurants;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stores, container,false);

        restaurants = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), restaurants);
        recyclerView = v.findViewById(R.id.stores_recylcerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void updateList(List<Restaurant> restaurants) {
        //System.out.println(restaurants.size());
        //System.out.println(recyclerViewAdapter);
        recyclerViewAdapter.setRestaurants(restaurants);
        recyclerViewAdapter.notifyDataSetChanged();
    }


}
