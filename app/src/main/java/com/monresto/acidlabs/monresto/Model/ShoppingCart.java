package com.monresto.acidlabs.monresto.Model;

import com.monresto.acidlabs.monresto.Utilities;

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
    private double minCartTotal;

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

        @Override
        public boolean equals(Object obj) {
            return (obj != null && obj instanceof Options && ((Options) obj).quantity == this.quantity && ((Options) obj).dimension == this.dimension && ((Options) obj).components == this.components);
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
    public int getCount() {
        return items.size();
    }


    public void addToCart(Dish dish) {
        addToCart(dish, 1, null, null);
    }

    public double getCartSubTotal() {
        double subTotal = 0;
        for (Map.Entry<Dish, Options> entry : getItems().entrySet()) {
            Dish cle = entry.getKey();
            Options valeur = entry.getValue();

            if (valeur.getDimension() != null && valeur.getDimension().getPrice() != 0)
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

    public boolean addToCart(Dish dish, int quantity, Dish.Option dimension, ArrayList<Dish.Component> components) {
        if (restoID == -1) {
            restoID = dish.getRestoID();
            for (Restaurant R : Monresto.getInstance().getRestaurants()) {
                if (R.getId() == restoID)
                    minCartTotal = R.getMinimalPrice();
            }
        } else {
            if (dish.getRestoID() != restoID)
                return false;
        }
        Dish addedDish;
        try {
            addedDish = dish.clone();
            StringBuilder compCode = new StringBuilder();
            if (components != null)
                for (Dish.Component c : components) {
                    for (Dish.Option o : c.getOptions())
                        compCode.append(o.getId());
                }
            addedDish.setOptionsHash(Utilities.md5("" + (dimension != null ? String.valueOf(dimension.getId()) : "") + compCode));
            if (items.containsKey(addedDish)) {
                items.get(addedDish).quantity += quantity;
            } else {
                items.put(addedDish, new Options(quantity, dimension, components));
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void removeFromCart(Dish dish) {
        if (items.containsKey(dish))
            items.remove(dish);
        if (items.isEmpty())
            restoID = -1;
    }

    public int getCurrentRestaurant() {
        if (getItems() != null)
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
                actualItem.put("dishID", dish.getId());
                actualItem.put("quantity", options.quantity);
                actualItem.put("dimensionID", options.dimension != null ? String.valueOf(options.dimension.getId()) : "");
                actualItem.put("comment", "");

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
                        actualComponent.put("Options", optionsJson);
                        componentsJson.put(actualComponent);
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

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getMinCartTotal() {
        return minCartTotal;
    }
}
