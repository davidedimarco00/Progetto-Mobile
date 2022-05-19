package com.app.mypresence.model.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserRepository;

import java.util.List;

public class MyPresenceViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;

    private UserRepository userRepo;

    public MyPresenceViewModel(@NonNull Application application){
        super(application);
        this.userRepo = new UserRepository(application);
        this.users = this.userRepo.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers(){return this.users;}

    public void addUser(final User user){this.userRepo.addUser(user);}

    public List<User> getUserFromUsernameAndPassword(String username, String password){
        return this.userRepo.getUserFromUsernameAndPassword(username, password);
    }

}
