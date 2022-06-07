package com.app.mypresence.view;
import com.app.mypresence.R;
import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.presenter.LoginPresenter;
import com.app.mypresence.presenter.LoginPresenterInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private LoginPresenterInterface presenter;

    AppCompatButton loginButton;
    CheckBox checkBox;
    EditText usernameText;
    EditText passwordText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setProgress(10);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading...");

        this.presenter  = new LoginPresenter(this);

        presenter.hideActionBar();
        loginButton = (AppCompatButton) findViewById(R.id.btnLogin);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        usernameText = (EditText) findViewById(R.id.username_input);
        passwordText = (EditText) findViewById(R.id.pass_input);

        Runnable prepopDBThread = () -> {
            AppDatabase.prepopulateDBwithDateInfo();
        };
        prepopDBThread.run();


        loginButton.setOnClickListener(view -> {

            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();


            Runnable loginThread = () -> {
                    if (this.presenter.login(password, username)){
                        if (checkBox.isChecked()){
                            presenter.rememberLogin();
                        }
                        new MyTask().execute(); //this task show the progress dialog
                        presenter.startUserActivity();
                    }else{
                        Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                    }

            };
            loginThread.run();
        });

        this.passwordText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                return true;
            }
        });



    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        public void onPreExecute() {
            progressDialog.show();
        }
        public Void doInBackground(Void... unused) {
            return null;
        }
    }
}


