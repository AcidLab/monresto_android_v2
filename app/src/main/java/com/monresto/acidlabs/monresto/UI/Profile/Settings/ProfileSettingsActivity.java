package com.monresto.acidlabs.monresto.UI.Profile.Settings;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.FAQ.FAQActivity;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileSettingsActivity extends AppCompatActivity {
    @BindView(R.id.imageCloseProfileSettings)
    ImageView imageClose;
    @BindView(R.id.linearLayoutProfileSettings)
    LinearLayout linearLayout;
    @BindView(R.id.textProfileTitle)
    TextView textProfileTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        ButterKnife.bind(this);

        imageClose.setOnClickListener(e -> finish());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof FragmentGotoItem) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

        UserService userService = new UserService(this);

        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Informations du profil").setIcon(getResources().getDrawable(R.drawable.male_user, getTheme())).setIntent(EditProfileActivity.class));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Mes adresses").setIcon(getResources().getDrawable(R.drawable.icon_address, getTheme())).setIntent(AddressSettingsActivity.class));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Promotions").setIcon(getResources().getDrawable(R.drawable.discount_50, getTheme())));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Bons de réduction").setIcon(getResources().getDrawable(R.drawable.cutting_coupon, getTheme())));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("FAQ").setIcon(getResources().getDrawable(R.drawable.faq_50, getTheme())).setIntent(FAQActivity.class));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("A propos").setIcon(getResources().getDrawable(R.drawable.info_50, getTheme())));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Se déconnecter").setIcon(getResources().getDrawable(R.drawable.log_out, getTheme())).setAction(e -> {
            userService.logout();
            finish();
        }));

        fragmentTransaction.commit();

        textProfileTitle.setText(String.format("%s %s", User.getInstance().getFname(), User.getInstance().getLname()));
    }
    @Override
    public void onResume(){
        super.onResume();
        textProfileTitle.setText(String.format("%s %s", User.getInstance().getFname(), User.getInstance().getLname()));
    }
}
