package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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

public class RegisterActivity extends AppCompatActivity {
    private boolean notEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn1 = (Button) findViewById(R.id.button8);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button) findViewById(R.id.RegisterButton);
        EditText mobileText = (EditText) findViewById(R.id.setMobile);
        EditText smText = (EditText) findViewById(R.id.setSM);
        mobileText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.equalsNull(mobileText.getText().toString()) || StringUtils.equalsNull(smText.getText().toString())) {
                    int tint = Color.parseColor("white");
                    btn2.getBackground().setColorFilter(tint, PorterDuff.Mode.DARKEN);
                    notEmpty = false;
                } else {
                    int tint = Color.parseColor("purple");
                    btn2.getBackground().setColorFilter(tint, PorterDuff.Mode.DARKEN);
                    notEmpty = true;
                }

            }
        });
        smText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.equalsNull(mobileText.getText().toString()) || StringUtils.equalsNull(smText.getText().toString())) {
                    int tint = Color.parseColor("white");
                    btn2.getBackground().setColorFilter(tint, PorterDuff.Mode.DARKEN);
                    notEmpty = false;
                } else {
                    int tint = Color.parseColor("purple");
                    btn2.getBackground().setColorFilter(tint, PorterDuff.Mode.DARKEN);
                    notEmpty = true;
                }

            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (notEmpty) {
                    String username = ((EditText) findViewById(R.id.setMobile)).getText().toString().trim();
                    String password = ((EditText) findViewById(R.id.setSM)).getText().toString().trim();
                    if (username.equals("") || password.equals("")) {
                        Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences settings = getSharedPreferences("setting", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("ph", username);
                        editor.putString("password", password);
                        editor.commit();
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("phone_number", username);
                        jsonParam.put("password", password);
                        String json = jsonParam.toJSONString();
                        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
                        RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
                        OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/RegisterS1", requestBody, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println(e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String data = response.body().string();
                                Looper.prepare();
                                if (data.equals("number registered"))
                                    Toast.makeText(RegisterActivity.this, "账号已被注册", Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(RegisterActivity.this, SetPasswordActivity.class);
                                    startActivity(i);
                                }
                                Looper.loop();

                            }
                        });
                    }
                }
            }

        });

        Button btn3 = findViewById(R.id.sendver);
        btn3.setText("发送");
        btn3.setClickable(true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer time = new CountDownTimer(1000 * 60, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn3.setClickable(false);
                        btn3.setText("重新发送" + "(" + (millisUntilFinished / 1000) + ")");
                    }

                    @Override
                    public void onFinish() {
                        btn3.setText("重新发送");
                        btn3.setClickable(true);
                    }
                };

                time.start();
            }
        });

    }

}