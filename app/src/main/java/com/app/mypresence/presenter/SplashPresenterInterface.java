package com.app.mypresence.presenter;

import android.os.Handler;

import com.app.mypresence.view.LoginActivity;
import com.app.mypresence.view.UserActivity;

public interface SplashPresenterInterface extends PresenterInterface {
    void startLoginActivity(Class<LoginActivity> startActivity, Handler handler);

    void removeCallBack();

    void onResume();

    boolean checkForAutomaticLogin();

    void startUserActivity(Handler handler);

    boolean checkForFirstLaunch();
}
