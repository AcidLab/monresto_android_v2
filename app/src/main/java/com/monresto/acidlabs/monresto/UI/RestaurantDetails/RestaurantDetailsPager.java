package com.monresto.acidlabs.monresto.UI.RestaurantDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.Review;
import com.monresto.acidlabs.monresto.UI.RestaurantDetails.Dishes.FragmentDish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RestaurantDetailsPager extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Restaurant restaurant;
    HashMap<Menu, ArrayList<Dish>> dishes = new HashMap<Menu, ArrayList<Dish>>();
    ArrayList<Review> reviews;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public RestaurantDetailsPager(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Restaurant restaurant, HashMap<Menu, ArrayList<Dish>> dishes, ArrayList<Review> reviews) {
        super(fm);
        this.restaurant = restaurant;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.dishes = dishes;
        this.reviews = reviews;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            FragmentRestaurantDetails firstTab = new FragmentRestaurantDetails();
            Bundle firstTabBundle = new Bundle();
            firstTabBundle.putParcelable("restaurant", restaurant);
            firstTabBundle.putParcelableArrayList("reviews", reviews);
            firstTab.setArguments(firstTabBundle);
            return firstTab;
        } else              // Tabs reserved for dishes
        {
            FragmentDish otherTab = null;

            for(Map.Entry<Menu, ArrayList<Dish>> entry : dishes.entrySet()) {
                Menu key = entry.getKey();
                ArrayList<Dish> value = entry.getValue();
                if (value == null)
                    value = new ArrayList<>();

                if (Titles[position].equals(key.getTitle()))
                    otherTab = new FragmentDish(value);
            }
            return otherTab;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}