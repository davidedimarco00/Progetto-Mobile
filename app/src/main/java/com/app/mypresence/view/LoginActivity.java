package com.app.mypresence.view;
import com.app.mypresence.R;
import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.presenter.LoginPresenter;
import com.app.mypresence.presenter.LoginPresenterInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private LoginPresenterInterface presenter;

    AppCompatButton loginButton;
    CheckBox checkBox;
    EditText usernameText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.presenter  = new LoginPresenter(this);

        presenter.hideActionBar();
        loginButton = (AppCompatButton) findViewById(R.id.btnLogin);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        usernameText = (EditText) findViewById(R.id.username_input);
        passwordText = (EditText) findViewById(R.id.pass_input);

        loginButton.setOnClickListener(view -> {

            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();

            Runnable loginThread = () -> {
                    if (this.presenter.login(password, username)){
                        if (checkBox.isChecked()){
                            presenter.rememberLogin();
                        }
                        presenter.startUserActivity();
                    }else{
                        Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                    }
                AppDatabase.prepopulateDBwithDateInfo();
            };

            loginThread.run();


        });

    }

}