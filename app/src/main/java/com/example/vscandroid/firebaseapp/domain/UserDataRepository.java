package com.example.vscandroid.firebaseapp.domain;

public interface UserDataRepository {

    void addUser(String username, String phone, String email);

    void addUserDataListener(UserDataListener storeUserListener);

    interface UserDataListener {
        void saveSuccess();
    }
}

