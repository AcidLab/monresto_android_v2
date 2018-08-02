package com.monresto.acidlabs.monresto.UI.Maps;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.monresto.acidlabs.monresto.GPSTracker;
import com.monresto.acidlabs.monresto.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    GPSTracker gpsTracker;
    Geocoder geocoder;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    double lat;
    double lng;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gpsTracker = new GPSTracker(this);
        geocoder = new Geocoder(getBaseContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Button button = findViewById(R.id.buttonPickPosition);
        ImageView finishBtn = findViewById(R.id.buttonFinish);

        button.setOnClickListener(e -> onBackPressed());
        finishBtn.setOnClickListener(e -> onBackPressed());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (gpsTracker.canGetLocation()) {
            LatLng position = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            mMap.addMarker(new MarkerOptions().position(position).title("Ma position")).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12));

            this.lat = position.latitude;
            this.lng = position.longitude;
        }

        mMap.setOnMapClickListener(e -> {
            List<Address> addresses = new ArrayList<>();
            mMap.clear();
            try {
                addresses = geocoder.getFromLocation(e.latitude, e.longitude, 1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String title = "Votre adresse";
            if (!addresses.isEmpty()) {
                title = addresses.get(0).getFeatureName();
            }
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(e.latitude, e.longitude))
                    .title(title)).showInfoWindow();

            this.lat = e.latitude;
            this.lng = e.longitude;

        });
    }

    @Override
    public void onBackPressed()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("lat", this.lat);
        resultIntent.putExtra("lon", this.lng);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}