package com.monresto.acidlabs.monresto.UI.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.viewPagerRegister)
    ViewPager viewPager;
    @BindView(R.id.nextButton)
    Button nextButton;

    boolean isValid = true;

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

        UserService userService = new UserService(this);
        User newUser = new User(0 ,"" ,"" ,"");

        nextButton.setOnClickListener(e -> {
            switch (viewPager.getCurrentItem()) {
                case 0:
                    if(fragmentRegisterLoginInfo.validate()){
                        fragmentRegisterLoginInfo.fill(newUser);
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                    break;

                case 1:
                    if(fragmentRegisterPersonalInfo.validate()){
                        fragmentRegisterPersonalInfo.fill(newUser);
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                    break;

                case 2:

                    if(false)
                    userService.register(newUser.getLogin(),newUser.getPassword(),newUser.getPassword_confirm(),newUser.getEmail(),newUser.getFname(),
                            newUser.getLname(),newUser.getCivility(),newUser.getPhone(),newUser.getMobile(),"", newUser.getAddresses());
                    break;
                default:
                    break;

            }

        });


        /*ArrayList<Address> addresses = new ArrayList<>();
        Address A = new Address(30.1, 20.1, "ok", "ok", "ok", "ok", "ok", 9999, 5, 22, "yes");

        addresses.add(A);

        userService.register("mrTester","azerty","azerty","tester@az.er","Cool",
                "Tester","whatever","11111","22222","no", addresses);*/

    }


}
