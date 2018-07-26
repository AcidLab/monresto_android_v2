package com.monresto.acidlabs.monresto.UI.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Maps.MapsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRegisterAddress extends Fragment {
    private GoogleMap mMap;
    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    @BindView(R.id.textAddress)
    EditText textAddress;
    @BindView(R.id.textStreet)
    EditText textStreet;
    @BindView(R.id.textAppart)
    EditText textAppart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_register_address, container, false);
        ButterKnife.bind(this, root);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        return root;
    }

    public boolean validate() {
        return true;
    }
    public Address fill(Address address) {
        address.setAdresse(textAddress.getText().toString());
        address.setRue(textStreet.getText().toString());
        address.setAppartement(textAppart.getText().toString());
        return address;
    }
}
