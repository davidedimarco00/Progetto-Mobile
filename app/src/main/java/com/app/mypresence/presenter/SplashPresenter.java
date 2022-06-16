package com.app.mypresence.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mypresence.model.SplashScreenModel;
import com.app.mypresence.model.SplashScreenModelInterface;
import com.app.mypresence.view.LoginActivity;
import com.app.mypresence.view.UserActivity;

public class SplashPresenter extends Presenter implements SplashPresenterInterface {
    private final AppCompatActivity activity;
    private final SplashScreenModelInterface model = new SplashScreenModel(this);
    private Handler handler;
    private String username;
    private String password;


    public SplashPresenter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    Runnable runLogin = new Runnable() {
        @Override
        public void run() {
            if (!activity.isFinishing()){
                Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);

                if (username != null && password != null) {
                    Bundle userBundle = new Bundle();
                    userBundle.putString("username", username);
                    userBundle.putString("password", password);
                    intent.putExtra("userInfoLogin", userBundle);
                }

                activity.startActivity(intent);
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
    public void startLoginActivity(Class<LoginActivity> startActivity, Handler handler, String username, String password) {
        this.handler = handler;
        this.username = username;
        this.password = password;
    }

    @Override
    public void startUserActivity(Handler handler) {
        this.handler = handler;
        this.handler.postDelayed(runUserActivity, 8000);
    }

    @Override
    public void removeCallBack() {
        this.handler.removeCallbacks(runLogin);
        this.handler.removeCallbacks(runUserActivity);
    }

    @Override
    public void onResume() {
        this.handler.postDelayed(runLogin, 8000);

    }

    @Override
    public Pair<Boolean, Pair<String, String>> checkForAutomaticLogin() {
        return this.model.checkSavedLoginData();
    }

    @Override
    public boolean checkForFirstLaunch() {
        return this.model.checkFirstLaunch();
    }
}
