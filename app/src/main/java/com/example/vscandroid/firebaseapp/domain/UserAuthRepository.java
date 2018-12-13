package com.example.vscandroid.firebaseapp.domain;

import com.google.firebase.auth.FirebaseAuth;

public interface UserAuthRepository {

    void registerUser(String email, String password, String name, int result);

    void loginUserByEmail(String email, String password);

    void loginUserAnonymously();

    void linkUserWithCredential(String email, String password, String name, int result);

    void addSignInListener(SignInListener signInListener);

    void removeSignInListener();

    void getCurrentUser(SignOutListener signOutListener);

    void signOut();

    void insertUserRatingRecord(String name, int score);

    interface SignOutListener {
        void onUserInstanceReceived(FirebaseAuth mAuth);
    }

    interface SignInListener {
        void onSignInSuccessful(String userId);

        void onSignInError();

        void onRegisterError();

        void showInvalidPasswordError();

        void showNoInternetConnectionError();

        void showWeakPasswordError();

        void onAnonymousSignIn();
    }

}
