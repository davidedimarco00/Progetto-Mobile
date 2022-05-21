package com.app.mypresence.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.mypresence.R;
import com.app.mypresence.presenter.Presenter;
import com.app.mypresence.presenter.SplashPresenterInterface;
import com.app.mypresence.presenter.UserActivityPresenter;
import com.app.mypresence.presenter.UserActivityPresenterInterface;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.presenter.hideActionBar();
        this.bottomNavigationView =  (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        this.bottomNavigationView.setSelectedItemId(R.id.btn_profile);

        /**TODO*/
        Intent intent = getIntent();
        System.out.println(intent.getBundleExtra("userInfo").get("name").toString());


        if (savedInstanceState == null) {
            if (true) { //QUI È DA VERIFICARE SE L'ACCESSO È STATO FATTO CON ADMIN O USER
                this.presenter.showUserFragment(UserFragment.class);
            } else {
                this.presenter.showAdminFragment(AdminFragment.class);
            }
        }

        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_statics:
                        presenter.showStatisticFragment(StatisticsFragment.class);
                        break;
                    case R.id.btn_profile:
                        presenter.showUserFragment(UserFragment.class);
                        break;
                    case R.id.btn_settings:
                        presenter.showSettingsFragment(SettingsFragment.class);
                        break;
                }
                return true;
            }
        });
    }
}