package com.example.vscandroid.firebaseapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.database.model.User;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends BaseActivity {

    private EditText inputUsername, inputEmail, inputPhone, inputPassword, inputConfPassword;
    private MaterialButton btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    DatabaseReference databaseUsers;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//       super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        auth = FirebaseAuth.getInstance();
//        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
//
//        btnSignIn = findViewById(R.id.sign_in_button);
//        btnSignUp = findViewById(R.id.sign_up_button);
//        inputUsername = findViewById(R.id.username);
//        inputEmail = findViewById(R.id.email);
//        inputPhone = findViewById(R.id.phone);
//        inputPassword = findViewById(R.id.password);
//        inputConfPassword = findViewById(R.id.conf_password);
//        progressBar = findViewById(R.id.progressBar);
//        btnResetPassword = findViewById(R.id.btn_reset_password);
//
//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
//            }
//        });
//
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String username = inputUsername.getText().toString().trim();
//                String email = inputEmail.getText().toString().trim();
//                String phoneNumber = inputPhone.getText().toString().trim();
//                String password = inputPassword.getText().toString().trim();
//                String confPass = inputConfPassword.getText().toString().trim();
//                if (TextUtils.isEmpty(username)) {
//                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(phoneNumber)) {
//                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(confPass)) {
//                    Toast.makeText(getApplicationContext(), "Enter confirmed pass!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!confPass.equals(password)) {
//                    Toast.makeText(getApplicationContext(), "Passwords not confirmed!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//                auth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
//                                if (!task.isSuccessful()) {
//                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
//                                            Toast.LENGTH_SHORT).show();
//                                } else {
//                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                                    addUser();
//                                    finish();
//                                }
//                            }
//                        });
//
//            }
//        });
//    }

    @Override
    protected void onViewCreated() {

    }

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected void doInject(ActivityComponent activityComponent) {

    }

    private void addUser() {
        String username = inputUsername.getText().toString().trim();
        String userPhone = inputPhone.getText().toString().trim();
        String userEmail = inputEmail.getText().toString().trim();
        if (!TextUtils.isEmpty(username)) {
            String id = databaseUsers.push().getKey();
            User user = new User(id, username, userPhone, userEmail);
            databaseUsers.child(id).setValue(user);
            Toast.makeText(this, "Hello " + username + "!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}