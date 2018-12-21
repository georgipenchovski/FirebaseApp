package com.example.vscandroid.firebaseapp.domain.usecases;

import android.util.Log;

import com.example.vscandroid.firebaseapp.database.remote.FirebaseAuthRepository;
import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class MainUsecase {

    private static final String TAG = "MainUsecase";

    private UserAuthRepository userAuthRepository;
    private ViewListener viewListener;

    @Inject
    public MainUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void signOut() {
        userAuthRepository.signOut();
        Log.e(TAG, String.valueOf("signOut: " + FirebaseAuth.getInstance().getCurrentUser() == null));
        viewListener.logoutSuccess();
    }

    public interface ViewListener {
        void logoutSuccess();
    }
}
