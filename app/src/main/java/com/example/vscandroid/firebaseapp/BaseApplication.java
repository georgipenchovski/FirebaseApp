package com.example.vscandroid.firebaseapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.vscandroid.firebaseapp.injection.component.ApplicationComponent;
import com.example.vscandroid.firebaseapp.injection.component.DaggerApplicationComponent;
import com.example.vscandroid.firebaseapp.injection.module.ApplicationModule;

public class BaseApplication extends Application {

    ApplicationComponent mApplicationComponent;
    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        MultiDex.install(this);
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();

    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
