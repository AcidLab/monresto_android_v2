package com.monresto.acidlabs.monresto.Model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Dish, Integer> items = new HashMap<>();

    private static ShoppingCart instance;

    private ShoppingCart(){
        items = new HashMap<>();
    }

    public static ShoppingCart getInstance(){
        if(instance==null)
            instance = new ShoppingCart();
        return instance;
    }

    public Map<Dish, Integer> getItems() {
        return items;
    }

    public void addToCart(Dish dish){
        items.put(dish, 1);
    }

    public void addToCart(Dish dish, int quantity){
        items.put(dish, quantity);
    }

    public void removeFromCart(Dish dish){
        if(items.containsKey(dish))
            items.remove(dish);
    }
}
