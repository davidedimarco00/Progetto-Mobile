package com.app.mypresence.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.app.mypresence.R;
import com.app.mypresence.presenter.UserActivityPresenter;
import com.app.mypresence.presenter.UserActivityPresenterInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * USER ACTIVITY IS FOR ADMIN(datore di lavoro) and user (dipendenti) at the same time. so we should use:
 * 1 - fragment_user -> datore di lavoro
 * 2 - fragment_admin -> dipendenti
 */
public class UserActivity extends AppCompatActivity {

    private final UserActivityPresenterInterface presenter  = new UserActivityPresenter(this);
    private BottomNavigationView bottomNavigationView;
    UserFragment userFragment = new UserFragment();
    StatisticsFragment statisticsFragment;
    SettingsFragment settingsFragment = new SettingsFragment() ;
    AdminFragment adminFragment = new AdminFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.presenter.hideActionBar();
        this.bottomNavigationView =  (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        this.bottomNavigationView.setSelectedItemId(R.id.btn_profile);
        Bundle infoUser = getIntent().getBundleExtra("userInfo");
        String username = infoUser.getString("username");
        String password = infoUser.getString("password");

        this.statisticsFragment = StatisticsFragment.newInstance(username, password);
        /**TODO*/
        Intent intent = getIntent();
        boolean isAdmin = intent.getBundleExtra("userInfo").getBoolean("isAdmin");
        if (savedInstanceState == null) {
            if (!isAdmin) {
                Log.e("bundle", intent.getExtras().toString());

                userFragment.setArguments(intent.getExtras());

                this.presenter.showUserFragment(userFragment);
            } else {
                this.presenter.showAdminFragment(adminFragment);
            }
        }

        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_statics:
                        presenter.showStatisticFragment(statisticsFragment);
                        break;
                    case R.id.btn_profile:
                        presenter.showUserFragment(userFragment);
                        break;
                    case R.id.btn_settings:
                        presenter.showSettingsFragment(settingsFragment);
                        break;
                }
                return true;
            }
        });
    }
}