package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InforActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
    }
}