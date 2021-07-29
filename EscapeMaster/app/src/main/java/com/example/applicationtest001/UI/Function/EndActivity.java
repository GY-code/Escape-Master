package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.applicationtest001.R;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Button btn1 = (Button)findViewById(R.id.backbt);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndActivity.this,MainpageActivity.class);
                startActivity(i);
            }
        });
    }
}