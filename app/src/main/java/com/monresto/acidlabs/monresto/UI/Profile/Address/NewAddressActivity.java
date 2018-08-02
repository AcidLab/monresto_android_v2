package com.monresto.acidlabs.monresto.UI.Profile.Address;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.Model.City;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.City.CityAsyncResponse;
import com.monresto.acidlabs.monresto.Service.City.CityService;
import com.monresto.acidlabs.monresto.UI.Maps.MapsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAddressActivity extends AppCompatActivity implements CityAsyncResponse {
    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    @BindView(R.id.textAddress)
    EditText textAddress;
    @BindView(R.id.textStreet)
    EditText textStreet;
    @BindView(R.id.textAppart)
    EditText textAppart;
    @BindView(R.id.textComment)
    EditText textComment;
    @BindView(R.id.city_spinner)
    Spinner citySpinner;
    @BindView(R.id.buttonSubmitAddress)
    Button buttonSubmitAddress;

    ArrayList<City> cities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_new);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, 0);

        buttonSubmitAddress.setOnClickListener(e -> {
            Toast.makeText(this, "Mch taw", Toast.LENGTH_SHORT).show();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(this,
                R.array.default_city_array, android.R.layout.simple_spinner_item);
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter_city);

        cities = new ArrayList<>();
        CityService cityService = new CityService(this);
        cityService.getCities();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (0): {
                if (resultCode == Activity.RESULT_OK) {
                    double lat = data.getDoubleExtra("lat", 36.849109);
                    double lon = data.getDoubleExtra("lon", 10.166124);
                    /*android.location.Address currentAddress;
                    try {
                        List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
                        if (addressList != null && !addressList.isEmpty()) {
                            currentAddress = addressList.get(0);
                            address.setPostalCode(currentAddress.getPostalCode());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    address.setLat(lat);
                    address.setLon(lon);*/
                }
                break;
            }
        }
    }

    @Override
    public void onCitiesReceived(ArrayList<City> cities) {
        ArrayList<CharSequence> list = new ArrayList<>();
        for(int i = 0; i<cities.size();i++)
            list.add(cities.get(i).getName());

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
    }
}
