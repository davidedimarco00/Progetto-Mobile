package com.app.mypresence.model.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserRepository {

    private final UserDAO userDAO;

    private final MutableLiveData<List<User>> allUsers;
    private final MutableLiveData<List<UserAndStats>> usersAndStats;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDAO();

        this.allUsers = this.userDAO.getAllUsers();
        this.usersAndStats = this.userDAO.getUsersWithStats();
    }

    public MutableLiveData<List<User>> getAllUsers(){
        return this.allUsers;
    }

    public MutableLiveData<List<UserAndStats>> getAllUsersAndStats(){
        return this.usersAndStats;
    }

    public void addUser(final User user){
        AppDatabase.executor.execute(() -> this.userDAO.addUser(user));
    }

}
