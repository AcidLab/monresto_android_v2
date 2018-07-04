package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Dish, Options> items;

    private static ShoppingCart instance;

    public class Options{
        private int quantity;
        private Dish.Dimension dimension;
        private ArrayList<Dish.Component> components;

        public Options(int quantity, Dish.Dimension dimension, ArrayList<Dish.Component> components) {
            this.quantity = quantity;
            this.dimension = dimension;
            this.components = components;
        }

        public int getQuantity() {
            return quantity;
        }

        public Dish.Dimension getDimension() {
            return dimension;
        }

        public ArrayList<Dish.Component> getComponents() {
            return components;
        }
    }

    private ShoppingCart(){
        items = new HashMap<>();
    }

    public static ShoppingCart getInstance(){
        if(instance==null)
            instance = new ShoppingCart();
        return instance;
    }

    public Map<Dish, Options> getItems() {
        return items;
    }

    public void addToCart(Dish dish){
        items.put(dish, new Options(1 , null, null));
    }

    public void addToCart(Dish dish, int quantity, Dish.Dimension dimension, ArrayList<Dish.Component> components){
        items.put(dish, new Options(quantity, dimension, components));
    }

    public void removeFromCart(Dish dish){
        if(items.containsKey(dish))
            items.remove(dish);
    }

    public JSONArray getOrdersJson(){
        JSONArray orders = new JSONArray();
        JSONObject actualItem;
        for(Map.Entry<Dish, Options> item : items.entrySet()) {
            Dish dish = item.getKey();
            Options options = item.getValue();

            actualItem = new JSONObject();
            try {
                actualItem.put("productID", dish.getId());
                actualItem.put("quantity", options.quantity);
                if(options.dimension!=null)
                    actualItem.put("dimensionID", String.valueOf(options.dimension.getId()));
                if(options.components!=null){
                    JSONArray componentsJson = new JSONArray();
                    JSONObject actualComponent;
                    for(Dish.Component component : options.components){
                        actualComponent = new JSONObject();
                        actualComponent.put("componentID", component.getId());
                        JSONArray optionsJson = new JSONArray();
                        for(Dish.Component.Option option : component.getOptions()){
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
}
