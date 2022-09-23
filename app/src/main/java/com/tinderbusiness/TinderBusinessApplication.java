package com.tinderbusiness;

import android.content.ContextWrapper;

import androidx.multidex.MultiDexApplication;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.pixplicity.easyprefs.library.Prefs;

public class TinderBusinessApplication extends MultiDexApplication {

    public static final String TAG = TinderBusinessApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(this);

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

    }

}

