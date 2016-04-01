package com.varadr.hackernews;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by varad on 28/3/16.
 */
public class HackerNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * Initialize Firebase
         */
        Firebase.setAndroidContext(this);
    }
}
