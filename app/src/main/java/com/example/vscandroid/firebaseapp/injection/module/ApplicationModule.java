package com.example.vscandroid.firebaseapp.injection.module;

import android.app.Application;
import android.content.Context;

import com.example.vscandroid.firebaseapp.database.remote.FirebaseDataRepository;
import com.example.vscandroid.firebaseapp.database.local.DBUtils;
import com.example.vscandroid.firebaseapp.injection.ApplicationContext;
import com.example.vscandroid.firebaseapp.utils.PreferencesUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    DBUtils provideDBUtils() {
        return new DBUtils(mApplication);
    }


    @Provides
    @Singleton
    PreferencesUtils providePreferencesUtils() {
        return new PreferencesUtils(mApplication);
    }

    @Provides
    @Singleton
    FirebaseDataRepository provideFireBase() {
        return new FirebaseDataRepository();
    }
}
