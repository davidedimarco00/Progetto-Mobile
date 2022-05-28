package com.app.mypresence.presenter;

import androidx.appcompat.app.AppCompatActivity;

import com.app.mypresence.model.LoginModelInterface;
import com.app.mypresence.model.NFCModel;
import com.app.mypresence.model.NFCModelInterface;

public class NFCActivityPresenter extends Presenter implements NFCPresenterInterface{

    private final NFCModelInterface model;

    public NFCActivityPresenter(AppCompatActivity activity) {
        super(activity);
        this.model = new NFCModel(this);
    }


}
