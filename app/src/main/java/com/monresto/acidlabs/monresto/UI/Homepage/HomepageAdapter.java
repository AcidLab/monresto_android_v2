package com.monresto.acidlabs.monresto.UI.Homepage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.MainActivity;
import com.monresto.acidlabs.monresto.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> news;

    public HomepageAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title)
        TextView news_title;
        @BindView(R.id.newsContainer)
        ConstraintLayout newsContainer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public HomepageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_homepage, viewGroup, false);

        return new HomepageAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageAdapter.ViewHolder viewHolder, int i) {
        String faq;
        if (news != null && !news.isEmpty()) {
            faq = news.get(i);
            viewHolder.news_title.setText(faq);
            viewHolder.newsContainer.setOnClickListener(e -> {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (news == null)
            return 0;
        return news.size();
    }

    public void setNews(ArrayList<String> list) {
        this.news = list;
    }

}
