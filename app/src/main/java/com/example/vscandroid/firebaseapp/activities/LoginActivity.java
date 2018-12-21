package com.example.vscandroid.firebaseapp.activities;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.domain.usecases.LoginUsecase;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements LoginUsecase.ViewListener {

    private static final String TAG = "LoginActivity";
    @Inject LoginUsecase usecase;

    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.password) EditText inputPassword;
    @BindView(R.id.btn_signup) MaterialButton btnSignup;
    @BindView(R.id.btn_login) MaterialButton btnLogin;
    @BindView(R.id.btn_reset_password) MaterialButton btnReset;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onViewCreated() {
        usecase.setViewListener(this);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            showLoginSuccess();
        }
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                usecase.signIn(email, password);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void doInject(ActivityComponent activityComponent) {
        activityComponent.
                inject(this);
    }

    @Override
    public void showInvalidPasswordLengthError() {
        inputPassword.setError(getString(R.string.minimum_password));
    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoginSuccess() {
        startActivity(MainActivity.getIntent(this));
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
