package com.monresto.acidlabs.monresto.Model;

import android.graphics.drawable.Drawable;

import com.monresto.acidlabs.monresto.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Filter implements Serializable {
    private String title;
    private int icon;
    private int type;

    private static ArrayList<Filter> filters;

    private Filter(String title, int icon, int type) {
        this.title = title;
        this.icon = icon;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public int getType() {
        return type;
    }

    public static ArrayList<Filter> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
            filters.add(new Filter("Note", R.drawable.rate, Monresto.FILTER_NOTE));
            filters.add(new Filter("Ouvert", R.drawable.open, Monresto.FILTER_OPEN));
            filters.add(new Filter("Promotion", R.drawable.prom, Monresto.FILTER_PROMO));
            filters.add(new Filter("Temps de livraison", R.drawable.time, Monresto.FILTER_TIME));
        }
        return filters;
    }
}
