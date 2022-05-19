package com.app.mypresence.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.mypresence.model.database.MyPresenceViewModel;
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
