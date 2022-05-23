package com.app.mypresence.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.Presenter;

import java.util.List;

public class LoginModel extends Model implements LoginModelInterface {

    private LoginPresenterInterface presenter;
    private SharedPreferences sharedPref;
    private MyPresenceViewModel myPresenceViewModel;
    private User loggedUser ;

    public LoginModel(Presenter presenter){
        Log.e ("presenter", presenter.toString());
        this.presenter =  (LoginPresenterInterface) presenter;
        this.sharedPref = this.presenter.getActivityContext().getSharedPreferences("loginPreferences",Context.MODE_PRIVATE);
        this.myPresenceViewModel = new ViewModelProvider(this.presenter.getActivity()).get(MyPresenceViewModel.class);
        //this.myPresenceViewModel.addUser(new User("Davide", "Di Marco", "dima", "dima1", ""));
    }

    @Override
    public boolean login(String password, String username) {
        List<User> usersList = this.myPresenceViewModel.getUserFromUsernameAndPassword(username, password);
        this.loggedUser = usersList.isEmpty() ? null : usersList.get(0);
        return this.loggedUser != null;
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
        if (this.loggedUser != null) {
            userBundle.putString("name", this.loggedUser.getName());
            userBundle.putString("surname", this.loggedUser.getSurname());
            userBundle.putString("image", this.loggedUser.getProfile_image()); //directory a image
            userBundle.putInt("userId", this.loggedUser.getUserId());
            userBundle.putString("username", this.loggedUser.getUsername());
            userBundle.putString("password", this.loggedUser.getPassword());
            userBundle.putBoolean("isAdmin", this.loggedUser.isAdmin());
        }
        return userBundle;
    }
}
