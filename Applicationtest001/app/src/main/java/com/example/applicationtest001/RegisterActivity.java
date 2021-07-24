package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn1 = (Button)findViewById(R.id.button8);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button)findViewById(R.id.button7);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String username = ((EditText)findViewById(R.id.phonenumber)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.password2)).getText().toString().trim();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(RegisterActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences settings=getSharedPreferences("setting",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putString("ph",username);
                    editor.putString("password",password);
                    editor.commit();
                    new Thread(){

                        HttpURLConnection connection=null;
                        @Override
                        public void run() {
                            try {
                                String data= "ph="+ URLEncoder.encode(username,"utf-8")+"&pw="+ URLEncoder.encode(password,"utf-8");
                                connection=HttpConnectionUtils.getConnection(data,"RegisterS1");
                                int code = connection.getResponseCode();
                                if(code==200){
                                    InputStream inputStream = connection.getInputStream();
                                    String str = StreamChangeStrUtils.toChange(inputStream);
                                    Message message = Message.obtain();
                                    message.obj=str;
                                    message.what=0;
                                    System.out.println(str);
                                    handler.sendMessage(message);
                                }
                                else {
                                    Message message = Message.obtain();
                                    message.what=1;
                                    message.obj="注册异常...请稍后再试";
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Message message = Message.obtain();
                                message.what=1;
                                message.obj="服务器异常...请稍后再试";
                                handler.sendMessage(message);
                            }

                        }
                    }.start();//不要忘记开线程

                }
            }

        });

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if(msg.obj.equals("register failed"))
                        Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this,InforActivity.class);
                        startActivity(i);
                    }
                    break;
                case 1:
                    Toast.makeText(RegisterActivity.this, "Internet error", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
}