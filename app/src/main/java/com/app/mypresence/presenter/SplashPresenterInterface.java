package com.app.mypresence.presenter;

import android.os.Handler;
import android.util.Pair;

import com.app.mypresence.view.LoginActivity;

public interface SplashPresenterInterface extends PresenterInterface {
    void startLoginActivity(Class<LoginActivity> startActivity, Handler handler, String username, String password);

    void removeCallBack();

    void onResume();

    Pair<Boolean, Pair<String, String>> checkForAutomaticLogin();

    void startUserActivity(Handler handler);

    boolean checkForFirstLaunch();
}
