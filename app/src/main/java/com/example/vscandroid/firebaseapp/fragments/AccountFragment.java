package com.example.vscandroid.firebaseapp.fragments;

import android.support.design.button.MaterialButton;

import com.example.vscandroid.firebaseapp.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountFragment extends BaseFragment {

    @BindView(R.id.sign_out_btn)MaterialButton signOutBtn;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    protected void onViewCreated() {

    }

    @OnClick(R.id.sign_out_btn)
    public void signOutClicked() {

    }

}