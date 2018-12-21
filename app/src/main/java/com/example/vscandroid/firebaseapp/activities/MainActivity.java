package com.example.vscandroid.firebaseapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.vscandroid.firebaseapp.R;
import com.example.vscandroid.firebaseapp.databinding.ActivityMainBinding;
import com.example.vscandroid.firebaseapp.domain.usecases.MainUsecase;
import com.example.vscandroid.firebaseapp.fragments.AccountFragment;
import com.example.vscandroid.firebaseapp.fragments.CompletedTasksFragment;
import com.example.vscandroid.firebaseapp.fragments.TaskFragment;
import com.example.vscandroid.firebaseapp.injection.component.ActivityComponent;

import javax.inject.Inject;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements MainUsecase.ViewListener {

    private static final String TAG = "MainActivity";

    @Inject MainUsecase usecase;

    @Override
    protected void onViewCreated() {
        binding.mainNavBar.setOnNavigationItemSelectedListener(
                onNavigationItemSelectedListener);
        usecase.setViewListener(this);
        binding.mainNavBar.setSelectedItemId(R.id.nav_account);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void doInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(MainActivity.getIntent(this));
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
                .replace(R.id.main_frame, CompletedTasksFragment.newInstance())
                .commit();
    }

    private void showTaskFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, TaskFragment.newInstance())
                .commit();
    }

    private void showAccountFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, AccountFragment.newInstance())
                .commit();
    }

    @Override
    public void logoutSuccess() {
        Log.e(TAG, "logoutSuccess");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void signOut() {
        usecase.signOut();
    }
}
