package com.monresto.acidlabs.monresto.UI.User;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRegisterPersonalInfo extends Fragment {
    @BindView(R.id.textLastName)
    EditText textLastName;
    @BindView(R.id.textFirstName)
    EditText textFirstName;
    @BindView(R.id.textEmail)
    EditText textEmail;
    @BindView(R.id.textPhoneNumber)
    EditText textPhoneNumber;
    @BindView(R.id.textMobileNumber)
    EditText textMobileNumber;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_register_personal_info, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    public boolean validate() {
        if (textLastName.getText().equals(""))
            return false;
        if (textFirstName.getText().equals(""))
            return false;
        if (textEmail.getText().equals(""))
            return false;
        if (textLastName.getText().equals(""))
            return false;
        if (textPhoneNumber.getText().equals(""))
            return !textMobileNumber.getText().equals("");
        if (textMobileNumber.getText().equals(""))
            return !textPhoneNumber.getText().equals("");

        return true;
    }
    public User fill(User user) {
        user.setFname(textFirstName.getText().toString());
        user.setLname(textLastName.getText().toString());
        user.setEmail(textEmail.getText().toString());
        user.setPhone(textPhoneNumber.getText().toString());
        user.setMobile(textMobileNumber.getText().toString());
        return user;
    }
}