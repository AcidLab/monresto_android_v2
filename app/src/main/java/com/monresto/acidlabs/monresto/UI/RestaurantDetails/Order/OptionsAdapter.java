package com.monresto.acidlabs.monresto.UI.RestaurantDetails.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish.Option;
import com.monresto.acidlabs.monresto.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsAdapter extends ArrayAdapter<Option> {

    private ArrayList<Option> optionsList;
    private Context context;
    private int selectedItemCounter = 0;

    @BindView(R.id.option_name)
    TextView option_name;
    @BindView(R.id.option_price)
    TextView option_price;
    @BindView(R.id.option_checkbox)
    CheckBox option_checkbox;

    public OptionsAdapter(ArrayList<Option> optionsList, Context context) {
        super(context, R.layout.item_dish_option, optionsList);
        this.optionsList = optionsList;
        this.context = context;
        selectedItemCounter = 0;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_dish_option, null);

            ButterKnife.bind(this, v);
        }

        Option option = optionsList.get(position);
        option_name.setText(option.getTitle());
        option_price.setText("(" + String.valueOf(option.getPrice()) + " DT)");

        option_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItemCounter++;
                } else {
                    selectedItemCounter--;
                }
                if (selectedItemCounter >= 2) {
                    buttonView.setChecked(false);
                    selectedItemCounter--;
                    notifyDataSetChanged();
                }
            }
        });

        option_checkbox.setChecked(false);

        return v;
    }
}
