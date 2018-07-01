package com.monresto.acidlabs.monresto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("ValidFragment")
public class FragmentDish extends Fragment {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    ArrayList<Dish> dishes;

    public FragmentDish(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.restaurant_content, null);

        setUpView(root);

        return root;
    }

    void setUpView(ViewGroup root){
        ButterKnife.bind(this, root);
        setUPList();
    }

    void setUPList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RestaurantDetailsAdapter adapter = new RestaurantDetailsAdapter(createItemList());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Dish> createItemList() {
        ArrayList<Dish> dishesList = new ArrayList<>();
        if (dishes.size() > 0)
            for (int i = 0; i < dishes.size(); i++) {
                dishesList.add(dishes.get(i));
            }
        return dishesList;
    }

}
