package com.app.mypresence.presenter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


/**
 * This class provide a presenter for the activity passed in the costructor according to
 * MVP architecture
 * */
public class Presenter implements PresenterInterface {

    private final AppCompatActivity activity;

    public Presenter(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void hideActionBar() {
        Objects.requireNonNull(this.activity.getSupportActionBar()).hide();
    }
}
