package com.monresto.acidlabs.monresto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadioListAdapter extends RecyclerView.Adapter<RadioListAdapter.ViewHolder> {

    private ArrayList<CharSequence> options;
    private Context context;
    private int selectedItem;
    private CharSequence selectedOption;
    private int disabledItem = -1;

    public RadioListAdapter(ArrayList<CharSequence> options, Context context,int selectedItem) {
        this.context = context;
        this.options = options;
        this.selectedItem = selectedItem;
        selectedOption = "";
    }

    public RadioListAdapter(ArrayList<CharSequence> options, Context context,int selectedItem,int disabledItem) {
        this.context = context;
        this.options = options;
        this.selectedItem = selectedItem;
        this.disabledItem = disabledItem;
        selectedOption = "";
    }

    public int getSelectedItem() {
        return selectedItem+1;
    }
    public CharSequence getSelectedOption() {
        return selectedOption;
    }

    @NonNull
    @Override
    public RadioListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_radio_option, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RadioListAdapter.ViewHolder viewHolder, int i) {
        CharSequence option = options.get(i);
        viewHolder.option_name.setText(option);

        if (i == disabledItem) {
            viewHolder.option_radio.setEnabled(false);
        }
        if (i == selectedItem) {
            viewHolder.option_radio.setChecked(true);
            selectedOption = viewHolder.option_name.getText();
        } else viewHolder.option_radio.setChecked(false);

        viewHolder.option_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = i;
                selectedOption = viewHolder.option_name.getText();
                notifyDataSetChanged();
            }
        });
        viewHolder.option_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = i;
                selectedOption = viewHolder.option_name.getText();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.option_container)
        ConstraintLayout option_container;
        @BindView(R.id.option_name)
        TextView option_name;
        @BindView(R.id.option_radio)
        RadioButton option_radio;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}