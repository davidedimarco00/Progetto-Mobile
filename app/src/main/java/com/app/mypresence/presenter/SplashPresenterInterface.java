package com.app.mypresence.presenter;

import android.os.Handler;

import com.app.mypresence.view.LoginActivity;

public interface SplashPresenterInterface extends PresenterInterface {
    void startLoginActivity(Class<LoginActivity> startActivity, Handler handler);

    void removeCallBack();

    void onResume();
}
