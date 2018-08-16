package com.monresto.acidlabs.monresto.UI.User;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;
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
import com.monresto.acidlabs.monresto.UI.User.RegisterFragments.FragmentRegisterAddress;
import com.monresto.acidlabs.monresto.UI.User.RegisterFragments.FragmentRegisterLoginInfo;
import com.monresto.acidlabs.monresto.UI.User.RegisterFragments.FragmentRegisterPersonalInfo;
import com.monresto.acidlabs.monresto.UI.User.RegisterFragments.FragmentRegisterSubmit;
import com.monresto.acidlabs.monresto.Utilities;

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

    Address address = new Address();
    Geocoder geocoder;

    User newUser;

    FragmentRegisterAddress fragmentRegisterAddress;
    FragmentRegisterSubmit fragmentRegisterSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragmentRegisterLoginInfo fragmentRegisterLoginInfo = new FragmentRegisterLoginInfo();
        FragmentRegisterPersonalInfo fragmentRegisterPersonalInfo = new FragmentRegisterPersonalInfo();
        fragmentRegisterAddress = new FragmentRegisterAddress();
        fragmentRegisterSubmit = new FragmentRegisterSubmit();

        viewPagerAdapter.AddFragment(fragmentRegisterLoginInfo, "fragmentRegisterLoginInfo");
        viewPagerAdapter.AddFragment(fragmentRegisterPersonalInfo, "fragmentRegisterPersonalInfo");
        viewPagerAdapter.AddFragment(fragmentRegisterAddress, "fragmentRegisterAddress");
        viewPagerAdapter.AddFragment(fragmentRegisterSubmit, "fragmentRegisterSubmit");

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==3)
                    nextButton.setText("Valider");
                else
                    nextButton.setText("Suivant");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        UserService userService = new UserService(this);
        newUser = new User(0, "", "", "");

        geocoder = new Geocoder(this);

        nextButton.setOnClickListener(e -> {
            Utilities.hideKeyboard(this);
            switch (viewPager.getCurrentItem()) {
                case 0:
                    nextButton.setText("Suivant");
                    switch (fragmentRegisterLoginInfo.validate()) {
                        case 1:
                            fragmentRegisterLoginInfo.fill(newUser);
                            userService.checkLoginDispo(newUser.getLogin(), newUser.getEmail());
                            break;
                        case -1:
                            Toast.makeText(this, "Veuillez renseigner tout les champs.", Toast.LENGTH_SHORT).show();
                            break;
                        case -2:
                            Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:
                    if (fragmentRegisterPersonalInfo.validate()) {
                        fragmentRegisterPersonalInfo.fill(newUser);
                        Intent intent = new Intent(this, MapsActivity.class);
                        startActivityForResult(intent, 0);
                    } else {
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
                        fragmentRegisterSubmit.fill(newUser);
                        viewPager.setCurrentItem(3);
                    } else
                        Toast.makeText(this, "Veuillez renseigner tout les champs.", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    userService.register(newUser.getLogin(), newUser.getPassword(), newUser.getPassword_confirm(), newUser.getEmail(), newUser.getFname(),
                            newUser.getLname(), newUser.getCivility(), newUser.getPhone(), newUser.getMobile(), newUser.getComment(), newUser.getAddresses());
                    finish();
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
                        if (addressList != null && !addressList.isEmpty()) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (viewPager.getCurrentItem()) {
                case 1:
                    viewPager.setCurrentItem(0);
                    break;
                case 2:
                    viewPager.setCurrentItem(1);
                    break;
                case 3:
                    viewPager.setCurrentItem(0);
                    break;
                default:
                    return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
