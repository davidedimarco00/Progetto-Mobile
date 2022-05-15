package com.app.mypresence.presenter;

import android.content.Context;
import android.os.Handler;

import com.app.mypresence.view.LoginActivity;

public interface PresenterInterface {
    void hideActionBar();

    Context getActivityContext();
}
