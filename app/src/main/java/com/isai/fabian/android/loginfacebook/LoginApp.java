package com.isai.fabian.android.loginfacebook;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

/**
 * Created by fabianisai on 7/8/16.
 */

public class LoginApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public void logout() {
        LoginManager.getInstance().logOut();  //cerrar session de face
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
