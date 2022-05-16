package com.app.mypresence.view;
import com.app.mypresence.R;
import com.app.mypresence.presenter.SplashPresenter;
import com.app.mypresence.presenter.SplashPresenterInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends AppCompatActivity {

    private final SplashPresenterInterface presenter =  new SplashPresenter(this);
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        presenter.hideActionBar();
            if (!presenter.checkForAutomaticLogin()) {
                presenter.startLoginActivity(LoginActivity.class, handler);
            } else {
                presenter.startUserActivity(handler);
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