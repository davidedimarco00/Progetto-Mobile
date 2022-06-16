package com.app.mypresence.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import com.app.mypresence.presenter.Presenter;
import com.app.mypresence.presenter.SplashPresenterInterface;

public class SplashScreenModel extends Model implements SplashScreenModelInterface {

    private SplashPresenterInterface presenter;

    public SplashScreenModel(Presenter presenter){
        this.presenter = (SplashPresenterInterface) presenter;
    }

    @Override
    public Pair<Boolean, Pair<String, String>> checkSavedLoginData() {
        SharedPreferences prefs = this.presenter.getActivityContext().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("loggedIn", false);
        String username = prefs.getString("username", "DEFAULT");
        String password = prefs.getString("password", "DEFAULT");
        Log.e("AUTO LOGIN", String.valueOf(loggedIn)); /*JUST DEBUG*/
        return new Pair<>(loggedIn, new Pair<>(password, username));
    }

    @Override
    public boolean checkFirstLaunch() {
        SharedPreferences prefs = this.presenter.getActivityContext().getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        boolean firstLaunch = prefs.getBoolean("firstLaunch", true);
       // Log.e("AUTO LOGIN", String.valueOf(firstLaunch)); /*JUST DEBUG*/
        return firstLaunch;
    }


}
