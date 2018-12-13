package com.example.vscandroid.firebaseapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vscandroid.firebaseapp.injection.ApplicationContext;

import javax.inject.Inject;

public class PreferencesUtils {

    private static final String PREF_FILE_NAME = "safe_net_prefs";
    private static SharedPreferences sPref;

    @Inject
    public PreferencesUtils(@ApplicationContext Context context) {
        sPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }
}
