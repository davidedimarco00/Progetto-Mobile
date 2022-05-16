package com.app.mypresence.model;

import com.app.mypresence.presenter.Presenter;
import com.app.mypresence.presenter.SplashPresenterInterface;
import com.app.mypresence.presenter.UserActivityPresenter;
import com.app.mypresence.presenter.UserActivityPresenterInterface;

public class UserActivityModel extends Model implements UserActivityModelInterface {

    private UserActivityPresenterInterface presenter;

    public UserActivityModel(Presenter presenter){
        this.presenter = (UserActivityPresenterInterface) presenter;
    }
}
