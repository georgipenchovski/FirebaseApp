package com.example.vscandroid.firebaseapp.domain;

import com.google.firebase.auth.FirebaseAuth;

public interface UserAuthRepository {

    void registerUser(String email, String password, String username, String phoneNumber);

    void loginUserByEmail(String email, String password);

    void addSignInListener(SignInListener signInListener);

    void addSignUpListener(SignUpListener signUpListener);

    void removeSignInListener();

    void getCurrentUser(SignOutListener signOutListener);

    void signOut();

    void resetPassword(String email);

    void addResetPasswordListener(ResetPasswordListener resetPasswordListener);

    void addCheckUserListener(CheckUserListener checkUserListener);

    void checkForLoggedUser();

    interface SignOutListener {
        void onUserInstanceReceived(FirebaseAuth mAuth);
    }

    interface SignInListener {
        void onSignInSuccessful(String userId);

        void onSignInError();

        void showInvalidPasswordError();
    }

    interface SignUpListener {
        void onSignUpSuccessful(String userId);

        void onSignUpError();
    }

    interface ResetPasswordListener {
        
        void onResetSuccess();
        
        void onResetError();
    }

    interface CheckUserListener {
        void userLoggedSuccessful();

        void userLoggedError();
    }
}
