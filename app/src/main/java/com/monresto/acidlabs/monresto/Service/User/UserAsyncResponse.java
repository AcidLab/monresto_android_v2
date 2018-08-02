package com.monresto.acidlabs.monresto.Service.User;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Order;
import com.monresto.acidlabs.monresto.Model.User;

import java.util.ArrayList;

public interface UserAsyncResponse {
    default void onUserLogin(User user){}
    default void onUserDetailsReceived(User user){}
    default void oncheckLoginDispoReceived(boolean isDispo){}
    default void onHistoryReceived(ArrayList<Order> orders){}
    default void onPendingReceived(ArrayList<Order> orders){}
    default void onAddressListReceived(ArrayList<Address> addresses){}
    default void onAddressAddResponse(boolean success){}
}
