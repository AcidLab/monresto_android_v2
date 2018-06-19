package com.monresto.acidlabs.monresto.Service.Restaurant;

import com.monresto.acidlabs.monresto.Model.Restaurant;

import java.util.ArrayList;

public interface RestaurantAsyncResponse {
    void onListReceived(ArrayList<Restaurant> restaurantList);
    void onDetailsReceived(Restaurant restaurant);
}
