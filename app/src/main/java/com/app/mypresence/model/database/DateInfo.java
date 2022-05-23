package com.app.mypresence.model.database;

import android.util.Pair;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.mypresence.model.utils.Converters;
import com.app.mypresence.model.utils.TimeManagement;

import java.util.Date;

@Entity
public class DateInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dateInfoId")
    private int dateInfoId;

    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "workedHours")
    private int workedHours;
    @ColumnInfo(name = "workedMinutes")
    private int workedMinutes;
    @ColumnInfo(name = "startShiftTime")
    private String startShiftTime;
    @ColumnInfo(name = "endShiftTime")
    private String endShiftTime;

    public int userOwnerOfStat;

    public DateInfo(final String status, final Date date, final String startShiftTime, final String endShiftTime){
        this.status = status;
        this.date = date;
        this.startShiftTime = startShiftTime;
        this.endShiftTime = endShiftTime;
        Pair<Integer, Integer> workedTime = TimeManagement.getInterval(startShiftTime, endShiftTime);
        this.workedHours = workedTime.first.intValue();
        this.workedMinutes = workedTime.second.intValue();
    }

    public int getDateInfoId() {
        return dateInfoId;
    }

    public String getStatus() { return status; }

    public Date getDate() { return this.date; }

    public void setDateInfoId(int dateInfoId) {
        this.dateInfoId = dateInfoId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWorkedHours() {return workedHours;}
    public int getWorkedMinutes() {
        return workedMinutes;
    }

    public void setWorkedMinutes(int workedMinutes) {
        this.workedMinutes = workedMinutes;
    }
    public void setWorkedHours(int workedHours) {this.workedHours = workedHours;}

    public String getStartShiftTime() {
        return startShiftTime;
    }

    public void setStartShiftTime(String startShiftTime) {
        this.startShiftTime = startShiftTime;
    }

    public String getEndShiftTime() {
        return endShiftTime;
    }

    public void setEndShiftTime(String endShiftTime) {
        this.endShiftTime = endShiftTime;
    }
}
