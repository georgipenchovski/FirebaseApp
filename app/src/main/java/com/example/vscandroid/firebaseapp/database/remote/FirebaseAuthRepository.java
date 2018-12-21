package com.example.vscandroid.firebaseapp.database.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository implements UserAuthRepository {
    private static final String TAG = "FirebaseAuthRepository";

    private static FirebaseAuthRepository instance;
    private FirebaseAuth authenticator;
    private FirebaseAuth.AuthStateListener authStateListener;
    private SignInListener signInListener;
    private SignUpListener signUpListener;
    private ResetPasswordListener resetPasswordListener;
    private CheckUserListener checkUserListener;

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
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "onComplete: login");
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

        authenticator.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: login");
                        signInListener.onSignInError();
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
        checkForSignedUser(signInListener);
    }

    public void checkForSignedUser(final SignInListener signInListener) {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "User signed-in or registered and signed in automatically:" + user.getUid());
                } else if (signInListener != null) {
                    signInListener.onSignInSuccessful(authenticator.getCurrentUser().getUid());
                }
            }
        };
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

    @Override
    public void resetPassword(String email) {
        authenticator.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            resetPasswordListener.onResetSuccess();
                        } else {
                            resetPasswordListener.onResetError();
                        }
                    }
                });
    }

    public void addResetPasswordListener(ResetPasswordListener resetPasswordListener) {
        this.resetPasswordListener = resetPasswordListener;
//        authenticator.addAuthStateListener(authStateListener);
    }

    @Override
    public void addCheckUserListener(CheckUserListener checkUserListener) {
        this.checkUserListener = checkUserListener;
    }

    @Override
    public void checkForLoggedUser() {
        if (authenticator.getCurrentUser() != null) {
            checkUserListener.userLoggedSuccessful();
        } else {
            checkUserListener.userLoggedError();
        }
    }
}