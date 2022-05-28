package com.app.mypresence.model.database.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.utils.SHA1;

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
    @ColumnInfo(name = "matrice")
    private String matrice;
    @ColumnInfo(name = "role")
    private String role;
    @ColumnInfo(name = "bio")
    private String bio;

    public User(final String name, final String surname, final String username, final String bio, final String password, final String profile_image, final boolean isAdmin, final String role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.profile_image = profile_image;
        this.isAdmin = isAdmin;
        this.bio = bio;
        this.role = role;
        this.matrice = SHA1.SHA1(AppDatabase.getMyPresNFCcode() + SHA1.SHA1(this.username));
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

    public String getMatrice() {
        return matrice;
    }

    public void setMatrice(String matrice) {
        this.matrice = matrice;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
