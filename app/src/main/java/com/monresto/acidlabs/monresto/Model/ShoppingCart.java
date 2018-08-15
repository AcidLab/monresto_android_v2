package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private HashMap<Dish, Options> items;

    private static ShoppingCart instance;
    private int restoID;

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

        public void setQuantity(int quantity) {
            this.quantity = quantity;
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
        restoID = -1;
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

    public double getCartSubTotal() {
        double subTotal = 0;
        for (Map.Entry<Dish, Options> entry : getItems().entrySet()) {
            Dish cle = entry.getKey();
            Options valeur = entry.getValue();

            if (valeur.getDimension() != null)
                subTotal += valeur.getDimension().getPrice();
            else subTotal += cle.getPrice();

            for (Dish.Component component : valeur.getComponents()) {
                for (Dish.Option option : component.getOptions()) {
                    subTotal += option.getPrice();
                }
            }

            subTotal *= valeur.quantity;
        }
        return subTotal;
    }

    public double getCartDelivery() {
        if (ShoppingCart.getInstance().getItems().keySet().iterator().hasNext()) {
            return Monresto.getInstance().findRestaurant(ShoppingCart.getInstance().getItems().keySet().iterator().next().getRestoID()).getDeliveryCost();
        } else return (0);
    }

    public void addToCart(Dish dish, int quantity, Dish.Option dimension, ArrayList<Dish.Component> components) {
        if(restoID==-1)
            restoID=dish.getRestoID();
        else{
            if(dish.getRestoID()!=restoID)
                return;
        }

        if(items.containsKey(dish)){
            System.out.println("Quantity received: " + quantity);
            items.get(dish).quantity+=quantity;
        }
        else {
            System.out.println("Quantity received: " + quantity);
            items.put(dish, new Options(quantity, dimension, components));
            items.get(dish).setQuantity(quantity);
        }
    }


    public void removeFromCart(Dish dish) {
        if (items.containsKey(dish))
            items.remove(dish);
        if(items.isEmpty())
            restoID=-1;
    }

    public int getCurrentRestaurant() {
        if (getItems()!=null)
            return getItems().keySet().iterator().next().getRestoID();
        else return -1;
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



}
