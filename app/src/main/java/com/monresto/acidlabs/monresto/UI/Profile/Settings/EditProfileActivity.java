package com.monresto.acidlabs.monresto.UI.Profile.Settings;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.FAQ.FAQActivity;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity implements UserAsyncResponse {
    @BindView(R.id.buttonBack)
    ImageView buttonBack;
    @BindView(R.id.textFname)
    TextView textFname;
    @BindView(R.id.textLname)
    TextView textLname;
    @BindView(R.id.textEmail)
    TextView textEmail;
    @BindView(R.id.textPhoneNumber)
    TextView textPhoneNumber;
    @BindView(R.id.buttonSubmit)
    TextView buttonSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings_edit);
        ButterKnife.bind(this);

        User user = User.getInstance();
        textFname.setText(user.getFname());
        textLname.setText(user.getLname());
        textEmail.setText(user.getEmail());
        textPhoneNumber.setText(user.getMobile());

        buttonBack.setOnClickListener(e -> finish());
        buttonSubmit.setOnClickListener(e -> {
            user.setFname(textFname.getText().toString());
            user.setLname(textLname.getText().toString());
            user.setEmail(textEmail.getText().toString());
            user.setMobile(textPhoneNumber.getText().toString());

            UserService userService = new UserService(this);
            userService.updateProfile(user);
        });
    }



    @Override
    public void onUserProfileUpdated(){
        Toast.makeText(this, "Vos données ont été mis a jour.", Toast.LENGTH_SHORT).show();
    }
}
