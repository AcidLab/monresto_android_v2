package com.monresto.acidlabs.monresto.UI.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.MainActivity;
import com.monresto.acidlabs.monresto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentGotoItem extends Fragment {
    @BindView(R.id.profileSettingLabel)
    TextView label;
    @BindView(R.id.profileSettingIcon)
    ImageView icon;
    @BindView(R.id.itemProfileSetting)
    ConstraintLayout constraintLayout;

    private String _label;
    private Drawable _drawable;
    private Class _class;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.item_profile_setting, container, false);
        ButterKnife.bind(this, root);

        if(_label!=null)
            label.setText(_label);
        if(_drawable!=null)
            icon.setImageDrawable(_drawable);
        if(_class!=null){
            Intent intent = new Intent(getActivity(), _class);
            constraintLayout.setOnClickListener(e -> startActivity(intent));
        }

        return root;
    }

    public FragmentGotoItem setLabel(String s) {
        _label = s;
        return this;
    }

    public FragmentGotoItem setIcon(Drawable drawable) {
        _drawable = drawable;
        return this;
    }

    public FragmentGotoItem setIntent(Class activity) {
        _class = activity;
        return this;
    }
}
