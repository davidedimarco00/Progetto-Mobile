package com.app.mypresence.presenter;

import android.content.Intent;

import com.app.mypresence.view.AdminFragment;
import com.app.mypresence.view.SettingsFragment;
import com.app.mypresence.view.StatisticsFragment;
import com.app.mypresence.view.UserFragment;

public interface UserActivityPresenterInterface extends PresenterInterface {

    void showUserFragment(UserFragment userFragment);
    void showAdminFragment(AdminFragment adminFragment);

    void showStatisticFragment(StatisticsFragment statisticsFragmentClass);

    void showSettingsFragment(SettingsFragment settingsFragmentClass);
}
