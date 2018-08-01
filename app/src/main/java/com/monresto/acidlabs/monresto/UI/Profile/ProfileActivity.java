package com.monresto.acidlabs.monresto.UI.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Profile.Address.FragmentAddress;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.viewPagerProfile)
    ViewPager viewPagerProfile;
    @BindView(R.id.tabLayoutProfile)
    TabLayout tabLayoutProfile;

    @BindView(R.id.imageProfileSettings)
    ImageView imageSettings;

    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentOrdersItem(), "Commandes");
        adapter.AddFragment(new FragmentHistory(), "Historique");
        adapter.AddFragment(new FragmentFavorites(), "Favoris");
        adapter.AddFragment(new FragmentAddress(), "Adresses");

        viewPagerProfile.setAdapter(adapter);
        viewPagerProfile.setOffscreenPageLimit(4);
        tabLayoutProfile.setupWithViewPager(viewPagerProfile);

        tabLayoutProfile.getTabAt(0).setIcon(R.drawable.icon_orders);
        tabLayoutProfile.getTabAt(1).setIcon(R.drawable.icon_history);
        tabLayoutProfile.getTabAt(2).setIcon(R.drawable.icon_dishes);
        tabLayoutProfile.getTabAt(3).setIcon(R.drawable.icon_address);

        imageSettings.setOnClickListener(e -> {
            Intent intent = new Intent(this, ProfileSettingsActivity.class);
            startActivity(intent);
        });

        viewPagerProfile.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        /*FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.add(R.id.layoutOrdersContainer, new FragmentOrdersItem());
                        fragmentTransaction.add(R.id.layoutOrdersContainer, new FragmentOrdersItem());
                        fragmentTransaction.add(R.id.layoutOrdersContainer, new FragmentOrdersItem());

                        fragmentTransaction.commit();*/
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}