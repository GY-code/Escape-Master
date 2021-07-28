package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.applicationtest001.R;

public class ModifypasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText password1 = findViewById(R.id.input_password1);
        EditText password2 = findViewById(R.id.input_password2);
        EditText password3 = findViewById(R.id.input_password3);

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypassword);

//        password1.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btn1 = findViewById(R.id.confirm);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(ModifypasswordActivity.this,MainpageActivity.class);
                startActivity(i);
            }
        });
    }
}