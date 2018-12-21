package com.example.vscandroid.firebaseapp.domain.usecases;

import com.example.vscandroid.firebaseapp.database.remote.FirebaseAuthRepository;
import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;

import javax.inject.Inject;

public class ResetPasswordUsecase {


    private UserAuthRepository userAuthRepository;
    private ViewListener viewListener;

    @Inject
    public ResetPasswordUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void resetPassword(String email) {
        viewListener.showProgress();
        userAuthRepository.addResetPasswordListener(new UserAuthRepository.ResetPasswordListener() {
            @Override
            public void onResetSuccess() {
                viewListener.showResetPasswordSuccess();
                viewListener.hideProgress();
            }

            @Override
            public void onResetError() {
                viewListener.showResetPasswordError();
                viewListener.showProgress();
            }
        });
        userAuthRepository.resetPassword(email);


    }

    public interface ViewListener {
        void showResetPasswordSuccess();

        void showResetPasswordError();

        void showProgress();

        void hideProgress();
    }

}