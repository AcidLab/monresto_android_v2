package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Order {
    private int orderID;
    private String orderDate;
    private String status;
    private double orderPrice;
    private int restoID;
    private String restoName;
    private String restoImagePath;
    private ArrayList<Dish> Dishes;

    public Order(int orderID, String orderDate, String status, double orderPrice, int restoID, String restoName, String restoImagePath, ArrayList<Dish> dishes) {
        System.out.println("orderID = [" + orderID + "], orderDate = [" + orderDate + "], status = [" + status + "], orderPrice = [" + orderPrice + "], restoID = [" + restoID + "], restoName = [" + restoName + "], restoImagePath = [" + restoImagePath + "], dishes = [" + dishes + "]");
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.status = status;
        this.orderPrice = orderPrice;
        this.restoID = restoID;
        this.restoName = restoName;
        this.restoImagePath = restoImagePath;
        Dishes = dishes;
    }

    public static ArrayList<Order> makeListFromJson(JSONArray array) throws JSONException {
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Dish> dishesList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            JSONArray dishes = obj.getJSONArray("Dishes");
            for (int j = 0; j < dishes.length(); j++) {
                JSONObject dishObj = dishes.getJSONObject(j);
                dishesList.add(Dish.getOrderedDish(dishObj.optInt("dishID"), dishObj.optString("dishName"), dishObj.optInt("dishQuantity")));
            }
            orders.add(new Order(obj.optInt("orderID"), obj.optString("orderDate"), obj.optString("status"), obj.optDouble("orderPrice"), obj.optInt("restoID"), obj.optString("restoName"), obj.optString("restoImagePath"), dishesList));
        }
        return orders;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public int getRestoID() {
        return restoID;
    }

    public String getRestoName() {
        return restoName;
    }

    public String getRestoImagePath() {
        return restoImagePath;
    }

    public ArrayList<Dish> getDishes() {
        return Dishes;
    }

    public String getDishesString() {
        String dishNames = "";
        for (Dish D : Dishes) {
            if (dishNames.equals(""))
                dishNames = D.getQuantity() + " " + D.getTitle();
            else
                dishNames += ", " + D.getQuantity() + " " + D.getTitle();
        }
        return dishNames;
    }
}
