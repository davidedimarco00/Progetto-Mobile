package com.app.mypresence.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mypresence.model.LoginModel;
import com.app.mypresence.model.LoginModelInterface;
import com.app.mypresence.view.UserActivity;

public class LoginPresenter extends Presenter implements LoginPresenterInterface {
    private final AppCompatActivity activity;
    private final LoginModelInterface model = new LoginModel(this);

    public LoginPresenter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public boolean login(String username, String password) {
        //if json contains the username and password is correct log in else not login
        //model.login()
        return true;
    }

    @Override
    public void rememberLogin() {
        this.model.saveLogin();
    }

    @Override
    public AppCompatActivity getActivity() {
        return this.activity;
    }

    @Override
    public void startUserActivity() {
        Intent intent = new Intent(activity.getApplicationContext(), UserActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public Context getActivityContext() {
        return super.getActivityContext();
    }

}
