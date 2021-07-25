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

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn1 = (Button)findViewById(R.id.passwordlogin);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(i);
                SharedPreferences settings=getSharedPreferences("setting",0);
                settings.edit().clear().commit();
            }
        });

        Button btn2 = (Button)findViewById(R.id.nextstep1);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String phone_number=((EditText)findViewById(R.id.phonenumber2)).getText().toString().trim();
                SharedPreferences settings=getSharedPreferences("setting",0);
                SharedPreferences.Editor editor=settings.edit();
                editor.putString("ph",phone_number);
                editor.commit();
                if(phone_number.equals("") ){
                    Toast.makeText(MainActivity2.this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
                }
                JSONObject jsonParam=new JSONObject();
                jsonParam.put("phone_number",phone_number);
                jsonParam.put("clientIp","123");
                String json = jsonParam.toJSONString();
                MediaType mediaType=MediaType.Companion.parse("application/json;charset=utf-8");
                RequestBody requestBody=RequestBody.Companion.create(json,mediaType);
                OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/sendSM", requestBody,  new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(e);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String data = response.body().string();
                        Looper.prepare();
                        if(data.equals("SmsError"))
                            Toast.makeText(MainActivity2.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(MainActivity2.this, "已发送验证码", Toast.LENGTH_SHORT).show();
                            editor.putString("msgId",data);
                            editor.commit();
                            Intent i = new Intent(MainActivity2.this,VerActivity.class);
                            startActivity(i);
                        }
                        Looper.loop();

                    }
                });

            }
        });
    }
}