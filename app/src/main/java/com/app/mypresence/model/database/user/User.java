package com.app.mypresence.model.database.user;

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
    @ColumnInfo(name = "isAdmin")
    private boolean isAdmin;

    public User(final String name, final String surname, final String username, final String password, final String profile_image, final boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.profile_image = profile_image;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {return this.userId;}

    public String getName() {return this.name;}

    public String getSurname() {return this.surname;}

    public String getUsername() {return this.username;}

    public String getPassword() {return this.password;}

    public String getProfile_image() {return this.profile_image;}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public boolean isAdmin() {return isAdmin;}

    public void setAdmin(boolean admin) {isAdmin = admin;}
}
