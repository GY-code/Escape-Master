package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.Class.User;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Tool.OkHttpUtils;
import com.example.applicationtest001.UI.Register.InforActivity;

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
        TextView t1=findViewById(R.id.editName1);
        TextView t2=findViewById(R.id.editName2);
        TextView t3=findViewById(R.id.editgender);
        TextView t4=findViewById(R.id.editinfo);
        t1.setText(User.user.getPhone_number());
        t2.setText(User.user.getNickname());
        if(User.user.getGender()==1)
            t3.setText("男");
        else
            t3.setText("女");
        t4.setText(User.user.getSignature());
        Button btn=findViewById(R.id.editconfirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=t1.getText().toString().trim();
                String nickname=t2.getText().toString().trim();
                String gender=t3.getText().toString().trim();
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
                    OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/ModifuInfo", requestBody,  new Callback() {
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
                                User.user= JSON.parseObject(data,User.class);
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