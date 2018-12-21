package com.example.vscandroid.firebaseapp.domain.usecases;

import android.util.Log;

import com.example.vscandroid.firebaseapp.database.remote.FirebaseAuthRepository;
import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class LoginUsecase {

    private static final String TAG = "LoginUsecase";
    private UserAuthRepository userAuthRepository;
    private ViewListener viewListener;

    @Inject
    LoginUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void signIn(String email, final String password) {
        Log.e(TAG, "signIn: email: " + email);
        viewListener.showProgress();
        userAuthRepository.addSignInListener(getSignInListener());
        userAuthRepository.loginUserByEmail(email, password);
    }

    private UserAuthRepository.SignInListener getSignInListener() {
        Log.e(TAG, "getSignInListener: exec");
        return new UserAuthRepository.SignInListener() {
            @Override
            public void onSignInSuccessful(String userId) {
                Log.e(TAG, "onSignInSuccessful:");
                viewListener.hideProgress();
                viewListener.showLoginSuccess();
            }

            @Override
            public void onSignInError() {
                Log.e(TAG, "onSignInError: ");
                viewListener.hideProgress();
                viewListener.showLoginFailed();
            }

            @Override
            public void showInvalidPasswordError() {
                Log.e(TAG, "showInvalidPasswordError:");
                viewListener.hideProgress();
                viewListener.showInvalidPasswordLengthError();
            }
        };
    }

    public interface ViewListener {

        void showInvalidPasswordLengthError();

        void showLoginFailed();

        void showLoginSuccess();

        void showProgress();

        void hideProgress();
    }
}

