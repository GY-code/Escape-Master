package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

        ImageView imageView = findViewById(R.id.Passwordstrength);

        EditText editText = findViewById(R.id.setpassword1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                imageView.setImageResource(R.drawable.di);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}