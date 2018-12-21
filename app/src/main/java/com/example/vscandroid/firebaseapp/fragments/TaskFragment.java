package com.example.vscandroid.firebaseapp.fragments;

import com.example.vscandroid.firebaseapp.R;


public class TaskFragment extends BaseFragment {

    private static final String TAG = "TaskFragment";

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_task;
    }

    @Override
    protected void onViewCreated() {

    }
}
