package com.monresto.acidlabs.monresto.UI.Homepage.Snacks;

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

import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SnacksAdapter extends RecyclerView.Adapter<SnacksAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Dish> snacks;
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public SnacksAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView item_title;
        @BindView(R.id.item_price)
        TextView item_price;
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
    public SnacksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_snack, viewGroup, false);

        return new SnacksAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SnacksAdapter.ViewHolder viewHolder, int i) {
        Dish snack;
        setFadeAnimation(viewHolder.itemView);
        if (snacks != null && !snacks.isEmpty()) {
            snack = snacks.get(i);
            viewHolder.item_title.setText(Utilities.decodeUTF(snack.getTitle()));
            viewHolder.item_price.setText(snack.getPrice() + " TND");
            Picasso.get().load(snack.getImagePath()).into(viewHolder.item_bg);
            viewHolder.itemContainer.setOnClickListener(e -> {
                ShoppingCart.getInstance().addToCart(snack);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (snacks == null)
            return 0;
        return snacks.size();
    }

    public void setSnacks(ArrayList<Dish> snacks) {
        this.snacks = snacks;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}
