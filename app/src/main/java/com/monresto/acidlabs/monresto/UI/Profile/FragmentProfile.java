package com.monresto.acidlabs.monresto.UI.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentProfile extends Fragment {
    @BindView(R.id.viewPagerProfile)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.AddFragment(new FragmentProfileNoLogin(), "no_login");
        viewPagerAdapter.AddFragment(new FragmentProfileLogin(), "login");

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setCurrentItem(0);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (User.getInstance() == null)
            viewPager.setCurrentItem(0);
        else
            viewPager.setCurrentItem(1);
    }
}
