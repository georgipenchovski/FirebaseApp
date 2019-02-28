package com.example.vscandroid.firebaseapp.fragments;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.vscandroid.firebaseapp.R;


public class TaskFragment extends BaseFragment {

    private static final String TAG = "TaskFragment";
    FloatingActionButton openCameraBtn;


    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_task;
    }

    @Override
    protected void onViewCreated() {

        openCameraBtn = getLayoutView().findViewById(R.id.open_camera_btn);

        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
             startActivity(intent);
            }
        });
    }
}
