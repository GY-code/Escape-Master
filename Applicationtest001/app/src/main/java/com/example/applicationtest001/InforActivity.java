package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                if(username.equals("") || information.equals("")){
                    Toast.makeText(InforActivity.this,"用户信息不完整",Toast.LENGTH_SHORT).show();
                }
                else {
                    JSONObject jsonParam=new JSONObject();
                    jsonParam.put("phone_number",phone_number);
                    jsonParam.put("password",password);
                    jsonParam.put("nickname",username);
                    jsonParam.put("gender",gender);
                    jsonParam.put("signature",information);
                    String json = jsonParam.toJSONString();
                    MediaType mediaType=MediaType.Companion.parse("application/json;charset=utf-8");
                    RequestBody requestBody=RequestBody.Companion.create(json,mediaType);
                    OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/RegisterS2", requestBody,  new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String data = response.body().string();
                            Looper.prepare();
                            if(data.equals("register error"))
                                Toast.makeText(InforActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(InforActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(InforActivity.this,MainpageActivity.class);
                                startActivity(i);
                            }
                            Looper.loop();

                        }
                    });

                }
            }
        });
    }
}