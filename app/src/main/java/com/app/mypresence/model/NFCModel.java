package com.app.mypresence.model;

import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.NFCPresenterInterface;
import com.app.mypresence.presenter.Presenter;
import com.app.mypresence.model.nfcUtils.*;

public class NFCModel extends Model implements NFCModelInterface {

    private final NFCPresenterInterface presenter;
    public NFCModel(Presenter presenter) {
        this.presenter = (NFCPresenterInterface) presenter;
    }
}
