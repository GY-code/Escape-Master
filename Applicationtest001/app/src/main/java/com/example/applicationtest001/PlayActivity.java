package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button btn1 = (Button)findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PlayActivity.this,ChatActivity.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button)findViewById(R.id.button);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PlayActivity.this,TaskActivity.class);
                startActivity(i);
            }
        });
    }
}