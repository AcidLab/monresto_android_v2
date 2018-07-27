package com.monresto.acidlabs.monresto.UI.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.City;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.City.CityAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Maps.MapsActivity;
import com.monresto.acidlabs.monresto.UI.Restaurants.ViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements UserAsyncResponse, CityAsyncResponse {
    @BindView(R.id.viewPagerRegister)
    ViewPager viewPager;
    @BindView(R.id.nextButton)
    Button nextButton;

    boolean isValid = true;
    Address address = new Address();
    Geocoder geocoder;

    boolean loginDispo = true;
    User newUser;

    FragmentRegisterAddress fragmentRegisterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragmentRegisterLoginInfo fragmentRegisterLoginInfo = new FragmentRegisterLoginInfo();
        FragmentRegisterPersonalInfo fragmentRegisterPersonalInfo = new FragmentRegisterPersonalInfo();
        fragmentRegisterAddress = new FragmentRegisterAddress();

        viewPagerAdapter.AddFragment(fragmentRegisterLoginInfo, "fragmentRegisterLoginInfo");
        viewPagerAdapter.AddFragment(fragmentRegisterPersonalInfo, "fragmentRegisterPersonalInfo");
        viewPagerAdapter.AddFragment(fragmentRegisterAddress, "fragmentRegisterAddress");

        viewPager.setAdapter(viewPagerAdapter);

        UserService userService = new UserService(this);
        newUser = new User(0, "", "", "");

        geocoder = new Geocoder(this);

        nextButton.setOnClickListener(e -> {
            switch (viewPager.getCurrentItem()) {
                case 0:
                    nextButton.setText("Suivant");
                    if (fragmentRegisterLoginInfo.validate()) {
                        fragmentRegisterLoginInfo.fill(newUser);
                        userService.checkLoginDispo(newUser.getLogin(), newUser.getEmail());
                    }
                    break;
                case 1:
                    nextButton.setText("Valider");
                    if (fragmentRegisterPersonalInfo.validate()) {
                        fragmentRegisterPersonalInfo.fill(newUser);
                        Intent intent = new Intent(this, MapsActivity.class);
                        startActivityForResult(intent, 0);
                    }
                    else{
                        Toast.makeText(this, "Veuillez renseigner tout les champs.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 2:
                    if (fragmentRegisterAddress.validate()) {
                        address = fragmentRegisterAddress.fill(address);
                        newUser = fragmentRegisterAddress.addComment(newUser);
                        ArrayList<Address> addresses = new ArrayList<>();
                        addresses.add(address);
                        newUser.setAddresses(addresses);
                        userService.register(newUser.getLogin(), newUser.getPassword(), newUser.getPassword_confirm(), newUser.getEmail(), newUser.getFname(),
                                newUser.getLname(), newUser.getCivility(), newUser.getPhone(), newUser.getMobile(), newUser.getComment(), newUser.getAddresses());
                        finish();
                    }else
                        Toast.makeText(this, "Veuillez renseigner tout les champs.", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;

            }
        });
    }


    @Override
    public void onUserLogin(User user) {

    }

    @Override
    public void onUserDetailsReceived(User user) {

    }

    @Override
    public void oncheckLoginDispoReceived(boolean isDispo) {
        if (isDispo) {
            viewPager.setCurrentItem(1);
        } else
            Toast.makeText(this, "Ce login est déja utilisé", Toast.LENGTH_SHORT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (0): {
                if (resultCode == Activity.RESULT_OK) {
                    viewPager.setCurrentItem(2);

                    double lat = data.getDoubleExtra("lat", 36.849109);
                    double lon = data.getDoubleExtra("lon", 10.166124);
                    android.location.Address currentAddress;
                    try {
                        List<android.location.Address> addressList = geocoder.getFromLocation(lat, lon, 1);
                        if (addressList != null && !addressList.isEmpty()){
                            currentAddress = addressList.get(0);
                            address.setPostalCode(currentAddress.getPostalCode());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    address.setLat(lat);
                    address.setLon(lon);
                }
                break;
            }
        }
    }

    @Override
    public void onCitiesReceived(ArrayList<City> cities) {
        fragmentRegisterAddress.fillCities(cities);
    }
}
