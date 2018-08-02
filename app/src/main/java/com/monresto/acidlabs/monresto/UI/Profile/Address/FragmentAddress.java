package com.monresto.acidlabs.monresto.UI.Profile.Address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAddress extends Fragment implements UserAsyncResponse, SwipeRefreshLayout.OnRefreshListener {

    private boolean _hasLoadedOnce = false; // your boolean field
    private UserService userService;
    @BindView(R.id.recyclerview_address)
    RecyclerView recyclerview_address;
    @BindView(R.id.address_pull_to_refresh)
    TextView address_pull_to_refresh;
    @BindView(R.id.status_address)
    ConstraintLayout status_address;
    @BindView(R.id.swiper_address)
    SwipeRefreshLayout swiper_address;
    @BindView(R.id.buttonNewAddress)
    Button buttonNewAddress;

    AddressRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_address, container, false);
        ButterKnife.bind(this, root);

        swiper_address.setOnRefreshListener(this);

        userService = new UserService(getContext());
        adapter = new AddressRecyclerViewAdapter(getContext());
        recyclerview_address.setLayoutManager(new LinearLayoutManager(getActivity()));

        buttonNewAddress.setOnClickListener(e -> {
            Intent intent = new Intent(this.getContext(), NewAddressActivity.class);
            startActivity(intent);
        });
        return root;
    }


    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (this.isVisible()) {
                if (User.getInstance().getAddresses() != null) {
                    // we check that the fragment is becoming visible
                    if (isFragmentVisible_ && !_hasLoadedOnce) {
                        System.out.println("SPECIAL DEBUG: " + User.getInstance().getAddresses().size() + " Addresses, sending to the listview");
                        adapter.setAddresses(User.getInstance().getAddresses());
                        recyclerview_address.setAdapter(adapter);

                        status_address.setVisibility(View.INVISIBLE);
                        swiper_address.setVisibility(View.VISIBLE);
                        address_pull_to_refresh.setVisibility(View.VISIBLE);


                        _hasLoadedOnce = true;
                    }
                }
                else {
                    System.out.println("GET ADDRESS CALLED");
                    userService.getAddress(User.getInstance().getId());
                }
        }

    }

    public void updateList(ArrayList<Address> addresses) {
        System.out.println("SPECIAL DEBUG: " + addresses.size() + " Addresses, choosing what to do...");
        if (addresses.size() == 0) {
            Utilities.statusChangerLoading(getContext(),"Aucune adresse trouvée",status_address,swiper_address);

            return;
        }

        adapter.setAddresses(addresses);
        recyclerview_address.setAdapter(adapter);

        status_address.setVisibility(View.INVISIBLE);
        swiper_address.setVisibility(View.VISIBLE);
        address_pull_to_refresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        userService.getAddress(User.getInstance().getId());
        swiper_address.setRefreshing(false);
    }
}
