package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.SMSlogin);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button)findViewById(R.id.teamchat);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        Button btn3 = (Button)findViewById(R.id.task);

        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username=((EditText)findViewById(R.id.phonenumber1)).getText().toString().trim();
                String password=((EditText)findViewById(R.id.password1)).getText().toString().trim();
                if(username.equals("")||password.equals(""))
                    Toast.makeText(MainActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                else
                {
                    JSONObject jsonParam=new JSONObject();
                    jsonParam.put("ph",username);
                    jsonParam.put("pw",password);
                    String json = jsonParam.toJSONString();
                    MediaType mediaType=MediaType.Companion.parse("application/json;charset=utf-8");
                    RequestBody requestBody=RequestBody.Companion.create(json,mediaType);
                    OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/LoginByPw", requestBody,  new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String data = response.body().string();
                            Looper.prepare();
                            if(data.equals("number not registered"))
                                Toast.makeText(MainActivity.this, "账号未注册", Toast.LENGTH_SHORT).show();
                            else {
                                if(data.equals("incorrect password"))
                                    Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                else
                                {
                                    User.user=JSON.parseObject(data,User.class);
                                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                    SharedPreferences settings=getSharedPreferences("setting",0);
                                    SharedPreferences.Editor editor=settings.edit();
                                    editor.putString("ph",username);
                                    editor.putString("password",password);
                                    editor.commit();
                                    Intent i = new Intent(MainActivity.this,MainpageActivity.class);
                                    startActivity(i);
                                }
                            }
                            Looper.loop();

                        }
                    });
                }

            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences settings=getSharedPreferences("setting",0);
        settings.edit().clear().commit();
    }

}
