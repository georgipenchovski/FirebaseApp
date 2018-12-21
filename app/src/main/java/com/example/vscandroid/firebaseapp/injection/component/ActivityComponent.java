package com.example.vscandroid.firebaseapp.injection.component;

import com.example.vscandroid.firebaseapp.activities.BaseActivity;
import com.example.vscandroid.firebaseapp.activities.LoginActivity;
import com.example.vscandroid.firebaseapp.activities.MainActivity;
import com.example.vscandroid.firebaseapp.activities.ResetPasswordActivity;
import com.example.vscandroid.firebaseapp.activities.SignUpActivity;
import com.example.vscandroid.firebaseapp.injection.PerActivity;
import com.example.vscandroid.firebaseapp.injection.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(ResetPasswordActivity resetPasswordActivity);

}
