package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.monresto.acidlabs.monresto.Model.Dish;

public class OptionsAdapter extends ArrayAdapter<Dish.Option> {
    public OptionsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
