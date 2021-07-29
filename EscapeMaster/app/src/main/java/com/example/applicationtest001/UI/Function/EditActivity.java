package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.applicationtest001.Class.User;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Tool.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText t1=findViewById(R.id.editName1);
        EditText t2=findViewById(R.id.editName2);
        RadioButton radioButton1=(RadioButton)findViewById(R.id.maleradioButton);
        RadioButton radioButton2=(RadioButton)findViewById(R.id.femaleradioButton);
        EditText t4=findViewById(R.id.editinfo);
        t1.setText(Friend.user.getPhone_number());
        t2.setText(Friend.user.getNickname());
        if(Friend.user.getGender()==1)
            radioButton1.setChecked(true);
        else
            radioButton2.setChecked(true);
        t4.setText(Friend.user.getSignature());
        Button btn=findViewById(R.id.editconfirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=t1.getText().toString().trim();
                String nickname=t2.getText().toString().trim();
                String gender;
                if(((RadioButton)findViewById(R.id.maleradioButton)).isChecked())
                    gender="1";
                else
                    gender="0";
                String information=t4.getText().toString().trim();
                if(username.equals("")||nickname.equals("")||gender.equals("")||information.equals(""))
                    Toast.makeText(EditActivity.this,"用户信息不完整",Toast.LENGTH_SHORT).show();
                else {
                    JSONObject jsonParam=new JSONObject();
                    jsonParam.put("phone_number",username);
                    jsonParam.put("nickname",nickname);
                    jsonParam.put("gender",gender);
                    jsonParam.put("signature",information);
                    String json = jsonParam.toJSONString();
                    MediaType mediaType=MediaType.Companion.parse("application/json;charset=utf-8");
                    RequestBody requestBody=RequestBody.Companion.create(json,mediaType);
                    OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/ModifyInfo", requestBody,  new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String data = response.body().string();
                            Looper.prepare();
                            if(data.equals("Modify error"))
                                Toast.makeText(EditActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            else {
                                Friend.user= JSON.parseObject(data,User.class);
                                Toast.makeText(EditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EditActivity.this, MainpageActivity.class);
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