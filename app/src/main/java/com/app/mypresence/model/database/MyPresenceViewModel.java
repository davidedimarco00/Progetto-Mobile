package com.app.mypresence.model.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MyPresenceViewModel extends AndroidViewModel {
    private MutableLiveData<List<User>> users;

    private UserRepository userRepo;

    public MyPresenceViewModel(@NonNull Application application){
        super(application);
        this.userRepo = new UserRepository(application);
        this.users = this.userRepo.getAllUsers();
    }

    MutableLiveData<List<User>> getAllUsers(){return this.users;}

    public void addUser(final User user){this.userRepo.addUser(user);}

}
