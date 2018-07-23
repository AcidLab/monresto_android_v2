package com.monresto.acidlabs.monresto.UI.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.viewPagerRegister)
    ViewPager viewPager;
   /* @BindView(R.id.nextButton)
    Button nextButton;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragmentRegisterLoginInfo fragmentRegisterLoginInfo = new FragmentRegisterLoginInfo();
        FragmentRegisterPersonalInfo fragmentRegisterPersonalInfo = new FragmentRegisterPersonalInfo();
        FragmentRegisterAddress fragmentRegisterAddress = new FragmentRegisterAddress();

        viewPagerAdapter.AddFragment(fragmentRegisterLoginInfo, "fragmentRegisterLoginInfo");
        viewPagerAdapter.AddFragment(fragmentRegisterPersonalInfo, "fragmentRegisterPersonalInfo");
        viewPagerAdapter.AddFragment(fragmentRegisterAddress, "fragmentRegisterAddress");

        viewPager.setAdapter(viewPagerAdapter);

        /*nextButton.setOnClickListener(e -> {
            if(viewPager.getCurrentItem()<3)
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        });*/

        /*ArrayList<Address> addresses = new ArrayList<>();
        Address A = new Address(30.1, 20.1, "ok", "ok", "ok", "ok", "ok", 9999, 5, 22, "yes");

        addresses.add(A);

        UserService userService = new UserService(this);
        userService.register("mrTester","azerty","azerty","tester@az.er","Cool",
                "Tester","whatever","11111","22222","no", addresses);*/

    }


}
