package com.example.vscandroid.firebaseapp.fragments;

import android.support.design.button.MaterialButton;
import android.util.Log;
import android.view.View;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.activities.MainActivity;

public class AccountFragment extends BaseFragment{

    private static final String TAG = "AccountFragment";
    private MainActivity activity;

    MaterialButton signOutBtn;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    protected void onViewCreated() {
        Log.d(TAG, "AccountFragment onViewCreated: exec");
        activity = (MainActivity) getActivity();

        signOutBtn = getLayoutView().findViewById(R.id.sign_out_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.signOut();
            }
        });
    }
}
