package com.example.vscandroid.firebaseapp.database.remote;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository implements UserAuthRepository {

    private static final String TAG = "FirebaseAuthRepository";
    private static final String NAME = "name";
    private static final String PLAYED_DATE = "playedDate";
    private static final String SCORE = "score";
    private static final String USER_ID = "userId";

    private static FirebaseAuthRepository instance;
    private FirebaseAuth authenticator;
    private FirebaseAuth.AuthStateListener authStateListener;
    private SignInListener signInListener;

    @Override
    public void registerUser(final String email, final String password, final String name, final int result) {
        authenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful() && signInListener != null) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                loginUserByEmail(email, password);
                            } catch (FirebaseAuthWeakPasswordException e) {
                                signInListener.showWeakPasswordError();
                            } catch (FirebaseNetworkException e) {
                                signInListener.showNoInternetConnectionError();
                            } catch (Exception e) {
                                signInListener.onRegisterError();
                            }
                        } else {
                            insertUserRatingRecord(authenticator.getCurrentUser().getUid(),
                                    name, result);
                            signInListener.onSignInSuccessful(authenticator.getCurrentUser().getUid());
                        }
                    }
                });
    }

    private void insertUserRatingRecord(String uid, String name, int score) {

    }

    @Override
    public void loginUserByEmail(String email, String password) {
        authenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful() && signInListener != null) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                signInListener.showWeakPasswordError();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                signInListener.showInvalidPasswordError();
                            } catch (Exception e) {
                                signInListener.onSignInError();
                            }
                        } else if (signInListener != null){
                            signInListener.onSignInSuccessful(authenticator.getCurrentUser().getUid());
                        }
                    }
                });
    }

    @Override
    public void loginUserAnonymously() {
        authenticator.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful() && signInListener != null) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        signInListener.showWeakPasswordError();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        signInListener.showInvalidPasswordError();
                    } catch (Exception e) {
                        signInListener.onSignInError();
                    }
                }
            }
        });
    }

    @Override
    public void linkUserWithCredential(final String email, final String password, final String name, final int result) {
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        authenticator.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful() && signInListener != null) {

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                loginUserByEmail(email, password);
                            } catch (FirebaseAuthWeakPasswordException e) {
                                signInListener.showWeakPasswordError();
                            } catch (FirebaseNetworkException e) {
                                signInListener.showNoInternetConnectionError();
                            } catch (Exception e) {
                                signInListener.onSignInError();
                            }
                        } else {
                            insertUserRatingRecord(authenticator.getCurrentUser().getUid(),
                                    name, result);
                            signInListener.onSignInSuccessful(authenticator.getCurrentUser().getUid());
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

        checkForSignedUser(signInListener);
    }

    public void checkForSignedUser(final SignInListener signInListener) {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "User signed-in or registered and signed in automatically:" + user.getUid());
                    if (user.isAnonymous() && signInListener != null) {
                        signInListener.onAnonymousSignIn();
                    } else if (signInListener != null) {
                        signInListener.onSignInSuccessful(authenticator.getCurrentUser().getUid());
                    }
                } else {
                    Log.d(TAG, "User signed-out");
                }
            }
        };
    }

    public void addSignInListener(SignInListener signInListener) {
        this.signInListener = signInListener;
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
        setupFireBaseAuthentication();
    }

    @Override
    public void insertUserRatingRecord(String name, int score) {
        insertUserRatingRecord(authenticator.getCurrentUser().getUid(), name, score);
    }
}