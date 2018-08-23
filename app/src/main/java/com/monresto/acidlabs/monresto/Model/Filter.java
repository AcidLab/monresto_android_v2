package com.monresto.acidlabs.monresto.Model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

public class Filter implements Serializable {
    private String title;
    private Drawable icon;
    private int type;

    private static ArrayList<Filter> filters;

    private Filter(String title, Drawable icon, int type) {
        this.title = title;
        this.icon = icon;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public int getType() {
        return type;
    }

    // TODO 
    public static ArrayList<Filter> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
            filters.add(new Filter("Note", null, Monresto.FILTER_NOTE));
            filters.add(new Filter("Ouvert", null, Monresto.FILTER_OPEN));
            filters.add(new Filter("Promotion", null, Monresto.FILTER_PROMO));
            filters.add(new Filter("Temps de livraison", null, Monresto.FILTER_TIME));
        }
        return filters;
    }
}
