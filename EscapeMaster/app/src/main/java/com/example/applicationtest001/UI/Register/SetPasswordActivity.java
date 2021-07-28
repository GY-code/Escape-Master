package com.example.applicationtest001.UI.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.applicationtest001.Tool.CheckStrength;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Tool.StringUtils;

public class SetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        EditText password1 = findViewById(R.id.setpassword1);
        EditText password2 = findViewById(R.id.setpassword2);

        password1.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btn1 = (Button)findViewById(R.id.RegisterButton);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String pw1 = password1.getText().toString().trim();
                String pw2 = password2.getText().toString().trim();
                if (!pw1.equals(pw2))
                    Toast.makeText(SetPasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                else {
                    SharedPreferences settings = getSharedPreferences("setting", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("password", pw1);
                    editor.commit();
                    Intent i = new Intent(SetPasswordActivity.this, InforActivity.class);
                    startActivity(i);
                }

            }
        });

        ImageView imageView1 = findViewById(R.id.Passwordstrength);
        ImageView imageView2 = findViewById(R.id.judge);

        EditText editText1 = findViewById(R.id.setpassword1);
        EditText editText2 = findViewById(R.id.setpassword2);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckStrength cs=new CheckStrength();
                if (StringUtils.equalsNull(editText1.getText().toString())) {
                    return;
                }
                int level=cs.checkPasswordStrength(editText1.getText().toString());
                Log.i("password",level+"");
                switch (level) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        imageView1.setImageResource(R.drawable.di);
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        imageView1.setImageResource(R.drawable.zhong);
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    default:
                        imageView1.setImageResource(R.drawable.gao);
                        break;

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText1.getText()==editText2.getText())
                    imageView2.setImageResource(R.drawable.correct);
                else
                    imageView2.setImageResource(R.drawable.wrong);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}