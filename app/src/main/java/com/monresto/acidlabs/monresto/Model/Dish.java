package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;

public class Dish {
    private int id;
    private int quantity;
    private String name;
    private String ingredient;
    private String price;
    private String promotion;
    private String imagePath;
    private boolean isOrdered;
    private boolean isFavorite;
    private boolean isComposed;
    private boolean isBay;

    private int restoID;
    private String restoname;
    private String restoimage;
    private JSONArray paymentmethode;

    public Dish(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
