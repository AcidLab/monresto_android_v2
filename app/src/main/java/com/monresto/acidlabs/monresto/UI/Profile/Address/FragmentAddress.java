package com.monresto.acidlabs.monresto.UI.Profile.Address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAddress extends Fragment {

    private boolean _hasLoadedOnce= false; // your boolean field

    @BindView(R.id.listview_address)
    ListView listview_address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_address, container, false);
        ButterKnife.bind(this, root);


        return root;
    }


    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                System.out.println("SPECIAL DEBUG: " + User.getInstance().getAddresses().size() + " Addresses, sending to the listview");
                AddressAdapter addressAdapter = new AddressAdapter(User.getInstance().getAddresses());
                listview_address.setAdapter(addressAdapter);

                _hasLoadedOnce = true;
            }
        }
    }

}
