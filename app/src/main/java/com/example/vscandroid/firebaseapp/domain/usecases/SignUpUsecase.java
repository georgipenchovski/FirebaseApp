package com.example.vscandroid.firebaseapp.domain.usecases;

import com.example.vscandroid.firebaseapp.activities.SignUpActivity;
import com.example.vscandroid.firebaseapp.database.remote.FirebaseAuthRepository;
import com.example.vscandroid.firebaseapp.domain.UserAuthRepository;
import com.example.vscandroid.firebaseapp.domain.UserDataRepository;

import javax.inject.Inject;

public class SignUpUsecase {

    private UserAuthRepository userAuthRepository;
    private UserDataRepository userDataRepository;
    private ViewListener viewListener;
    private String username;

    @Inject
    SignUpUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public void setViewListener(SignUpActivity viewListener) {
        this.viewListener = viewListener;
    }

    public void registerUser(String email, String password, String username, String phoneNumber) {
        viewListener.showProgress();
        userAuthRepository.addSignUpListener(getSignUpListener());
        userAuthRepository.registerUser(email, password, username, phoneNumber);

        this.username = username;
        userDataRepository.addUserDataListener(getUserDataListener());
    }

    private UserDataRepository.UserDataListener getUserDataListener() {
        return new UserDataRepository.UserDataListener() {
            @Override
            public void saveSuccess() {
                viewListener.showMainScreen(username);
            }
        };
    }


    private UserAuthRepository.SignUpListener getSignUpListener() {
        return new UserAuthRepository.SignUpListener() {
            @Override
            public void onSignUpSuccessful(String userId) {
                viewListener.hideProgress();
                viewListener.showRegistrationSuccess();
            }

            @Override
            public void onSignUpError() {
                viewListener.hideProgress();
                viewListener.showRegistrationFailed();
            }
        };
    }

    public void addUser(String username, String userPhone, String userEmail) {
        userDataRepository.addUser(username, userPhone, userEmail);
    }


    public interface ViewListener {

        void showRegistrationFailed();

        void showRegistrationSuccess();

        void showProgress();

        void hideProgress();

        void showMainScreen(String username);
    }
}
