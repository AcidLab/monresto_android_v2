package com.monresto.acidlabs.monresto.Service.Restaurant;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Speciality;

import java.util.ArrayList;

public interface RestaurantAsyncResponse {
    void onListReceived(ArrayList<Restaurant> restaurantList);
    default void onDetailsReceived(Restaurant restaurant){};
    default void onMenusReceived(ArrayList<Menu> menus){};
    default void onDishesReceived(ArrayList<Dish> dishes, Menu menu){};
    default void onComposedDishReceived(Dish dish){};
    default void onSpecialitiesReceived(ArrayList<Speciality> specialities){};
    default void onServerDown(){};
    default void onServerHighDemand(){};
}
