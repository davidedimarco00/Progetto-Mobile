package com.app.mypresence.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.mypresence.model.utils.Converters;

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

    public int userOwnerOfStat;

    public DateInfo(final String status, final Date date, final int workedHours){
        this.status = status;
        this.date = date;
        this.workedHours = workedHours;
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

    public void setWorkedHours(int workedHours) {this.workedHours = workedHours;}

}
