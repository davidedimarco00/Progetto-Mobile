package com.app.mypresence.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.mypresence.R;
import com.app.mypresence.presenter.LoginPresenter;
import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.NFCActivityPresenter;
import com.app.mypresence.presenter.NFCPresenterInterface;

public class NFCActivity extends AppCompatActivity {

    private NFCPresenterInterface presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcactivity);
        this.presenter  = new NFCActivityPresenter(this);
    }
}