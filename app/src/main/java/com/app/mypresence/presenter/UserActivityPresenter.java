package com.app.mypresence.presenter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.mypresence.R;
import com.app.mypresence.model.LoginModel;
import com.app.mypresence.model.LoginModelInterface;
import com.app.mypresence.model.UserActivityModel;
import com.app.mypresence.model.UserActivityModelInterface;
import com.app.mypresence.view.AdminFragment;
import com.app.mypresence.view.UserFragment;

public class UserActivityPresenter extends Presenter implements UserActivityPresenterInterface {

    private final AppCompatActivity activity;
    private final UserActivityModelInterface model = new UserActivityModel(this);

    public UserActivityPresenter(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void showUserFragment(Class<UserFragment> userFragment) {
        this.showFragment(userFragment);
    }

    @Override
    public void showAdminFragment(Class<AdminFragment> adminFragment) {
        this.showFragment(adminFragment);
    }

    private void showFragment(Class<? extends Fragment> fragment) {
        this.activity.getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, fragment, null)
                .commit();
    }
}
