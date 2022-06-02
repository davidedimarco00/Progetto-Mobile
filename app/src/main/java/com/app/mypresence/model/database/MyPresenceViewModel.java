package com.app.mypresence.model.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserRepository;

import java.util.List;
import java.util.Map;

import kotlin.Pair;

public class MyPresenceViewModel extends AndroidViewModel {
    private List<User> users;

    private UserRepository userRepo;

    public MyPresenceViewModel(@NonNull Application application){
        super(application);
        this.userRepo = new UserRepository(application);
        this.users = this.userRepo.getAllUsers();
    }

    public List<User> getAllUsers(){return this.users;}

    public void updateDateInfo(DateInfo dateInfo) {
        this.userRepo.updateDateInfo(dateInfo);
    }

    public void addUser(final User user){this.userRepo.addUser(user);}

    public List<User> getUserFromUsernameAndPassword(String username, String password){
        return this.userRepo.getUserFromUsernameAndPassword(username, password);
    }

    public void addStats(final DateInfo dateInfo){
        this.userRepo.addStats(dateInfo);
    }

    public List<UserAndStats> getUserStats(final String username, final String password){
        return this.userRepo.getUserStats(username, password);
    }

    public String getMatrice(final String username, final String password){
        return this.userRepo.getMatrice(username, password);
    }

    public int totalWorkedHoursThisMonth(final String username, final String password){
        return this.userRepo.totalWorkedHoursThisMonth(username, password);
    }

    public int averageHoursWorkedThisMonth(final String username, final String password){
        return this.userRepo.averageWorkedHoursThisMonth(username, password);
    }

    public Pair<String, Pair<Integer, Integer>> mostWorkedHoursInDay(final String username, final String password){
        return this.userRepo.mostWorkedHoursInDay(username, password);
    }

    public String earliestArrival(final String username, final String password){
        return this.userRepo.earliestArrival(username, password);
    }

    public String latestLeave(final String username, final String password){
        return this.userRepo.latestLeave(username, password);
    }

    public Map<String, List<Long>> getMonthStatus(final String username, final String password, final int month){
        return this.userRepo.getMonthStatus(username, password, month);
    }

}
