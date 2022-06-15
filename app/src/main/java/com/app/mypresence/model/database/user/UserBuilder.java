package com.app.mypresence.model.database.user;

public class UserBuilder {

    private String name;
    private String surname;
    private String username;
    private String bio;
    private String password;
    private String profileImage;
    private boolean isAdmin;
    private String role;
    private String company;

    public UserBuilder name(final String name){
        this.name = name;
        return this;
    }

    public UserBuilder surname(final String surname){
        this.surname = surname;
        return this;
    }

    public UserBuilder username(final String username){
        this.username = username;
        return this;
    }

    public UserBuilder company(final String company){
        this.company = company;
        return this;
    }

    public UserBuilder password(final String password){
        this.password = password;
        return this;
    }

    public UserBuilder bio(final String bio){
        this.bio = bio;
        return this;
    }

    public UserBuilder role(final String role){
        this.role = role;
        return this;
    }

    public UserBuilder profileImage(final String profileImage){
        this.profileImage = profileImage;
        return this;
    }

    public UserBuilder isAdmin(final boolean isAdmin){
        this.isAdmin = isAdmin;
        return this;
    }

    public User buildUser(){
        return new User(this.name, this.surname,
                this.username, this.bio,
                this.password, this.profileImage,
                this.isAdmin, this.role,this.company);
    }

}
