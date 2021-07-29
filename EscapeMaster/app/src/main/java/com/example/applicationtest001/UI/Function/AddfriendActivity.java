package com.example.applicationtest001.UI.Function;

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
import com.example.applicationtest001.Class.Friend;
import com.example.applicationtest001.Class.User;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Tool.OkHttpUtils;
import com.example.applicationtest001.UI.Login.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddfriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        Button btn=findViewById(R.id.Add);
        EditText editText=findViewById(R.id.input_phone);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=editText.getText().toString();
                if(phone.equals("")){
                    Toast.makeText(AddfriendActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(Isfriend(phone)){
                    Toast.makeText(AddfriendActivity.this, "该用户已是好友", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences settings = getSharedPreferences("setting", 0);
                    String myphone=settings.getString("ph","0");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("myphone", myphone);
                    jsonParam.put("phonw", phone);
                    String json = jsonParam.toJSONString();
                    MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
                    RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
                    OkHttpUtils.sendOkHttpResponse("http://192.168.43.72:8080/user/AddFriend", requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String data = response.body().string();
                            Looper.prepare();
                            if(data.equals("no user"))
                            Toast.makeText(AddfriendActivity.this, "没有该用户", Toast.LENGTH_SHORT).show();
                            else
                            {
                                Friend.getFriend(myphone);
                                Intent intent = new Intent(AddfriendActivity.this,MainpageActivity.class);
                                startActivity(intent);
                            }
                            Looper.loop();

                        }
                    });
                }
            }
        });
    }

    private boolean Isfriend(String phone){
        for (int i = 0; i < Friend.getFriendlist().size(); i++) {
            if(phone.equals(Friend.getFriendlist().get(i).getPhone_number()))
                return true;
        }
        return false;
    }
}