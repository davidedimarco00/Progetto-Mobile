package com.app.mypresence.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mypresence.model.LoginModel;
import com.app.mypresence.model.LoginModelInterface;
import com.app.mypresence.view.UserActivity;

public class LoginPresenter extends Presenter implements LoginPresenterInterface {
    private final AppCompatActivity activity;
    private final LoginModelInterface model;

    public LoginPresenter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        this.model = new LoginModel(this);
    }

    @Override
    public boolean login(String username, String password) {
        return this.model.login(username, password);
    }

    @Override
    public void rememberLogin(String password, String username) {
        this.model.saveLogin(password,username);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this.activity;
    }

    @Override
    public void startUserActivity() {
        Intent intent = new Intent(activity.getApplicationContext(), UserActivity.class);
        intent.putExtra("userInfo", this.model.getUserInfo());
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public Context getActivityContext() {
        return super.getActivityContext();
    }

}
