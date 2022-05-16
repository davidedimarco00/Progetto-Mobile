package com.app.mypresence.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.mypresence.R;
import com.app.mypresence.presenter.Presenter;
import com.app.mypresence.presenter.SplashPresenterInterface;
import com.app.mypresence.presenter.UserActivityPresenter;
import com.app.mypresence.presenter.UserActivityPresenterInterface;

/**
 * USER ACTIVITY IS FOR ADMIN(datore di lavoro) and user (dipendenti) at the same time. so we should use:
 * 1 - fragment_user -> datore di lavoro
 * 2 - fragment_admin -> dipendenti
 */
public class UserActivity extends AppCompatActivity {

    UserActivityPresenterInterface presenter  = new UserActivityPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.presenter.hideActionBar();

        if (savedInstanceState == null) {
            if (true) { //QUI È DA VERIFICARE SE L'ACCESSO È STATO FATTO CON ADMIN O USER
                this.presenter.showUserFragment(UserFragment.class);
            } else {
                this.presenter.showAdminFragment(AdminFragment.class);
            }
        }
    }
}