package com.carassist.carassist.activities;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by user on 3/17/2016.
 * This is the entry point of the application
 */
public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //initializes the Firebase Database in the application's context
        Firebase.setAndroidContext(this);
    }
}
