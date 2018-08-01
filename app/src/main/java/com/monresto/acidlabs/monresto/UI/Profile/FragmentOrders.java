package com.monresto.acidlabs.monresto.UI.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;

import butterknife.ButterKnife;

public class FragmentOrders extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_orders, container, false);
        ButterKnife.bind(this, root);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.layoutOrdersContainer, new FragmentOrdersItem());
        fragmentTransaction.add(R.id.layoutOrdersContainer, new FragmentOrdersItem());
        fragmentTransaction.add(R.id.layoutOrdersContainer, new FragmentOrdersItem());

        fragmentTransaction.commit();

        return root;
    }
}
