package com.app.mypresence.presenter;

import androidx.appcompat.app.AppCompatActivity;

public interface LoginPresenterInterface extends PresenterInterface {

    boolean login(String username, String password);

    void startUserActivity();

    void rememberLogin(String password, String username);

    AppCompatActivity getActivity();

}
