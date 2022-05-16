package com.app.mypresence.presenter;

import com.app.mypresence.view.AdminFragment;
import com.app.mypresence.view.UserFragment;

public interface UserActivityPresenterInterface extends PresenterInterface {

    void showUserFragment(Class<UserFragment> userFragment);
    void showAdminFragment(Class<AdminFragment> adminFragment);

}
