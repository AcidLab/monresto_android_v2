package com.monresto.acidlabs.monresto;

import com.onesignal.OneSignal;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }
}
