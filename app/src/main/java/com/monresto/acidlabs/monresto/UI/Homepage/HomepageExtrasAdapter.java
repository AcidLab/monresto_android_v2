package com.monresto.acidlabs.monresto.UI.Homepage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Homepage.Snacks.SnacksActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomepageExtrasAdapter extends RecyclerView.Adapter<HomepageExtrasAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> images;
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public HomepageExtrasAdapter(Context context) {
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
    public HomepageExtrasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_homepage_dish, viewGroup, false);

        return new HomepageExtrasAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageExtrasAdapter.ViewHolder viewHolder, int i) {
        String image;
        setFadeAnimation(viewHolder.itemView);
        viewHolder.item_label.setVisibility(View.GONE);
        /*if(dishes != null && dishes.size()>1)
            viewHolder.cardViewBg.setLayoutParams(new ConstraintLayout.LayoutParams(viewHolder.cardViewBg.getMeasuredWidth() - 20, viewHolder.cardViewBg.getMeasuredHeight()));*/
        if (images != null && !images.isEmpty()) {
            image = images.get(i);
            if (i == 0) {
                viewHolder.item_title.setText("SNACKS");
                Picasso.get().load(image).into(viewHolder.item_bg);
                viewHolder.itemContainer.setOnClickListener(e -> {
                    Intent intent = new Intent(context, SnacksActivity.class);
                    context.startActivity(intent);
                });
            } else {
                viewHolder.item_title.setText("COURSIER");
                Picasso.get().load(image).into(viewHolder.item_bg);
                viewHolder.itemContainer.setOnClickListener(e -> {
                    Toast.makeText(context, "Ce service sera bientôt disponible", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (images == null)
            return 0;
        return images.size();
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}
