package com.example.vscandroid.firebaseapp.activities;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.domain.usecases.SignUpUsecase;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;

import javax.inject.Inject;

import butterknife.BindView;

public class SignUpActivity extends BaseActivity implements SignUpUsecase.ViewListener {

    @Inject SignUpUsecase usecase;

    @BindView(R.id.username) EditText inputUsername;
    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.password) EditText inputPassword;
    @BindView(R.id.conf_password) EditText inputConfPassword;
    @BindView(R.id.phone) EditText inputPhone;
    @BindView(R.id.sign_in_button) MaterialButton btnSignIn;
    @BindView(R.id.sign_up_button) MaterialButton btnSignUp;
    @BindView(R.id.btn_reset_password) MaterialButton btnResetPassword;
    @BindView(R.id.progressBar) ProgressBar progressBar;

//    private FirebaseAuth auth;


    @Override
    protected void onViewCreated() {
        usecase.setViewListener(this);
//        auth = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputUsername = findViewById(R.id.username);
        inputEmail = findViewById(R.id.email);
        inputPhone = findViewById(R.id.phone);
        inputPassword = findViewById(R.id.password);
        inputConfPassword = findViewById(R.id.conf_password);
        progressBar = findViewById(R.id.progressBar);
        btnResetPassword = findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = inputUsername.getText().toString().trim();
                final String email = inputEmail.getText().toString().trim();
                final String phoneNumber = inputPhone.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                String confPass = inputConfPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confPass)) {
                    Toast.makeText(getApplicationContext(), "Enter confirmed pass!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confPass.equals(password)) {
                    Toast.makeText(getApplicationContext(), "Passwords not confirmed!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                usecase.registerUser(email, password, username, phoneNumber);

            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_signup;
    }

    @Override
    protected void doInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void showRegistrationFailed() {

    }

    @Override
    public void showRegistrationSuccess() {
        String username = inputUsername.getText().toString().trim();
        String userPhone = inputPhone.getText().toString().trim();
        String userEmail = inputEmail.getText().toString().trim();
        usecase.addUser(username, userPhone, userEmail);
    }

    @Override
    public void showMainScreen(String username) {
        Toast.makeText(this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}