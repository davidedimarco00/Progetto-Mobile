package com.app.mypresence.presenter;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mypresence.model.SplashScreenModel;
import com.app.mypresence.model.SplashScreenModelInterface;
import com.app.mypresence.view.LoginActivity;
import com.app.mypresence.view.UserActivity;

public class SplashPresenter extends Presenter implements SplashPresenterInterface {
    private final AppCompatActivity activity;
    private final SplashScreenModelInterface model = new SplashScreenModel(this);
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

    Runnable runUserActivity = new Runnable() {
        @Override
        public void run() {
            if (!activity.isFinishing()){
                activity.startActivity(new Intent(activity.getApplicationContext(), UserActivity.class));
                activity.finish();
            }
        }
    };
    @Override
    public void startLoginActivity(Class<LoginActivity> startActivity, Handler handler) {
        this.handler = handler;
    }

    @Override
    public void startUserActivity(Handler handler) {
        this.handler = handler;
        this.handler.postDelayed(runUserActivity, 4000);
    }

    @Override
    public void removeCallBack() {
        this.handler.removeCallbacks(runLogin);
        this.handler.removeCallbacks(runUserActivity);
    }

    @Override
    public void onResume() {
        this.handler.postDelayed(runLogin, 4000);

    }

    @Override
    public boolean checkForAutomaticLogin() {
        return this.model.checkSavedLoginData();
    }

    @Override
    public boolean checkForFirstLaunch() {
        return this.model.checkFirstLaunch();
    }
}
