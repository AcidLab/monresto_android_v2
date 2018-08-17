package com.monresto.acidlabs.monresto.UI.Homepage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.monresto.acidlabs.monresto.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageActivity extends AppCompatActivity {

    @BindView(R.id.homepageRecycler)
    RecyclerView homepageRecycler;

    HomepageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ButterKnife.bind(this);

        adapter = new HomepageAdapter(this);

        ArrayList<String> news = new ArrayList<>();
        news.add("Découvrez les meilleurs restaurants");
        news.add("Click me -> MainActivity");
        homepageRecycler.setLayoutManager(new LinearLayoutManager(this));
        homepageRecycler.setAdapter(adapter);
        adapter.setNews(news);
        adapter.notifyDataSetChanged();

    }

}
