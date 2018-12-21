package com.example.vscandroid.firebaseapp.activities;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.databinding.ActivityResetPasswordBinding;
import com.example.vscandroid.firebaseapp.domain.usecases.ResetPasswordUsecase;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding> implements ResetPasswordUsecase.ViewListener {

    @Inject ResetPasswordUsecase usecase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usecase.setViewListener(this);



        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.email.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                usecase.resetPassword(email);
            }
        });
    }

    @Override
    protected void onViewCreated() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void doInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void showResetPasswordSuccess() {
        Toast.makeText(ResetPasswordActivity.this,
                "We have sent you instructions to reset your password!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResetPasswordError() {
        Toast.makeText(ResetPasswordActivity.this,
                "Failed to send reset email!",
                Toast.LENGTH_SHORT).show();
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