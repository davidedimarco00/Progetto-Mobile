package com.app.mypresence.model;

import android.util.Pair;

public interface SplashScreenModelInterface {
    Pair<Boolean, Pair<String, String>> checkSavedLoginData();

    boolean checkFirstLaunch();
}
