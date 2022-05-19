package com.app.mypresence.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.mypresence.model.LoginModel;
import com.app.mypresence.model.LoginModelInterface;
import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserDAO;
import com.app.mypresence.view.UserActivity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginPresenter extends Presenter implements LoginPresenterInterface {
    private final AppCompatActivity activity;
    private final LoginModelInterface model;
    private MyPresenceViewModel myPresenceViewModel;

    public LoginPresenter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        this.model = new LoginModel(this);
        this.myPresenceViewModel = new ViewModelProvider(this.activity).get(MyPresenceViewModel.class);
        //this.myPresenceViewModel.addUser(new User("Davide", "Di Marco", "dima", "dima1", ""));
    }

    @Override
    public boolean login(String password, String username) {
        System.out.println("---------------");
        this.myPresenceViewModel.getUserFromUsernameAndPassword(username).observe(this.activity, users -> {

            Log.e("DIOCANE", String.valueOf(users.size()));
            for (User user : users) {
                Log.e("User: ",user.getUsername());
            }
        });
        System.out.println("---------------");
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
