package com.app.mypresence.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mypresence.model.LoginModelInterface;
import com.app.mypresence.model.NFCModel;
import com.app.mypresence.model.NFCModelInterface;

import java.time.Duration;

public class NFCActivityPresenter extends Presenter implements NFCPresenterInterface{

    private final NFCModelInterface model;
    private final Context context;

    public NFCActivityPresenter(AppCompatActivity activity) {
        super(activity);
        this.context = activity.getBaseContext();
        this.model = new NFCModel(this, activity);
    }


    @Override
    public void startNFC() {
        this.model.startNFC();
    }

    @Override
    public void Error(String message) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
    }
}
