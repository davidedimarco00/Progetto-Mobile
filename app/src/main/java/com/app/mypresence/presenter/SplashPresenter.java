package com.app.mypresence.presenter;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mypresence.view.LoginActivity;
import com.app.mypresence.view.SplashScreen;

public class SplashPresenter extends Presenter implements SplashPresenterInterface {
    private final AppCompatActivity activity;
    private Handler handler;


    public SplashPresenter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    Runnable runLogin = new Runnable() {
        @Override
        public void run() {
            if (!activity.isFinishing()){
                activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
                activity.finish();
            }
        }
    };

    @Override
    public void startLoginActivity(Class<LoginActivity> startActivity, Handler handler) {
        this.handler = handler;
        //this.handler.postDelayed(runLogin, 2000);
    }

    @Override
    public void removeCallBack() {
        this.handler.removeCallbacks(runLogin);
    }

    @Override
    public void onResume() {
        this.handler.postDelayed(runLogin, 4000);
    }
}
