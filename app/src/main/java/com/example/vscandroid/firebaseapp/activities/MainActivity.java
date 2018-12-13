package com.example.vscandroid.firebaseapp.activities;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.fragments.AccountFragment;
import com.example.vscandroid.firebaseapp.fragments.BaseFragment;
import com.example.vscandroid.firebaseapp.fragments.CompletedTasksFragment;
import com.example.vscandroid.firebaseapp.fragments.TaskFragment;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_nav_bar)
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onViewCreated() {
        frameLayout = findViewById(R.id.main_frame);
        setupViews();
    }

    private void setupViews() {
//       bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.main_nav_bar);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void doInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.nav_completed_tasks:
                    //   bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                    showCompletedTasksFragment();
                    return true;

                case R.id.nav_tasks:
                    //     bottomNavigationView.setItemBackgroundResource(R.color.colorAccent);
                    showTaskFragment();
                    return true;

                case R.id.nav_account:
                    //       bottomNavigationView.setItemBackgroundResource(R.color.colorPrimaryDark);
                    showAccountFragment();
                    return true;
            }

            return false;
        }
    };


    private void showCompletedTasksFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, CompletedTasksFragment.newInstance())
                .commit();
    }

    private void showTaskFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, TaskFragment.newInstance())
                .commit();
    }

    private void showAccountFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, AccountFragment.newInstance())
                .commit();
    }
}
