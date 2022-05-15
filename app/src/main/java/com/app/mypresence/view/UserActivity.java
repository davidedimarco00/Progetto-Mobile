package com.app.mypresence.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.mypresence.R;
import com.app.mypresence.presenter.Presenter;
import com.app.mypresence.presenter.UserActivityPresenter;

/**
 * USER ACTIVITY IS FOR ADMIN(datore di lavoro) and user (dipendenti) at the same time. so we should use:
 * 1 - fragment_user -> datore di lavoro
 * 2 - fragment_admin -> dipendenti
 */
public class UserActivity extends AppCompatActivity {

    Presenter presenter  = new UserActivityPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (true) { //se è un dipendente
            setContentView(R.layout.activity_user);  //set user fragment

        }else{ //se è admin
            //set admin fragment
        }

    }
}