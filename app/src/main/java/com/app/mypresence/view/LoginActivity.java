package com.app.mypresence.view;
import com.app.mypresence.R;
import com.app.mypresence.presenter.LoginPresenter;
import com.app.mypresence.presenter.Presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    Presenter presenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter.hideActionBar();
    }
}