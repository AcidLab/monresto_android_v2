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
import android.widget.EditText;

import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Profile.FragmentProfileLogin;
import com.monresto.acidlabs.monresto.UI.Profile.FragmentProfileNoLogin;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRegisterLoginInfo extends Fragment {
    @BindView(R.id.textLogin)
    EditText textLogin;
    @BindView(R.id.textEmail)
    EditText textEmail;
    @BindView(R.id.textPassword)
    EditText textPassword;
    @BindView(R.id.textPasswordConfirm)
    EditText textPasswordConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_register_login_info, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    public boolean validate() {
        return !textLogin.getText().toString().equals("") && !textPassword.getText().toString().equals("") && textPassword.getText().toString().equals(textPasswordConfirm.getText().toString());
    }
    public User fill(User user) {
        user.setLogin(textLogin.getText().toString());
        user.setEmail(textEmail.getText().toString());
        user.setPassword(textPassword.getText().toString());
        return user;
    }
}
