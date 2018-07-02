package com.monresto.acidlabs.monresto.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserService;

import java.util.ArrayList;

@SuppressLint("Registered")
public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Address> addresses = new ArrayList<>();
        Address A = new Address(30.1, 20.1, "ok", "ok", "ok", "ok", "ok", 9999, 5, 22, "yes");

        addresses.add(A);

        UserService userService = new UserService(this);
        userService.register("wxcv2","azerty","azerty","wxcv2@az.er","namefirst",
                "namelast","whatever","11111","22222","no", addresses);

    }


}
