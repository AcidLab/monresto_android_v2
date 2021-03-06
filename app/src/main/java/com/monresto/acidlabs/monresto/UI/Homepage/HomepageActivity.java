package com.monresto.acidlabs.monresto.UI.Homepage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.GPSTracker;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.HomepageConfig;
import com.monresto.acidlabs.monresto.Model.HomepageDish;
import com.monresto.acidlabs.monresto.Model.HomepageEvent;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.ObjectSerializer;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.InternetCheck;
import com.monresto.acidlabs.monresto.Service.Homepage.HomepageAsyncResponse;
import com.monresto.acidlabs.monresto.Service.Homepage.HomepageService;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Cart.CartActivity;
import com.monresto.acidlabs.monresto.UI.Delivery.DeliveryMapActivity;
import com.monresto.acidlabs.monresto.UI.Main.MainActivity;
import com.monresto.acidlabs.monresto.UI.Maps.MapsActivity;
import com.monresto.acidlabs.monresto.UI.Profile.Address.NewAddressActivity;
import com.monresto.acidlabs.monresto.UI.Profile.ProfileActivity;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;
import com.monresto.acidlabs.monresto.Utilities;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static com.monresto.acidlabs.monresto.Config.REQUEST_CODE_ASK_FOR_LOCATION;
import static com.monresto.acidlabs.monresto.Model.Monresto.loginPending;
import static com.monresto.acidlabs.monresto.UI.Maps.MapsActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class HomepageActivity extends AppCompatActivity implements UserAsyncResponse, HomepageAsyncResponse {

    @BindView(R.id.dishesRecycler)
    RecyclerView dishesRecycler;
    @BindView(R.id.eventsRecycler)
    RecyclerView eventsRecycler;
    @BindView(R.id.extrasRecycler)
    RecyclerView extrasRecycler;
    @BindView(R.id.config_bg)
    ImageView config_bg;
    @BindView(R.id.home_profile_icon)
    ImageView home_profile_icon;
    @BindView(R.id.cart_btn)
    ImageView cart_btn;
    @BindView(R.id.configContainer)
    ConstraintLayout configContainer;
    @BindView(R.id.homepage_swiper)
    SwipeRefreshLayout homepage_swiper;
    @BindView(R.id.platsJour)
    TextView platsJour;
    @BindView(R.id.evenements)
    TextView evenements;
    @BindView(R.id.jibly)
    TextView jibly;
    @BindView(R.id.homepageLayout)
    ConstraintLayout homepageLayout;

    HomepageDishesAdapter Dishesadapter;
    HomepageEventsAdapter Eventsadapter;
    HomepageExtrasAdapter Extrasadapter;

    GPSTracker gpsTracker;
    UserService userService;
    HomepageService homepageService;
    private boolean askedMain = false;
    //Request for location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);

        //Intent i = new Intent(this, DeliveryMapActivity.class);
        //startActivity(i);

        Dishesadapter = new HomepageDishesAdapter(this);
        Eventsadapter = new HomepageEventsAdapter(this);
        Extrasadapter = new HomepageExtrasAdapter(this);

        userService = new UserService(this);
        homepageService = new HomepageService(this);

        dishesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dishesRecycler.setAdapter(Dishesadapter);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        eventsRecycler.setAdapter(Eventsadapter);
        extrasRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        extrasRecycler.setAdapter(Extrasadapter);

        dishesRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        homepage_swiper.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        homepage_swiper.setEnabled(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }
        });
        eventsRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        homepage_swiper.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        homepage_swiper.setEnabled(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }
        });
        extrasRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        homepage_swiper.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        homepage_swiper.setEnabled(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }
        });
        homepageService.getAll();

        home_profile_icon.setOnClickListener(e -> {
            if (User.getInstance() == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        configContainer.setOnClickListener(view -> {
            if (loginPending) {
                askedMain = true;
                Toast.makeText(this, "Connexion en cours.. Veuillez patienter", Toast.LENGTH_LONG).show();
                //Utilities.statusChanger(this, R.layout.fragment_loading, homepageLayout, homepage_swiper);
                return;
            }
            Intent intent;
            if (User.getInstance() == null) {
                /*intent = new Intent(this, MapsActivity.class);
                intent.putExtra("update_position", true);
                startActivityForResult(intent, Config.REQUEST_CODE_POSITION_SELECT);*/
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(this), Config.REQUEST_PLACE_PICKER);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e1) {
                    e1.printStackTrace();
                }
            } else {
                intent = new Intent(this, SelectAddressActivity.class);
                startActivityForResult(intent, Config.REQUEST_CODE_ADRESS_SELECT);
            }

        });

        homepage_swiper.setOnRefreshListener(() -> homepageService.getAll());

        if (checkLocationPermission())
            checkInternet();

        SharedPreferences sharedPreferences = getSharedPreferences("itemsList", Context.MODE_PRIVATE);
        String serialItems;
        if (sharedPreferences.contains("items")) {
            serialItems = sharedPreferences.getString("items", "");
            ShoppingCart shoppingCart = (ShoppingCart) ObjectSerializer.deserialize(serialItems);

            if (shoppingCart != null) {
                ShoppingCart.setInstance((ShoppingCart) ObjectSerializer.deserialize(serialItems));
            }
        }

        cart_btn.setOnClickListener(e -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    public void checkInternet() {
        new InternetCheck(internet -> {
            if (internet) {
                if (!login())
                    displayLocationSettingsRequest(this);
            }
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
                        checkInternet();
                    } else {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
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
        if (user != null)
            userService.getDetails(user.getId(), true);
        else {
            askedMain = false;
            loginPending = false;
        }
    }

    @Override
    public void onUserDetailsReceived(User user) {
        userService.getAddress(User.getInstance().getId());
    }

    @Override
    public void onAddressListReceived(ArrayList<Address> addresses) {
        if (User.getInstance() != null) {
            User.getInstance().setAddresses(addresses);
            loginPending = false;
            if(addresses.isEmpty()){
                Intent intent = new Intent(this, NewAddressActivity.class);
                startActivity(intent);
            }
            else if (askedMain) {
                Intent intent = new Intent(this, SelectAddressActivity.class);
                startActivityForResult(intent, Config.REQUEST_CODE_ADRESS_SELECT);
            }
        }
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
            case (Config.REQUEST_CODE_ASK_FOR_LOCATION): {
                if (resultCode == Activity.RESULT_OK) {
                    init();
                }
            }
            break;
            case (Config.REQUEST_CODE_POSITION_SELECT): {
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case (Config.REQUEST_CODE_ADRESS_SELECT): {
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case Config.REQUEST_PLACE_PICKER: {
                if(data!=null){
                    Place place = PlacePicker.getPlace(data, this);
                    Monresto.setLat( place.getLatLng().latitude);
                    Monresto.setLon(place.getLatLng().longitude);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }

            }
            break;
        }
    }

    @Override
    public void onHomepageConfigReceived(HomepageConfig config) {



        Picasso.get().load(config.getCover_image()).into(config_bg);

        ShowcaseConfig configs = new ShowcaseConfig();
        configs.setDelay(500); // half second between each showcase view
        configs.setMaskColor(Color.parseColor("#cc1eb999"));
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "100");

        sequence.setConfig(configs);

        sequence.addSequenceItem(config_bg,
                "Découvrez les meilleurs restos", "SUIVANT");

        sequence.addSequenceItem(extrasRecycler,
                "Mais aussi deux autres services", "COMPRIS");




        HomepageConfig.setInstance(config);
        homepage_swiper.setRefreshing(false);

        ArrayList<String> images = new ArrayList<>();
        images.add(config.getSnack_image());
        images.add(config.getDelivery_image());
        Extrasadapter.setImages(images);
        Extrasadapter.notifyDataSetChanged();
        jibly.setVisibility(View.VISIBLE);
        extrasRecycler.setVisibility(View.VISIBLE);
        sequence.start();



    }

    @Override
    public void onHomepageEventsReceived(ArrayList<HomepageEvent> events) {
        Eventsadapter.setEvents(events);
        Eventsadapter.notifyDataSetChanged();
        homepage_swiper.setRefreshing(false);
        if (events.isEmpty()) {
            evenements.setVisibility(View.GONE);
            eventsRecycler.setVisibility(View.GONE);
        } else {
            evenements.setVisibility(View.VISIBLE);
            eventsRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHomepageDishesReceived(ArrayList<HomepageDish> dishes) {
        Dishesadapter.setDishes(dishes);
        Dishesadapter.notifyDataSetChanged();
        homepage_swiper.setRefreshing(false);
        if (dishes.isEmpty()) {
            platsJour.setVisibility(View.GONE);
            dishesRecycler.setVisibility(View.GONE);
        } else {
            platsJour.setVisibility(View.VISIBLE);
            dishesRecycler.setVisibility(View.VISIBLE);
        }


    }
}
