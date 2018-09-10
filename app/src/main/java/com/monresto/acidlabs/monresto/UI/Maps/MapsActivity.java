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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.monresto.acidlabs.monresto.GPSTracker;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    GPSTracker gpsTracker;
    Geocoder geocoder;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    double lat;
    double lng;
    String title;

    @BindView(R.id.text_address_position)
    TextView text_address_position;

    List<Address> addresses;
    private boolean updatePosition;

    LatLng[] latlng = new LatLng[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);


        gpsTracker = new GPSTracker(this);
        geocoder = new Geocoder(getBaseContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Button button = findViewById(R.id.buttonPickPosition);
        ImageView finishBtn = findViewById(R.id.buttonFinish);

        button.setOnClickListener(e -> closeWithResults());
        finishBtn.setOnClickListener(e -> closeWithResults());

        Intent intent = getIntent();
        updatePosition = intent.getBooleanExtra("update_position", false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        boolean update = intent.getBooleanExtra("update", false);
        if (update) {
            this.lat = intent.getDoubleExtra("lat", 0);
            this.lng = intent.getDoubleExtra("lng", 0);
            LatLng position = new LatLng(this.lat, this.lng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
        }

        else if(gpsTracker.canGetLocation()) {
            LatLng position = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            this.lat = position.latitude;
            this.lng = position.longitude;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
        }

        mMap.setOnCameraIdleListener(() -> {
            try {
                addresses = geocoder.getFromLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude, 1);
                title = "Votre adresse";
                if (!addresses.isEmpty()) {
                    title = addresses.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (text_address_position != null) {
                text_address_position.setText(title);
            }
        });
    }

    public void closeWithResults() {
        lat = mMap.getCameraPosition().target.latitude;
        lng = mMap.getCameraPosition().target.longitude;

        Intent resultIntent = new Intent();
        resultIntent.putExtra("lat", lat);
        resultIntent.putExtra("lon", lng);
        setResult(Activity.RESULT_OK, resultIntent);

        if(updatePosition){
            Monresto.setLat(lat);
            Monresto.setLon(lng);
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }
}