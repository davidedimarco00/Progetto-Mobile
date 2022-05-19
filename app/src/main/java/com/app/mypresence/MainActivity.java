package com.app.mypresence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if exist login automatically show userActivity directly
        setContentView(R.layout.activity_main);

    }
}