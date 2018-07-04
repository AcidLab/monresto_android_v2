package com.monresto.acidlabs.monresto.Service.User;

import com.monresto.acidlabs.monresto.Model.User;

public interface UserAsyncResponse {
    void onUserLogin(User user);
    void onUserDetailsReceived(User user);
}
