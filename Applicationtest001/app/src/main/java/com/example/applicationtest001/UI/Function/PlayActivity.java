package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Tool.OkHttpUtils;
import com.example.applicationtest001.UI.Chatroom.ChatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button btn1 = (Button)findViewById(R.id.teamchat);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PlayActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button)findViewById(R.id.task);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PlayActivity.this,TaskActivity.class);
                startActivity(i);
            }
        });

        Button btn3 = (Button)findViewById(R.id.editperson);

        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences settings=getSharedPreferences("setting",0);
                String phone_number= settings.getString("ph","");
                JSONObject jsonParam=new JSONObject();
                jsonParam.put("phone_number",phone_number);
                jsonParam.put("wifi_strength","strength");
                String json = jsonParam.toJSONString();
                MediaType mediaType=MediaType.Companion.parse("application/json;charset=utf-8");
                RequestBody requestBody=RequestBody.Companion.create(json,mediaType);
                OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/getPositon", requestBody,  new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(e);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String data = response.body().string();

                    }
                });
            }
        });
    }
}