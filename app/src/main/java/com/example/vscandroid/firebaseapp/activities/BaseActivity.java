package com.example.vscandroid.firebaseapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vscandroid.firebaseapp.BaseApplication;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;
import com.example.vscandroid.firebaseapp.injection.component.DaggerActivityComponent;
import com.example.vscandroid.firebaseapp.injection.module.ActivityModule;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getLayoutRes());
        doInject(getActivityComponent());
        ButterKnife.bind(this);
        onViewCreated();
    }

    protected abstract void onViewCreated();

    protected abstract int getLayoutRes();

    protected abstract void doInject(ActivityComponent activityComponent);

    private ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(BaseApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    protected BaseActivity getActivity() {
        return this;
    }
}



