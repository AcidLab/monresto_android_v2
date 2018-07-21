package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Utilities;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class ComponentsAdapter extends ArrayAdapter<Dish.Option> {

    private ArrayList<Dish.Option> optionsList;
    private Context context;
    private int selectedItemCounter;
    private int maxChoices;

    public ComponentsAdapter(ArrayList<Dish.Option> optionsList, Context context, int maxChoices) {
        super(context, R.layout.item_dish_option, optionsList);
        this.optionsList = optionsList;
        this.context = context;
        selectedItemCounter = 0;
        this.maxChoices = maxChoices;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_dish_component, null);

            ButterKnife.bind(this, v);
        }

        Dish.Option option = optionsList.get(position);

        TextView component_name = v.findViewById(R.id.component_name);
        TextView component_price = v.findViewById(R.id.component_price);
        final CheckBox component_checkbox = v.findViewById(R.id.component_checkbox);

        component_name.setText(Utilities.decodeUTF(option.getTitle()));
        component_price.setText("(" + String.valueOf(option.getPrice()) + " DT)");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                component_checkbox.performClick();
            }
        });
        component_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItemCounter++;
                } else {
                    selectedItemCounter--;
                }
                if (selectedItemCounter > maxChoices) {
                    buttonView.setChecked(false);
                    selectedItemCounter--;
                    notifyDataSetChanged();
                }
            }
        });

        //component_checkbox.setChecked(false);

        return v;
    }

}
