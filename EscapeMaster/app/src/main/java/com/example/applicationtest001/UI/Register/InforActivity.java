package com.example.applicationtest001.UI.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.Class.Friend;
import com.example.applicationtest001.UI.Function.MainpageActivity;
import com.example.applicationtest001.Tool.OkHttpUtils;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Class.User;

import java.io.IOException;

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
        Button button=findViewById(R.id.task);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender;
                if(((RadioButton)findViewById(R.id.maleradioButton)).isChecked())
                    gender="1";
                else
                    gender="0";
                String username = ((EditText)findViewById(R.id.editTextPersonName)).getText().toString().trim();
                String information = ((EditText)findViewById(R.id.editTextPersonInfo)).getText().toString().trim();
                SharedPreferences settings=getSharedPreferences("setting",0);
                String phone_number= settings.getString("ph","");
                String password=settings.getString("password","");
                if(username.equals("") || information.equals("")){
                    Toast.makeText(InforActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(InforActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                            else {
                                Friend.user= JSON.parseObject(data,User.class);
                                Toast.makeText(InforActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(InforActivity.this, MainpageActivity.class);
                                startActivity(i);
                                finish();
                            }
                            Looper.loop();

                        }
                    });

                }
            }
        });
    }
}