package com.example.vscandroid.firebaseapp.database.remote;

import android.support.annotation.NonNull;

import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthRepository implements UserAuthRepository {
    private static final String TAG = "FirebaseAuthRepository";

    private static FirebaseAuthRepository instance;
    private FirebaseAuth authenticator;
    private FirebaseAuth.AuthStateListener authStateListener;
    private SignInListener signInListener;
    private SignUpListener signUpListener;

    @Override
    public void registerUser(final String email, final String password, final String username, final String phoneNumber) {
        authenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            signUpListener.onSignUpError();
                        } else {
                            signUpListener.onSignUpSuccessful(authenticator.getCurrentUser().getUid());
                        }
                    }
                });
    }

    @Override
    public void loginUserByEmail(String email, final String password) {
        authenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                signInListener.showInvalidPasswordError();
                            } else {
                                signInListener.onSignInError();
                            }
                        } else {
                            signInListener.onSignInSuccessful(
                                    authenticator.getCurrentUser().getUid());
                        }
                    }
                });

    }


    public static FirebaseAuthRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthRepository();
        }
        return instance;
    }


    private FirebaseAuthRepository() {
        setupFireBaseAuthentication();
    }

    private void setupFireBaseAuthentication() {
        authenticator = FirebaseAuth.getInstance();

    }


    public void addSignInListener(SignInListener signInListener) {
        this.signInListener = signInListener;
        authenticator.addAuthStateListener(authStateListener);
    }

    @Override
    public void addSignUpListener(SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
        authenticator.addAuthStateListener(authStateListener);

    }

    public void removeSignInListener() {
        if (authStateListener != null) {
            authenticator.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void getCurrentUser(SignOutListener listener) {
        listener.onUserInstanceReceived(authenticator);
    }

    @Override
    public void signOut() {
        authenticator.signOut();
        authenticator = FirebaseAuth.getInstance();
    }
}