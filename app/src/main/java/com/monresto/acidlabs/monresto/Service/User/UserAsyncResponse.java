package com.monresto.acidlabs.monresto.Service.User;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.User;

import java.util.ArrayList;

public interface UserAsyncResponse {
    void onUserLogin(User user);
    void onUserDetailsReceived(User user);
    void oncheckLoginDispoReceived(boolean isDispo);

    default void onHistoryReceived(){}
    default void onPendingReceived(){}
    default void onAddressListReceived(ArrayList<Address> addresses){}
}
