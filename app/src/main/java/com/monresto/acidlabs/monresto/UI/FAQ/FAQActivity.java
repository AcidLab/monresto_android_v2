package com.monresto.acidlabs.monresto.UI.FAQ;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.FAQ;
import com.monresto.acidlabs.monresto.Model.Order;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.FAQ.FAQAsyncResponse;
import com.monresto.acidlabs.monresto.Service.FAQ.FAQService;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Profile.Address.FragmentAddress;
import com.monresto.acidlabs.monresto.UI.Profile.Favorites.FragmentFavorites;
import com.monresto.acidlabs.monresto.UI.Profile.History.FragmentHistory;
import com.monresto.acidlabs.monresto.UI.Profile.Orders.FragmentOrders;
import com.monresto.acidlabs.monresto.UI.Profile.Settings.ProfileSettingsActivity;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;
import com.monresto.acidlabs.monresto.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQActivity extends AppCompatActivity implements FAQAsyncResponse {
    @BindView(R.id.imageProfileBack)
    ImageView imageProfileBack;
    @BindView(R.id.faq_loading)
    ConstraintLayout faq_loading;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    FAQRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        adapter = new FAQRecyclerViewAdapter(this);

        imageProfileBack.setOnClickListener(e -> {
            finish();
        });

        FAQService faqService = new FAQService(this);
        faqService.getAll();
    }


    @Override
    public void processFinish(ArrayList<FAQ> FAQList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setFAQ(FAQList);
        adapter.notifyDataSetChanged();

        faq_loading.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
