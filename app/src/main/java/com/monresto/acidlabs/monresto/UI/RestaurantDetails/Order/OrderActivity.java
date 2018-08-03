package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.Menu;
import com.monresto.acidlabs.monresto.Model.Restaurant;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity implements RestaurantAsyncResponse {

    private Dish dish;
    CollapsingToolbarLayout toolbar_layout;
    RestaurantService restaurantService;
    OptionsAdapter optionsAdapter;
    ComponentsAdapter componentsAdapter;
    ArrayList<ListView> componentsLists;
    int currentQuantity;

    @BindView(R.id.lists_container)
    LinearLayout lists_container;
    @BindView(R.id.dish_name)
    TextView dish_name;
    @BindView(R.id.total_order)
    TextView total_order;
    @BindView(R.id.dimensions_list)
    ListView dimensions_list;
    @BindView(R.id.dimensions_text)
    TextView dimensions_text;
    @BindView(R.id.dish_description)
    TextView dish_description;
    @BindView(R.id.dish_price)
    TextView dish_price;
    @BindView(R.id.dish_quantity)
    TextView dish_quantity;
    @BindView(R.id.dish_quantity_add)
    ImageView dish_quantity_add;
    @BindView(R.id.dish_quantity_reduce)
    ImageView dish_quantity_reduce;
    @BindView(R.id.cancel_order)
    Button cancel_order;
    @BindView(R.id.add_to_cart)
    Button add_to_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get dish information from the caller intent
        Intent i = getIntent();
        dish = i.getParcelableExtra("dish");

        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        final Toolbar toolbar = findViewById(R.id.toolbar);

        currentQuantity = Integer.valueOf(dish_quantity.getText().toString());

        dish_name.setText(Utilities.decodeUTF(dish.getTitle()));
        if (Double.isNaN(dish.getPrice()))
            dish_price.setText("Choisissez parmi les options");
        else dish_price.setText("Prix: " + String.valueOf(dish.getPrice()) + " DT");
        dish_description.setText(Utilities.decodeUTF(dish.getDescription()));

        dish_quantity_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTotalOnQuantityChanged(currentQuantity + 1);
                dish_quantity.setText(Integer.toString(currentQuantity));
            }
        });

        dish_quantity_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(dish_quantity.getText().toString()) > 1) {
                    updateTotalOnQuantityChanged(currentQuantity - 1);
                    dish_quantity.setText(Integer.toString(currentQuantity));
                }
            }
        });

        total_order.setText(String.valueOf(dish.getPrice()));

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dish_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty())
                    updateTotalOnQuantityChanged(1);
                else updateTotalOnQuantityChanged(Integer.valueOf(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Dish.Component> components = new ArrayList<>();
                ArrayList<Dish.Option> options = new ArrayList<>();

                if (componentsLists != null)
                    for (int i = 0; i < componentsLists.size(); i++) {
                        ComponentsAdapter componentsAdapterTemp = (ComponentsAdapter) componentsLists.get(i).getAdapter();

                        for (int j = 0; j < componentsAdapterTemp.getCheckedItemsPositions().size(); j++)
                            options.add(componentsAdapterTemp.getItem(componentsAdapterTemp.getCheckedItemsPositions().get(j)));

                        components.add(new Dish.Component(dish.getComponents().get(i).getId(), dish.getComponents().get(i).getName(), dish.getComponents().get(i).getNumberChoice(), dish.getComponents().get(i).getNumberChoiceMax(), options));
                    }

                if (optionsAdapter != null)
                    ShoppingCart.getInstance().addToCart(dish, Integer.valueOf(dish_quantity.getText().toString()), optionsAdapter.getItem(optionsAdapter.getSelectedItem()), components);
                else ShoppingCart.getInstance().addToCart(dish, Integer.valueOf(dish_quantity.getText().toString()), null, components);


                System.out.println("SPECIAL DEBUG: Items added to cart !");
                finish();
            }
        });

        final ImageView img = new ImageView(this);

        if (dish.isComposed()) {
            total_order.setText("0");
            restaurantService = new RestaurantService(this);
            restaurantService.getComposedDish(dish);
        }


        setSupportActionBar(toolbar);

    }

    @Override
    public void onListReceived(ArrayList<Restaurant> restaurantList) {

    }

    @Override
    public void onMenusReceived(ArrayList<Menu> menus) {

    }

    @Override
    public void onDishesReceived(ArrayList<Dish> dishes, Menu menu) {

    }

    @Override
    public void onComposedDishReceived(final Dish dish) {
        this.dish = dish;

        dimensions_list.setVisibility(View.VISIBLE);
        dimensions_text.setVisibility(View.VISIBLE);

        optionsAdapter = new OptionsAdapter(dish.getDimensions(), this, total_order, dish_quantity);
        dimensions_list.setAdapter(optionsAdapter);
        dimensions_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utilities.convDpToPx(this, 30) * dish.getDimensions().size() + Utilities.convDpToPx(this, 16)));
        dimensions_list.requestLayout();

        componentsLists = new ArrayList<>();

        for (int i = 0; i < dish.getComponents().size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.textview_order_header, null);
            textView.setText(dish.getComponents().get(i).getName() + " (" + dish.getComponents().get(i).getNumberChoiceMax() + " CHOIX)");
            lists_container.addView(textView);

            ListView listViewTemp = (ListView) LayoutInflater.from(this).inflate(R.layout.listview_order_options, null);
            componentsAdapter = new ComponentsAdapter(dish.getComponents().get(i).getOptions(), this, dish.getComponents().get(i).getNumberChoiceMax(), total_order, dish_quantity);

            componentsLists.add(listViewTemp);
            componentsLists.get(i).setAdapter(componentsAdapter);


            componentsLists.get(i).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utilities.convDpToPx(this, 30) * dish.getComponents().get(i).getNumberChoice() + Utilities.convDpToPx(this, 16)));
            componentsLists.get(i).requestLayout();

            lists_container.addView(componentsLists.get(i));
        }

    }

    @Override
    public void onSpecialitiesReceived(ArrayList<Speciality> specialities) {

    }

    @Override
    public void onServerDown() {

    }

    private void updateTotalOnQuantityChanged(int quantity) {
        System.out.println(quantity);
        total_order.setText(String.valueOf(Double.valueOf(total_order.getText().toString()) / currentQuantity * quantity));
        currentQuantity = quantity;
    }

}
