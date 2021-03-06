package com.monresto.acidlabs.monresto.UI.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Profile.Address.NewAddressActivity;
import com.monresto.acidlabs.monresto.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements UserAsyncResponse {
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.forgotPasswordButton)
    Button forgotPasswordButton;
    @BindView(R.id.textLogin)
    EditText textLogin;
    @BindView(R.id.textPassword)
    EditText textPassword;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;

    private UserService userService;
    private CallbackManager callbackManager;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        userService = new UserService(this);
        sharedPref = getSharedPreferences("login_data", Context.MODE_PRIVATE);

        registerButton.setPaintFlags(registerButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgotPasswordButton.setPaintFlags(forgotPasswordButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(e -> {
            String login = Objects.requireNonNull(textLogin.getText()).toString();
            String password = Objects.requireNonNull(textPassword.getText()).toString();
            progressBar2.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            userService.login(login, password, sharedPref);
        });

        registerButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        textLogin.clearFocus();

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.nextButton);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                doGraphRequest();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });

        textLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                System.out.println("LoginActivity.onFocusChange");
                Utilities.hideKeyboard(LoginActivity.this);
            }
        });




        

    }

    @Override
    public void onUserLogin(User user) {
        if (user != null)
            userService.getDetails(user.getId(), true);
        else {
            progressBar2.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            Monresto.loginPending = false;
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
            Monresto.loginPending = false;
            if (addresses.isEmpty()) {
                Intent intent = new Intent(this, NewAddressActivity.class);
                startActivity(intent);
            } else if (Objects.requireNonNull(getIntent().getExtras()).getBoolean("ask_for_select", false)) {
                Intent intent = new Intent(this, SelectAddressActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }

    @Override
    public void oncheckLoginDispoReceived(boolean isDispo) {

    }

    private void doGraphRequest() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String email = object.getString("email");
                            String socialID = object.getString("id");
                            String fname = object.getString("first_name");
                            String lname = object.getString("last_name");
                            Log.d("onCompleted", fname + " - " + lname);
                            userService.facebookLogin(socialID, email, fname, lname, sharedPref);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        view.clearFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Utilities.hideKeyboard(this);
    }
}
