package com.monresto.acidlabs.monresto.Model;

import java.util.ArrayList;

public class Monresto {
    public final static int FILTER_PRICE = 1;
    public final static int FILTER_OPEN = 2;
    public final static int FILTER_PROMO = 3;
    public final static int FILTER_TIME = 4;
    public final static int FILTER_NOTE = 5;

    private static Monresto instance;
    private ArrayList<Restaurant> restaurants;
    private Address address;

    private static double lat = 0;
    private static double lon = 0;
    public static boolean locationChanged;

    public static boolean loginPending = false;

    public static Monresto getInstance() {
        if (instance == null)
            instance = new Monresto();
        return instance;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Restaurant findRestaurant(int id) {
        for (Restaurant R : restaurants) {
            if (R.getId() == id)
                return R;
        }
        return null;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Address getAddress() {
        if (User.getInstance() == null)
            return null;
        if (address == null)
            address = User.getInstance().getAddresses().get(0);
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        Monresto.lat = lat;
    }

    public static double getLon() {
        return lon;
    }

    public static void setLon(double lon) {
        Monresto.lon = lon;
    }
}
