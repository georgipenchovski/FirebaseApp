package com.example.vscandroid.firebaseapp.domain.usecases;

import com.example.vscandroid.firebaseapp.database.remote.FirebaseAuthRepository;
import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;

import javax.inject.Inject;

public class MainUsecase {

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
        viewListener.logoutSuccess();
    }

    public interface ViewListener {
        void logoutSuccess();
    }
}
