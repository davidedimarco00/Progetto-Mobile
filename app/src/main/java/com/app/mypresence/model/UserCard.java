package com.app.mypresence.model;

/*MODEL FOR CARDVIEW*/


import android.net.Uri;

public class UserCard {

    private String name;
    private String surname;
    private Uri image;

    public UserCard(String name, String surname, Uri image) {
        this.name = name;
        this.surname = surname;
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Uri getImage() {
        return image;
    }
}
