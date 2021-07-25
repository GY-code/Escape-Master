package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        Button btn1 = (Button)findViewById(R.id.nextstep4);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent i = new Intent(SetPasswordActivity.this,InforActivity.class);
                startActivity(i);

            }
        });
    }
}