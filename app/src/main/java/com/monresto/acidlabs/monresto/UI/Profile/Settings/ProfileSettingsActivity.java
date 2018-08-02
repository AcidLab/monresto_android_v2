package com.monresto.acidlabs.monresto.UI.Profile.Settings;

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

        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Informations du profil").setIcon(getResources().getDrawable(R.drawable.user_male, getTheme())).setIntent(LoginActivity.class));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Mes adresses").setIcon(getResources().getDrawable(R.drawable.mail_ad, getTheme())));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Promotions").setIcon(getResources().getDrawable(R.drawable.discount, getTheme())));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Bons de réduction").setIcon(getResources().getDrawable(R.drawable.vourcher, getTheme())));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("FAQ").setIcon(getResources().getDrawable(R.drawable.faq, getTheme())).setIntent(FAQActivity.class));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("A propos").setIcon(getResources().getDrawable(R.drawable.about, getTheme())));
        fragmentTransaction.add(R.id.linearLayoutProfileSettings, new FragmentGotoItem().setLabel("Se déconnecter").setIcon(getResources().getDrawable(R.drawable.logout, getTheme())));

        fragmentTransaction.commit();

        textProfileTitle.setText(String.format("%s %s", User.getInstance().getFname(), User.getInstance().getLname()));

    }
}
