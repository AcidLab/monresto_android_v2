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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Dish.Option;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsAdapter extends ArrayAdapter<Option> {

    private ArrayList<Option> optionsList;
    private Context context;
    private int selectedItem;
    private TextView total_order;
    private double oldPriceSelected;
    private TextView dish_quantity;

    public OptionsAdapter(ArrayList<Option> optionsList, Context context, TextView total_order, TextView dish_quantity) {
        super(context, R.layout.item_dish_option, optionsList);
        this.optionsList = optionsList;
        this.context = context;
        selectedItem = 0;
        this.total_order = total_order;
        oldPriceSelected = optionsList.get(0).getPrice();
        this.total_order.setText(String.valueOf(oldPriceSelected));
        this.dish_quantity = dish_quantity;
    }

    public OptionsAdapter(Context context) {
        super(context, R.layout.item_dish_option);

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_dish_option, null);
        }

        Option option = optionsList.get(position);

        TextView option_name = v.findViewById(R.id.option_name);
        TextView option_price = v.findViewById(R.id.option_price);
        RadioButton option_radio = v.findViewById(R.id.option_radio);

        if (option.getTitle().trim().length() > 0) {
            option_name.setText(option.getTitle());
        } else option_name.setText("Option " + position + 1);

        option_price.setText("(" + String.valueOf(option.getPrice()) + " DT)");

        if (position == selectedItem) {
            option_radio.setChecked(true);
            total_order.setText(String.valueOf(Double.valueOf(total_order.getText().toString()) - oldPriceSelected * Integer.valueOf(dish_quantity.getText().toString())));
            total_order.setText(String.valueOf(Double.valueOf(total_order.getText().toString()) + option.getPrice() * Integer.valueOf(dish_quantity.getText().toString())));
            oldPriceSelected = option.getPrice();
        } else option_radio.setChecked(false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                notifyDataSetChanged();
            }
        });
        option_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                notifyDataSetChanged();
            }
        });


        return v;
    }

    public int getSelectedItem() {
        return selectedItem;
    }
}
