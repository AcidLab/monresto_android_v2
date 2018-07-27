package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Dish, Options> items;
    private Restaurant restaurant;

    private static ShoppingCart instance;

    public class Options {
        private int quantity;
        private Dish.Option dimension;
        private ArrayList<Dish.Component> components;

        public Options(int quantity, Dish.Option dimension, ArrayList<Dish.Component> components) {
            this.quantity = quantity;
            this.dimension = dimension;
            this.components = components;
        }

        public int getQuantity() {
            return quantity;
        }

        public Dish.Option getDimension() {
            return dimension;
        }

        public ArrayList<Dish.Component> getComponents() {
            return components;
        }
    }

    private ShoppingCart() {
        items = new HashMap<>();
    }

    public static ShoppingCart getInstance() {
        if (instance == null)
            instance = new ShoppingCart();
        return instance;
    }

    public Map<Dish, Options> getItems() {
        return items;
    }


    public void addToCart(Dish dish) {
        addToCart(dish, 1, null, null);
    }

    public double getCartTotal() {
        for (Map.Entry<Dish, Options> entry : getItems().entrySet()) {
            Dish cle = entry.getKey();
            Options valeur = entry.getValue();


        }
        return 1;
    }

    public void addToCart(Dish dish, int quantity, Dish.Option dimension, ArrayList<Dish.Component> components) {
        if (dish.getRestoID() != restaurant.getId())
            return;
        items.put(dish, new Options(quantity, dimension, components));
    }


    /**
     * -- TEST --
     */
    public boolean addToCart(Dish dish, Restaurant resto, int quantity, Dish.Option dimension, ArrayList<Dish.Component> components) {
        if (restaurant == null) {
            restaurant = resto;
            items.put(dish, new Options(quantity, dimension, components));
        } else if (restaurant.equals(resto))
            items.put(dish, new Options(quantity, dimension, components));
        else
            return false;

        return true;
    }

    public void removeFromCart(Dish dish) {
        if (items.containsKey(dish))
            items.remove(dish);
        if (items.isEmpty())
            restaurant = null;
    }

    public JSONArray getOrdersJson() {
        JSONArray orders = new JSONArray();
        JSONObject actualItem;
        for (Map.Entry<Dish, Options> item : items.entrySet()) {
            Dish dish = item.getKey();
            Options options = item.getValue();

            actualItem = new JSONObject();
            try {
                actualItem.put("productID", dish.getId());
                actualItem.put("quantity", options.quantity);
                if (options.dimension != null)
                    actualItem.put("dimensionID", String.valueOf(options.dimension.getId()));
                if (options.components != null) {
                    JSONArray componentsJson = new JSONArray();
                    JSONObject actualComponent;
                    for (Dish.Component component : options.components) {
                        actualComponent = new JSONObject();
                        actualComponent.put("componentID", component.getId());
                        JSONArray optionsJson = new JSONArray();
                        for (Dish.Option option : component.getOptions()) {
                            JSONObject optionObject = new JSONObject();
                            optionObject.put("optionID", option.getId());
                            optionsJson.put(optionObject);
                        }
                    }
                    actualItem.put("Components", componentsJson);
                }
                orders.put(actualItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return orders;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
