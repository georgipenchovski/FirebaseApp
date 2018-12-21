package com.example.vscandroid.firebaseapp.fragments;


import com.example.vscandroid.firebaseapp.R;

public class CompletedTasksFragment extends BaseFragment {

    private static final String TAG = "CompletedTasksFragment";

    public static CompletedTasksFragment newInstance() {
        return new CompletedTasksFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_completed_tasks;
    }

    @Override
    protected void onViewCreated() {
    }

}
