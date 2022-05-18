package com.app.mypresence.model.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public int userOwnerOfStat;

    public DateInfo(final String status, final Date date){
        this.status = status;
        this.date = date;
    }

    public int getDateInfoId() {
        return dateInfoId;
    }

    public String getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }
}
