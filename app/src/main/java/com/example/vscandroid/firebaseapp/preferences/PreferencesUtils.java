package com.example.vscandroid.firebaseapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vscandroid.firebaseapp.injection.ApplicationContext;

import javax.inject.Inject;

public class PreferencesUtils {

    //TODO change app name
    private static final String PREF_FILE_NAME = "app_name";
    private static SharedPreferences sPref;

    @Inject
    PreferencesUtils(@ApplicationContext Context context) {
        sPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }
}
