package com.example.vscandroid.firebaseapp.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.databinding.ActivityLoginBinding;
import com.example.vscandroid.firebaseapp.domain.usecases.LoginUsecase;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginUsecase.ViewListener {

    @Inject LoginUsecase usecase;

    @Override
    protected void onViewCreated() {
        usecase.setViewListener(this);
        usecase.checkForLoggedUser();

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString();
                final String password = binding.password.getText().toString();

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
        binding.password.setError(getString(R.string.minimum_password));
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
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }
}
