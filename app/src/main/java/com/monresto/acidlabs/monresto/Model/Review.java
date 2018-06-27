package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Review {
    private int orderID;
    private String clientname;
    private String review;
    private String reviewdate;
    private String orderdate;
    private int note;
    private ArrayList<Dish> dishes;

    private Review(int orderID, String clientname, String review, String reviewdate, String orderdate, int note, ArrayList<Dish> dishes) {
        this.orderID = orderID;
        this.clientname = clientname;
        this.review = review;
        this.reviewdate = reviewdate;
        this.orderdate = orderdate;
        this.note = note;
        this.dishes = dishes;
    }

    public static Review createFromJson(JSONObject obj){
        ArrayList<Dish> dishes = new ArrayList<>();
        try {
            JSONArray orderedDishes = obj.getJSONArray("OrderedDishes");

            for(int i=0; i<orderedDishes.length(); i++){
                JSONObject dish = orderedDishes.getJSONObject(i);
                dishes.add(Dish.createFromJson(dish));
            }
            return new Review(Integer.valueOf(obj.getString("orderID")), obj.getString("firstname")+" "+obj.getString("lastname"), obj.getString("verbatim"), obj.getString("reviewDate"), obj.getString("orderDate"), obj.getInt("globalNote"), dishes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getClientname() {
        return clientname;
    }

    public String getReview() {
        return review;
    }

    public String getReviewdate() {
        return reviewdate;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public int getNote() {
        return note;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }
}
