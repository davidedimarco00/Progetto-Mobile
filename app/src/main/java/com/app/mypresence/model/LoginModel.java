package com.app.mypresence.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.Presenter;

public class LoginModel extends Model implements LoginModelInterface {

    private LoginPresenterInterface presenter;
    private SharedPreferences sharedPref;


    public LoginModel(Presenter presenter){
        Log.e ("presenter", presenter.toString());
        this.presenter =  (LoginPresenterInterface) presenter;
        this.sharedPref = this.presenter.getActivityContext().getSharedPreferences("loginPreferences",Context.MODE_PRIVATE);

    }

    @Override
    public boolean login(String password, String username) {
        return true;
    }

    @Override
    public void saveLogin() {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putBoolean("loggedIn", true);
        editor.apply();

        Log.e("login auto ", String.valueOf(this.sharedPref.getBoolean("loggedIn", false)));
    }
}
