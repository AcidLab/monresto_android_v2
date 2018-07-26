package com.monresto.acidlabs.monresto.UI.Maps;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.monresto.acidlabs.monresto.GPSTracker;
import com.monresto.acidlabs.monresto.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    GPSTracker gpsTracker;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gpsTracker = new GPSTracker(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button button = findViewById(R.id.buttonPickPosition);
        button.setOnClickListener(e -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("lat", mMap.getCameraPosition().target.latitude);
            resultIntent.putExtra("lon", mMap.getCameraPosition().target.longitude);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (gpsTracker.canGetLocation()) {
            LatLng position = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            mMap.addMarker(new MarkerOptions().position(position).title("Ma position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
    }
}