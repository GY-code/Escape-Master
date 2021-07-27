package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.applicationtest001.R;

public class ModifypasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypassword);

        Button btn1 = findViewById(R.id.confirm);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(ModifypasswordActivity.this,MainpageActivity.class);
                startActivity(i);
            }
        });
    }
}