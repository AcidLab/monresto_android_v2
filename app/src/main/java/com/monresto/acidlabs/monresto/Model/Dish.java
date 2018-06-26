package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Dish {
    private int id;
    private String title;
    private String description;
    private double price;
    private String promotion;
    private String tva;
    private boolean isOrdered;
    private boolean isFavorite;
    private boolean isComposed;
    private String imagePath;

    private boolean isBay;
    private String ingredient;
    private int restoID;
    private String restoname;
    private String restoimage;
    private JSONArray paymentmethode;
    private int quantity;

    private Dish(int id, String title, String description, double price, String promotion, String tva, boolean isOrdered, boolean isFavorite, boolean isComposed, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.promotion = promotion;
        this.tva = tva;
        this.isOrdered = isOrdered;
        this.isFavorite = isFavorite;
        this.isComposed = isComposed;
        this.imagePath = imagePath;
    }

    public static Dish createFromJson(JSONObject obj){
        return new Dish(obj.optInt("dishID"), obj.optString("title"), obj.optString("description"),
                obj.optDouble("price"), obj.optString("promotion"), obj.optString("tva"),
                obj.optInt("isOrdered") != 0, obj.optInt("isFavorite") != 0, obj.optInt("isComposed") != 0,
                obj.optString("imagePath"));
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getTva() {
        return tva;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isComposed() {
        return isComposed;
    }

    public String getImagePath() {
        return imagePath;
    }
}
