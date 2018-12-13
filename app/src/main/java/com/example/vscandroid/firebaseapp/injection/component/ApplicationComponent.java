package com.example.vscandroid.firebaseapp.injection.component;

import android.app.Application;
import android.content.Context;

import com.example.vscandroid.firebaseapp.injection.ApplicationContext;
import com.example.vscandroid.firebaseapp.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();
}
