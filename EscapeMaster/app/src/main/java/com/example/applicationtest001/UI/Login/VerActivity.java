package com.example.applicationtest001.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

public class VerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        EditText editText = findViewById(R.id.verify);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //获取电话号码
        SharedPreferences settings = getSharedPreferences("setting", 0);
        SharedPreferences.Editor editor=settings.edit();
        String phone_number = settings.getString("ph", "");

        TextView textView = findViewById(R.id.textViewnumber);
        textView.setText("已向您的手机"+phone_number+"发送验证码");
        Button button1=findViewById(R.id.button11);
        //button1.setText("发送");
        button1.setClickable(true);
        CountDownTimer timer1 = new CountDownTimer(1000 * 60, 1000){

            @Override
            public void onTick(long l) {
                button1.setClickable(false);
                button1.setText("重新发送" + "("+(l / 1000)+")" );
            }

            @Override
            public void onFinish() {
                button1.setText("重新发送");
                button1.setClickable(true);
            }
        };
        timer1.start();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                CountDownTimer time = new CountDownTimer(1000 * 60, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        button1.setClickable(false);
                        button1.setText("重新发送" + "("+(millisUntilFinished / 1000)+")" );
                    }

                    @Override
                    public void onFinish() {
                        button1.setText("重新发送");
                        button1.setClickable(true);
                    }
                };

                time.start();
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
                    String ph=settings.getString("ph","");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("msgId", msgId);
                    jsonParam.put("cache", verify);
                    jsonParam.put("phone_number",ph);
                    String json = jsonParam.toJSONString();
                    MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
                    RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
                    OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/LoginVerifySMS", requestBody, new Callback() {
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
                                User.user= JSON.parseObject(data,User.class);
                                Toast.makeText(VerActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(VerActivity.this, MainpageActivity.class);
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