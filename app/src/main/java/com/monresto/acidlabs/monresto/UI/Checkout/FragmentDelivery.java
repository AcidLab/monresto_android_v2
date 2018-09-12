package com.monresto.acidlabs.monresto.UI.Checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.RadioListAdapter;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDelivery extends Fragment {
    @BindView(R.id.address)
    TextView textAddress;

    @BindView(R.id.paymentMethods)
    RecyclerView paymentMethods;

    @BindView(R.id.paymentItemUnavailable)
    RecyclerView paymentItemUnavailable;
    @BindView(R.id.deliveryDate)
    RecyclerView deliveryDate;

    private Address address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_checkout_livraison, container, false);
        ButterKnife.bind(this, root);

        address = User.getInstance().getSelectedAddress();
        textAddress.setText(address.getAdresse());
        textAddress.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), SelectAddressActivity.class);
            startActivity(intent);
        });

        initRecyclerViews();

        return root;
    }

    public RecyclerView getPaymentMethods(){
        return paymentMethods;
    }
    public RecyclerView getItemUnavailable(){
        return paymentItemUnavailable;
    }
    public RecyclerView getDeliveryDate(){
        return deliveryDate;
    }

    void initRecyclerViews() {
        RadioListAdapter radioListAdapter;
        CharSequence[] subjects;


        //1
        subjects = getResources().getStringArray(R.array.payment_methods);
        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), getContext());
        paymentMethods.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentMethods.setAdapter(radioListAdapter);

        //2
        subjects = getResources().getStringArray(R.array.purchase_unavailable_options);
        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), getContext());
        paymentItemUnavailable.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentItemUnavailable.setAdapter(radioListAdapter);

        //3
        subjects = getResources().getStringArray(R.array.delivery_time_options);
        radioListAdapter = new RadioListAdapter(new ArrayList<CharSequence>(Arrays.asList(subjects)), getContext());
        deliveryDate.setLayoutManager(new LinearLayoutManager(getContext()));
        deliveryDate.setAdapter(radioListAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);

        if ((getFragmentManager() != null) && isVisible) {
            address = User.getInstance().getSelectedAddress();
            //textAddress.setText(address.getAdresse());
        }
    }
}
