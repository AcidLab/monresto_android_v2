package com.monresto.acidlabs.monresto.UI.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.ImageView;

import com.monresto.acidlabs.monresto.Model.Filter;
import com.monresto.acidlabs.monresto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends AppCompatActivity {
    FilterRecyclerViewAdapter adapter;

    //TODO
    @BindView(R.id.recyclerview_address)
    RecyclerView recyclerView;
    @BindView(R.id.btnClose)
    ImageView btnClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO
        //setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        getWindow().setLayout(width, height);

        adapter = new FilterRecyclerViewAdapter(this);
        adapter.setFilters(Filter.getFilters());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        btnClose.setOnClickListener(e -> finish());
    }

    void sendFilter(int filter){
        Intent intent = new Intent();
        intent.putExtra("filter", filter);
        setResult(RESULT_OK);
        finish();
    }
}
