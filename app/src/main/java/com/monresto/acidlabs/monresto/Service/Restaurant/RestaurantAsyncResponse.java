package com.monresto.acidlabs.monresto.Service.Restaurant;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;

import java.util.ArrayList;

public interface RestaurantAsyncResponse {
    void onListReceived(ArrayList<Restaurant> restaurantList);
    void onDetailsReceived(Restaurant restaurant);
    void onMenusReceived(ArrayList<Menu> menus);
    void onDishesReceived(ArrayList<Dish> dishes);
}
