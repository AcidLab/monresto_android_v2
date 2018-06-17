package com.monresto.acidlabs.monresto.Service.Restaurant;

import com.monresto.acidlabs.monresto.Model.Restaurant;

import java.util.ArrayList;

public interface RestaurantAsyncResponse {
    void processFinish(ArrayList<Restaurant> RestaurantList);
}
