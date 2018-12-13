package com.example.vscandroid.firebaseapp.fragments;

import android.os.Bundle;import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, view);
        onViewCreated();
        return view;
    }

    protected abstract int getLayoutRes();

    protected abstract void onViewCreated();
}

