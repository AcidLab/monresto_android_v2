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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stores_fragment, container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.stores_recylcerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), restaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restaurants = new ArrayList<>();

        restaurants.add(new Restaurant(1, "First Store", "Test"));
        restaurants.add(new Restaurant(2, "Second Store", "Test"));
        restaurants.add(new Restaurant(3, "Third Store", "Test"));
    }
}
