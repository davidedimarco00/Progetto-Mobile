package com.app.mypresence.model;

/*MODEL FOR CARDVIEW*/


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Pair;

import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserCard {

    private String name;
    private String surname;
    private Drawable image;
    private User user;
    private List<DateInfo> infos;

    public UserCard(String name, String surname, Drawable image, User user, List<DateInfo> infos) {
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.user = user;
        this.infos = infos;
    }

    public Pair<Integer, Integer> getHoursAndMinutesWorkedThisMonth(){
        int currentMonth = this.getCurrentMonth();
        int totalWorkedHours = 0;
        int totalWorkedMinutes = 0;

        for(DateInfo info : this.infos){
            if(info.getDate().getMonth() == currentMonth){
                totalWorkedHours += info.getWorkedHours();
                totalWorkedMinutes += info.getWorkedMinutes();
            }
        }
        totalWorkedHours += (int)(totalWorkedMinutes / 60);
        totalWorkedMinutes = totalWorkedMinutes % 60;
        return new Pair<>(totalWorkedHours, totalWorkedMinutes);
    }

    private int getCurrentMonth(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        return Integer.valueOf(formatter.format(currentDate).substring(3,5))-1;
    }

    public String getRole(){return this.user.getRole();}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Drawable getImage() {
        return image;
    }
}
