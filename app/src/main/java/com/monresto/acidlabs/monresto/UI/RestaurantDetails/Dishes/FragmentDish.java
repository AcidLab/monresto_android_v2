package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Dishes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.RestaurantDetailsAdapter;

import java.util.ArrayList;

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
        RestaurantDetailsAdapter adapter = new RestaurantDetailsAdapter(createItemList(), this.getContext());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Dish> createItemList() {
        ArrayList<Dish> dishesList = new ArrayList<>();
        if (dishes.size() > 0)
            dishesList.addAll(dishes);
        return dishesList;
    }

}
