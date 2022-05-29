package com.app.mypresence.model.database.user;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.UserAndStats;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.Pair;

public class UserRepository {

    private final UserDAO userDAO;

    private final List<User> allUsers;
    private final List<UserAndStats> usersAndStats;

    public UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);

        this.userDAO = db.userDAO();

        this.allUsers = this.userDAO.getAllUsers();
        this.usersAndStats = this.userDAO.getUsersWithStats();
    }

    public List<User> getAllUsers(){
        return this.allUsers;
    }

    public List<UserAndStats> getAllUsersAndStats(){
        return this.usersAndStats;
    }

    public void addUser(final User user){
        AppDatabase.executor.execute(() -> this.userDAO.addUser(user));
    }

    public void addStats(final DateInfo dateInfo){
        AppDatabase.executor.execute(() -> this.userDAO.addDateInfo(dateInfo));
    }

    public List<User> getUserFromUsernameAndPassword(String username, String password){
        return this.userDAO.checkIfUsernameAndPasswordAreCorrect(username, password);
    }

    public List<UserAndStats> getUserStats(final String username, final String password){
        return this.userDAO.getUserStats(username, password);
    }

    public String getMatrice(final String username, final String password){
        return this.userDAO.getMatrice(username, password);
    }

    public int totalWorkedHoursThisMonth(final String username, final String password){
        int currentMonth = this.getCurrentMonth();
        List<UserAndStats> userAndStats = this.getUserStats(username, password);
        List<DateInfo> dateInfos = userAndStats.get(0).stats;
        int totalWorkedHours = 0;
        for(DateInfo dateInfo : dateInfos){
            if(dateInfo.getDate().getMonth() == currentMonth){
                totalWorkedHours += dateInfo.getWorkedHours();
            }
        }
        return totalWorkedHours;
    }

    private int getCurrentMonth(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        return Integer.valueOf(formatter.format(currentDate).substring(3,5))-1;
    }

    private int getDaysWorked(final String username, final String password){
        List<UserAndStats> userAndStats = this.getUserStats(username, password);
        List<DateInfo> dateInfos = userAndStats.get(0).stats;
        int days = 0;
        int currentMonth = this.getCurrentMonth();
        for(DateInfo dateInfo : dateInfos){
            if(dateInfo.getDate().getMonth() == currentMonth){
                days += 1;
            }
        }
        return days;
    }

    public int averageWorkedHoursThisMonth(final String username, final String password){
        int totalHoursWorked = this.totalWorkedHoursThisMonth(username, password);
        return totalHoursWorked / this.getDaysWorked(username, password);
    }

    public Pair<String, Pair<Integer, Integer>> mostWorkedHoursInDay(final String username, final String password){
        List<UserAndStats> userAndStats = this.getUserStats(username, password);
        List<DateInfo> dateInfos = userAndStats.get(0).stats;
        int currentMonth = this.getCurrentMonth();
        List<DateInfo> thisMonthInfo = new ArrayList<>();
        Pair<Integer, Integer> maxTimeHoursMinutes = new Pair<>(0, 0);
        Date date = new Date();
        for(DateInfo dateInfo : dateInfos){
            if(dateInfo.getDate().getMonth() == currentMonth){
                int workedH = dateInfo.getWorkedHours();
                int workedMins = dateInfo.getWorkedMinutes();
                if(workedH > maxTimeHoursMinutes.getFirst() && workedMins > maxTimeHoursMinutes.getSecond()){
                    maxTimeHoursMinutes = new Pair<>(workedH, workedMins);
                    date = dateInfo.getDate();
                }
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return new Pair<>(formatter.format(date), maxTimeHoursMinutes);
    }

}
