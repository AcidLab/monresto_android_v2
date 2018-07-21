package com.monresto.acidlabs.monresto.UI.User;

import android.support.v4.app.Fragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Button;
import android.widget.EditText;

import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Profile.FragmentProfile;

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

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        userService = new UserService(this);

        registerButton.setPaintFlags(registerButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgotPasswordButton.setPaintFlags(forgotPasswordButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(e -> {
            String login = Objects.requireNonNull(textLogin.getText()).toString();
            String password = Objects.requireNonNull(textPassword.getText()).toString();

            userService.login(login, password);
        });


    }

    @Override
    public void onUserLogin(User user) {
        userService.getDetails(user.getId(), true);
    }

    @Override
    public void onUserDetailsReceived(User user) {
        finish();
    }
}
