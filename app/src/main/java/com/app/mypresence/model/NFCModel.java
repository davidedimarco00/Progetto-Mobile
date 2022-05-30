package com.app.mypresence.model;

import android.app.Activity;
import android.content.Context;

import com.app.mypresence.presenter.NFCPresenterInterface;
import com.app.mypresence.presenter.Presenter;

public class NFCModel extends Model implements NFCModelInterface {

    private final NFCPresenterInterface presenter;


    Context context;

    public NFCModel(Presenter presenter, Activity context) {
        this.presenter = (NFCPresenterInterface) presenter;
        this.context = context;

    }

    @Override
    public void startNFC() {
       /* if (!this.nfcProvider.checkDeviceNfcSupport()){
            this.presenter.Error("NFC_NOT_SUPPORT");
        }else {
            //readFromIntent(th);

        }*/
    }
}
