package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class InforActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender;
                if(findViewById(R.id.radioButton3).isSelected())
                    gender="1";
                else
                    gender="0";
                String username = ((EditText)findViewById(R.id.editTextTextPersonName)).getText().toString().trim();
                String information = ((EditText)findViewById(R.id.editTextTextPersonName2)).getText().toString().trim();
                SharedPreferences settings=getSharedPreferences("setting",0);
                String phone_number= settings.getString("ph","");
                String password=settings.getString("password","");
                settings.edit().clear().commit();
                if(username.equals("") || information.equals("")){
                    Toast.makeText(InforActivity.this,"用户信息不完整",Toast.LENGTH_SHORT).show();
                }
                else {
                    new Thread(){

                        HttpURLConnection connection=null;
                        @Override
                        public void run() {
                            try {
                                String data= "phone_number="+ URLEncoder.encode(phone_number,"utf-8")+"&password="+ URLEncoder.encode(password,"utf-8")+
                                        "&nickname="+URLEncoder.encode(username,"utf-8")+"&gender="+URLEncoder.encode(gender,"utf-8")+"&signature="+
                                        URLEncoder.encode(information,"utf-8");
                                connection=HttpConnectionUtils.getConnection(data,"RegisterS2");
                                int code = connection.getResponseCode();
                                if(code==200){
                                    InputStream inputStream = connection.getInputStream();
                                    String str = StreamChangeStrUtils.toChange(inputStream);
                                    System.out.println(str);
                                    Intent i = new Intent(InforActivity.this,MainpageActivity.class);
                                    startActivity(i);
                                }
                                else {
                                    Toast.makeText(InforActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();//不要忘记开线程

                }
            }
        });
    }
}