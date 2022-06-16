package com.app.mypresence.view;
import com.app.mypresence.R;
import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.presenter.LoginPresenter;
import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.SplashPresenter;
import com.app.mypresence.presenter.SplashPresenterInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;


public class SplashScreen extends AppCompatActivity {

    private final SplashPresenterInterface presenter =  new SplashPresenter(this);
    private final Handler handler = new Handler();
    private MyPresenceViewModel mpvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        presenter.hideActionBar();
        this.mpvm = new MyPresenceViewModel(getApplication());

        Runnable prepopDBThread = () -> {
            AppDatabase.prepopulateDBwithDateInfo();
        };
        prepopDBThread.run();

        Pair<Boolean, Pair<String, String>> usernameAndPass = presenter.checkForAutomaticLogin();
        if (!usernameAndPass.first) {
            presenter.startLoginActivity(LoginActivity.class, handler,null,null);
        } else {
                this.presenter.startLoginActivity(LoginActivity.class, handler,usernameAndPass.second.second, usernameAndPass.second.first);
            }
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.removeCallBack();
        super.onPause();
    }
}