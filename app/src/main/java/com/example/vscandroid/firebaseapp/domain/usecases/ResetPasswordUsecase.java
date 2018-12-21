package com.example.vscandroid.firebaseapp.domain.usecases;

public class ResetPasswordUsecase {

    private ViewListener viewListener;

    public ResetPasswordUsecase(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public interface ViewListener {

    }

}