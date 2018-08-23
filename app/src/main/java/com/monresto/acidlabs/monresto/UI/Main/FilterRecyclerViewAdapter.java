package com.monresto.acidlabs.monresto.UI.Main;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.monresto.acidlabs.monresto.Model.Filter;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.Speciality;
import com.monresto.acidlabs.monresto.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterRecyclerViewAdapter extends RecyclerView.Adapter<FilterRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Filter> filters;
    private Context context;
    private ViewHolder oldFilter;

    public FilterRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //TODO
        @BindView(R.id.filter)
        Button filter;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        //TODO
        v = LayoutInflater.from(context).inflate(R.layout.item_filter, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setIsRecyclable(false);
        Filter filter = filters.get(i);

        viewHolder.filter.setText(filter.getTitle());

        //TODO
        switch (filter.getType()) {
            case Monresto.FILTER_NOTE: {
                viewHolder.filter.setOnClickListener(view -> {
                    ((FilterActivity)context).sendFilter(Monresto.FILTER_NOTE);
                });
            }
            break;
            case Monresto.FILTER_OPEN: {
                viewHolder.filter.setOnClickListener(view -> {
                    ((FilterActivity)context).sendFilter(Monresto.FILTER_OPEN);
                });
            }
            break;
            case Monresto.FILTER_TIME: {
                viewHolder.filter.setOnClickListener(view -> {
                    ((FilterActivity)context).sendFilter(Monresto.FILTER_TIME);
                });
            }
            break;
            case Monresto.FILTER_PROMO: {
                viewHolder.filter.setOnClickListener(view -> {
                    ((FilterActivity)context).sendFilter(Monresto.FILTER_PROMO);
                });
            }
            break;
        }
    }


    @Override
    public int getItemCount() {
        if (filters == null)
            return 0;
        return filters.size();
    }

    public void setFilters(ArrayList<Filter> filters) {
        this.filters = filters;
    }

}
