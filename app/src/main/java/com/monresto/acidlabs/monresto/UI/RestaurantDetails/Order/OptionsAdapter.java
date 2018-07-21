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

    public OptionsAdapter(ArrayList<Option> optionsList, Context context) {
        super(context, R.layout.item_dish_option, optionsList);
        this.optionsList = optionsList;
        this.context = context;
        selectedItem = (-1);
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

        option_name.setText(option.getTitle());
        option_price.setText("(" + String.valueOf(option.getPrice()) + " DT)");

        if (position == selectedItem) option_radio.setChecked(true);
        else option_radio.setChecked(false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                OptionsAdapter.this.notifyDataSetChanged();
            }
        });
        option_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                OptionsAdapter.this.notifyDataSetChanged();
            }
        });


        return v;
    }
}
