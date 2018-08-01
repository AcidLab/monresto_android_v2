package com.monresto.acidlabs.monresto.UI.Profile.Address;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAddress extends Fragment implements UserAsyncResponse {

    private boolean _hasLoadedOnce = false; // your boolean field
    private UserService userService;
    @BindView(R.id.listview_address)
    ListView listview_address;
    Context context;
    @BindView(R.id.status_address)
    ConstraintLayout status_address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_address, container, false);
        ButterKnife.bind(this, root);

        userService = new UserService(context);

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
                        AddressAdapter addressAdapter = new AddressAdapter(User.getInstance().getAddresses());
                        listview_address.setAdapter(addressAdapter);
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
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.fragment_unavailable, status_address, true);

            status_address.removeAllViews();
            status_address.addView(layout);

            TextView unavailable_msg = (TextView)status_address.getViewById(R.id.unavailable_msg);
            unavailable_msg.setText("Aucune adresse trouvée");

            return;
        }

        AddressAdapter addressAdapter = new AddressAdapter(addresses);
        listview_address.setAdapter(addressAdapter);

        status_address.setVisibility(View.INVISIBLE);
        listview_address.setVisibility(View.VISIBLE);
    }

    public FragmentAddress setContext(Context context) {
        this.context = context;
        return this;
    }
}
