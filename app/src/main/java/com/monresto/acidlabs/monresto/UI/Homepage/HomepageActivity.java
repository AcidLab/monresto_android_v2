package com.monresto.acidlabs.monresto.UI.Homepage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.monresto.acidlabs.monresto.GPSTracker;
import com.monresto.acidlabs.monresto.MainActivity;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.InternetCheck;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.monresto.acidlabs.monresto.Config.REQUEST_CODE_ASK_FOR_LOCATION;
import static com.monresto.acidlabs.monresto.Config.REQUEST_CODE_MAP_INFO;
import static com.monresto.acidlabs.monresto.UI.Maps.MapsActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class HomepageActivity extends AppCompatActivity implements UserAsyncResponse {

    @BindView(R.id.homepageRecycler)
    RecyclerView homepageRecycler;

    HomepageAdapter adapter;

    GPSTracker gpsTracker;
    UserService userService;

    //Request for location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);

        adapter = new HomepageAdapter(this);

        ArrayList<String> news = new ArrayList<>();
        news.add("Découvrez les meilleurs restaurants");
        news.add("Click me -> MainActivity");
        homepageRecycler.setLayoutManager(new LinearLayoutManager(this));
        homepageRecycler.setAdapter(adapter);
        adapter.setNews(news);
        adapter.notifyDataSetChanged();

        userService = new UserService(this);

        new InternetCheck(internet -> {
            if (internet) {
                if (!login())
                    displayLocationSettingsRequest(this);
            } else
                Toast.makeText(this, "Cnx Unavailable", Toast.LENGTH_SHORT).show(); //TODO
        });

    }

    public void init() {
        gpsTracker = new GPSTracker(this);
        double lat = gpsTracker.getLatitude();
        double lon = gpsTracker.getLongitude();
        Monresto.setLat(lat);
        Monresto.setLon(lon);
    }

    public boolean login() {
        SharedPreferences sharedPref = getSharedPreferences("login_data", Context.MODE_PRIVATE);
        String savedLogin = sharedPref.getString("passwordLogin", null);
        JSONObject loginObj;
        if (savedLogin != null) {
            try {
                loginObj = new JSONObject(savedLogin);
                userService.login(loginObj.getString("login"), loginObj.getString("password"), sharedPref);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            savedLogin = sharedPref.getString("fbLogin", null);
            if (savedLogin != null) {
                try {
                    loginObj = new JSONObject(savedLogin);
                    User.setInstance(new User(loginObj.getInt("id"), loginObj.getString("email"), loginObj.getString("fname"), loginObj.getString("lname")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Demande d'autorisation")
                        .setMessage("Monresto a besoin de savoir votre position")
                        .setPositiveButton("Accépter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(HomepageActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        init();
                    }
                }
                break;
            }
            default:
                break;
        }
    }


    @Override
    public void onUserLogin(User user) {
        userService.getDetails(user.getId(), true);
    }

    @Override
    public void onUserDetailsReceived(User user) {
        userService.getAddress(User.getInstance().getId());
    }

    @Override
    public void onAddressListReceived(ArrayList<Address> addresses) {
        if (User.getInstance() != null)
            User.getInstance().setAddresses(addresses);
        Intent intent = new Intent(this, SelectAddressActivity.class);
        startActivity(intent);
    }


    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        init();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(HomepageActivity.this, REQUEST_CODE_ASK_FOR_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(HomepageActivity.this, "TODO no permission", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_CODE_ASK_FOR_LOCATION): {
                if (resultCode == Activity.RESULT_OK) {
                    init();
                    break;
                }
            }
        }
    }
}
