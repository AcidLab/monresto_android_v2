package com.monresto.acidlabs.monresto.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Homepage.HomepageActivity;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate (Bundle bundle) {
        super.onCreate(bundle);
    setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,HomepageActivity.class);
                startActivity(i);
                finish();
            }
        },3000);



    }
}
