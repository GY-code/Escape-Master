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

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        Button button1=findViewById(R.id.button11);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor=settings.edit();
                String phone_number = settings.getString("ph", "");
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("phone_number", phone_number);
                jsonParam.put("clientIp", "123");
                String json = jsonParam.toJSONString();
                MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
                RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
                OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/sendSM", requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String data = response.body().string();
                        Looper.prepare();
                        Toast.makeText(VerActivity.this, "已发送验证码", Toast.LENGTH_SHORT).show();
                        editor.putString("msgId",data);
                        editor.commit();
                        Looper.loop();

                    }

                });
            }
                });
        Button button2=findViewById(R.id.nextstep3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verify = ((EditText) findViewById(R.id.verify)).getText().toString().trim();
                if (verify.equals("")) {
                    Toast.makeText(VerActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences settings = getSharedPreferences("setting", 0);
                    SharedPreferences.Editor editor=settings.edit();
                    String msgId = settings.getString("msgId", "");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("msgId", msgId);
                    jsonParam.put("cache", verify);
                    String json = jsonParam.toJSONString();
                    MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
                    RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
                    OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/verify", requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String data = response.body().string();
                            Looper.prepare();
                            if(data.equals("false"))
                                Toast.makeText(VerActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            else
                            {
                                Toast.makeText(VerActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences settings=getSharedPreferences("setting",0);
                                settings.edit().clear().commit();
                                Intent i = new Intent(VerActivity.this,MainpageActivity.class);
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