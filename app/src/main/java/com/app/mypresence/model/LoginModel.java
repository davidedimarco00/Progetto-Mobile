package com.app.mypresence.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.Presenter;

public class LoginModel extends Model implements LoginModelInterface {

    private LoginPresenterInterface presenter;
    private SharedPreferences sharedPref;
    private MyPresenceViewModel myPresenceViewModel;

    public LoginModel(Presenter presenter){
        Log.e ("presenter", presenter.toString());
        this.presenter =  (LoginPresenterInterface) presenter;
        this.sharedPref = this.presenter.getActivityContext().getSharedPreferences("loginPreferences",Context.MODE_PRIVATE);
        this.myPresenceViewModel = new ViewModelProvider(this.presenter.getActivity()).get(MyPresenceViewModel.class);
        //this.myPresenceViewModel.addUser(new User("Davide", "Di Marco", "dima", "dima1", ""));
    }

    @Override
    public boolean login(String password, String username) {
        return !this.myPresenceViewModel.getUserFromUsernameAndPassword(username, password).isEmpty();
    }

    @Override
    public void saveLogin() {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putBoolean("loggedIn", true);
        editor.apply();

        Log.e("login auto ", String.valueOf(this.sharedPref.getBoolean("loggedIn", false)));
    }

    @Override
    public Bundle getUserInfo() {
        Bundle userBundle = new Bundle();
        //userBundle.putString(              );
        return userBundle;
    }
}
