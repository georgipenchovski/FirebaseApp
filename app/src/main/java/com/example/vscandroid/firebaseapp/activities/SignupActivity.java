package com.example.vscandroid.firebaseapp.activities;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.databinding.ActivitySignupBinding;
import com.example.vscandroid.firebaseapp.domain.usecases.SignUpUsecase;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;

public class SignUpActivity extends BaseActivity<ActivitySignupBinding> implements SignUpUsecase.ViewListener {

    @Inject SignUpUsecase usecase;

    @Override
    protected void onViewCreated() {
        usecase.setViewListener(this);

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
        });

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = binding.username.getText().toString().trim();
                final String email = binding.email.getText().toString().trim();
                final String phoneNumber = binding.phone.getText().toString().trim();
                final String password = binding.password.getText().toString().trim();
                String confPass = binding.confPassword.getText().toString().trim();
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
        binding.progressBar.setVisibility(View.GONE);
    }


    @Override
    public void showRegistrationFailed() {

    }

    @Override
    public void showRegistrationSuccess() {
        String username = binding.username.getText().toString().trim();
        String userPhone = binding.phone.getText().toString().trim();
        String userEmail = binding.email.getText().toString().trim();
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
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }
}