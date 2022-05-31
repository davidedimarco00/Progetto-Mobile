package com.app.mypresence.model.database.user;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.UserAndStats;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    public void updateDateInfo(DateInfo dateInfo) {
        this.userDAO.updateDateInfo(dateInfo);
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

    public String earliestArrival(final String username, final String password){
        List<UserAndStats> userAndStats = this.getUserStats(username, password);
        List<DateInfo> dateInfos = userAndStats.get(0).stats;
        int currentMonth = this.getCurrentMonth();
        int minH = 24;
        int minMin = 60;
        for(DateInfo dateInfo : dateInfos){
            if(dateInfo.getDate().getMonth() == currentMonth){
                String[] time = dateInfo.getStartShiftTime().split(":");
                int hour = Integer.valueOf(time[0]);
                int min = Integer.valueOf(time[1]);
                if(hour < minH && min < minMin){
                    minMin = min;
                    minH = hour;
                }
            }
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        String hours = this.timeFixerFormat(minH);
        String minutes = this.timeFixerFormat(minMin);
        return hours + ":" + minutes;
    }

    public String latestLeave(final String username, final String password){
        List<UserAndStats> userAndStats = this.getUserStats(username, password);
        List<DateInfo> dateInfos = userAndStats.get(0).stats;
        int currentMonth = this.getCurrentMonth();
        int maxH = 0;
        int maxMin = 0;
        for(DateInfo dateInfo : dateInfos){
            if(dateInfo.getDate().getMonth() == currentMonth){
                String[] time = dateInfo.getEndShiftTime().split(":");
                int hour = Integer.valueOf(time[0]);
                int min = Integer.valueOf(time[1]);
                if(hour > maxH && min > maxMin){
                    maxMin = min;
                    maxH = hour;
                }
            }
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        String hours = this.timeFixerFormat(maxH);
        String minutes = this.timeFixerFormat(maxMin);
        return hours + ":" + minutes;
    }

    private String timeFixerFormat(final int time){
        return time > 9 ? String.valueOf(time) : "0" +  String.valueOf(time);
    }

}
