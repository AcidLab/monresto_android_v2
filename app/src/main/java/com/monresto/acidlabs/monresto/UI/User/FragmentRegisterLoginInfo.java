package com.monresto.acidlabs.monresto.UI.User;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Profile.FragmentProfileLogin;
import com.monresto.acidlabs.monresto.UI.Profile.FragmentProfileNoLogin;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import butterknife.ButterKnife;

public class FragmentRegisterLoginInfo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_register_login_info, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        System.out.println(((ViewPager)view.getParent()).getLayoutParams().height);
        System.out.println(view.getLayoutParams().height);

        //view.setLayoutParams(new ViewPager.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT));
    }
}
