package com.example.vscandroid.firebaseapp.database.remote;

import com.example.vscandroid.firebaseapp.database.model.User;
import com.example.vscandroid.firebaseapp.domain.UserDataRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDataRepository implements UserDataRepository {

    DatabaseReference databaseUsers;
    UserDataListener userDataListener;

    public FirebaseDataRepository() {
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void addUser(String username, String phone, String email) {
        String id = databaseUsers.push().getKey();
        User user = new User(id, username, phone, email);
        databaseUsers.child(id).setValue(user);

        userDataListener.saveSuccess();
    }

    @Override
    public void addUserDataListener(UserDataListener userDataListener) {
        this.userDataListener = userDataListener;
    }
}
