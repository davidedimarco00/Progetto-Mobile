package com.app.mypresence.model.database.user;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.UserAndStats;

import java.util.List;

public class UserRepository {

    private final UserDAO userDAO;

    private final LiveData<List<User>> allUsers;
    private final LiveData<List<UserAndStats>> usersAndStats;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDAO();

        this.allUsers = this.userDAO.getAllUsers();
        this.usersAndStats = this.userDAO.getUsersWithStats();
    }

    public LiveData<List<User>> getAllUsers(){
        return this.allUsers;
    }

    public LiveData<List<UserAndStats>> getAllUsersAndStats(){
        return this.usersAndStats;
    }

    public void addUser(final User user){
        AppDatabase.executor.execute(() -> this.userDAO.addUser(user));
    }

    public LiveData<List<User>> getUserFromUsernameAndPassword(String username){
        return this.userDAO.checkIfUsernameAndPasswordAreCorrect(username);
    }

}
