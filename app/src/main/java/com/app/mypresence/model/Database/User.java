package com.app.mypresence.model.Database;

import android.app.usage.UsageStats;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "surname")
    private String surname;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "profile_image")
    private String profile_image;

    public User(final String name, final String surname, final String username, final String password, final String profile_image) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.profile_image = profile_image;
    }

    public int getId() {return this.userId;}

    public String getName() {return this.name;}

    public String getSurname() {return this.surname;}

    public String getUsername() {return this.username;}

    public String getPassword() {return this.password;}

    public String getProfile_image() {return this.profile_image;}
}
