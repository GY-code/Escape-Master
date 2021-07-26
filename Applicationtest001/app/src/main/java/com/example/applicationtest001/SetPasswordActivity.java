package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
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
                CheckStrength cs=new CheckStrength();
                if (StringUtils.equalsNull(editText.getText().toString())) {
                    return;
                }
                int level=cs.checkPasswordStrength(editText.getText().toString());
                Log.i("password",level+"");
                switch (level) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        imageView.setImageResource(R.drawable.di);
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        imageView.setImageResource(R.drawable.zhong);
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    default:
                        imageView.setImageResource(R.drawable.gao);
                        break;

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}