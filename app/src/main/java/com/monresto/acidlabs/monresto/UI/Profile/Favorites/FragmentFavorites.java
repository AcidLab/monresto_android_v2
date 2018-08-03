package com.monresto.acidlabs.monresto.UI.Profile.Favorites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavorites extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    DishesRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_favorites, container, false);
        ButterKnife.bind(this, root);

        adapter = new DishesRecyclerViewAdapter(getContext());
        return root;
    }

    public void updateList(ArrayList<Dish> dishes){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setDishes(dishes);
        adapter.notifyDataSetChanged();
    }
}
