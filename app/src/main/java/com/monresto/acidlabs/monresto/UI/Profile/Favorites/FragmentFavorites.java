package com.monresto.acidlabs.monresto.UI.Profile.Favorites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monresto.acidlabs.monresto.R;

import butterknife.ButterKnife;

public class FragmentFavorites extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_favorites, container, false);
        ButterKnife.bind(this, root);

        return root;
    }
}
