package com.monresto.acidlabs.monresto.UI.Homepage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.MainActivity;
import com.monresto.acidlabs.monresto.Model.HomepageConfig;
import com.monresto.acidlabs.monresto.Model.HomepageDish;
import com.monresto.acidlabs.monresto.Model.HomepageEvent;
import com.monresto.acidlabs.monresto.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomepageEventsAdapter extends RecyclerView.Adapter<HomepageEventsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HomepageEvent> events;

    public HomepageEventsAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView item_title;
        @BindView(R.id.item_label)
        TextView item_label;
        @BindView(R.id.item_bg)
        ImageView item_bg;
        @BindView(R.id.itemContainer)
        ConstraintLayout itemContainer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public HomepageEventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_homepage, viewGroup, false);

        return new HomepageEventsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageEventsAdapter.ViewHolder viewHolder, int i) {
        HomepageEvent event;
        if (events != null && !events.isEmpty()) {
            event = events.get(i);
            viewHolder.item_title.setText(event.getTitle());
            viewHolder.item_label.setText(event.getLabel());
            Picasso.get().load(event.getImage()).into(viewHolder.item_bg);
            viewHolder.itemContainer.setOnClickListener(e -> {
                //TODO open dish activity
                /*Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);*/
            });
        }
    }

    @Override
    public int getItemCount() {
        if (events == null)
            return 0;
        return events.size();
    }

    public void setEvents(ArrayList<HomepageEvent> events) {
        this.events = events;
    }

}
