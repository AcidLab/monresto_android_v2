package com.monresto.acidlabs.monresto.Model;

import java.util.ArrayList;

public class Monresto {
    private static Monresto instance;
    private ArrayList<Restaurant> restaurants;

    public static Monresto getInstance(){
        if(instance==null)
            instance = new Monresto();
        return instance;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Restaurant findRestaurant(int id){
        for(Restaurant R : restaurants){
            if(R.getId()==id)
                return R;
        }
        return null;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
