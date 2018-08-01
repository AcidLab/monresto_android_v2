package com.monresto.acidlabs.monresto.UI.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Model.Order;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Profile.Address.FragmentAddress;
import com.monresto.acidlabs.monresto.UI.Profile.Favorites.FragmentFavorites;
import com.monresto.acidlabs.monresto.UI.Profile.History.FragmentHistory;
import com.monresto.acidlabs.monresto.UI.Profile.Orders.FragmentOrders;
import com.monresto.acidlabs.monresto.UI.Profile.Settings.ProfileSettingsActivity;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements UserAsyncResponse {
    @BindView(R.id.viewPagerProfile)
    ViewPager viewPagerProfile;
    @BindView(R.id.tabLayoutProfile)
    TabLayout tabLayoutProfile;

    @BindView(R.id.imageProfileSettings)
    ImageView imageSettings;

    FragmentAddress fragmentAddress;

    ViewPagerAdapter adapter;

    FragmentOrders fragmentOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        fragmentAddress = new FragmentAddress().setContext(this);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        fragmentOrders = new FragmentOrders();

        adapter.AddFragment(fragmentOrders, "Commandes");
        adapter.AddFragment(new FragmentHistory(), "Historique");
        adapter.AddFragment(new FragmentFavorites(), "Favoris");
        adapter.AddFragment(fragmentAddress, "Adresses");

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
    }

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


    @Override
    public void onPendingReceived(ArrayList<Order> orders) {
        fragmentOrders.fillPending(orders);
        System.out.println("ProfileActivity.onPendingReceived");
    }

    @Override
    public void onAddressListReceived(ArrayList<Address> addresses) {
        fragmentAddress.updateList(addresses);
    }

}
