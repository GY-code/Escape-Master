package com.example.applicationtest001.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.UI.Chatroom.ChatActivity;
import com.example.applicationtest001.UI.Function.MainpageActivity;
import com.example.applicationtest001.Tool.OkHttpUtils;
import com.example.applicationtest001.R;
import com.example.applicationtest001.UI.Register.RegisterActivity;
import com.example.applicationtest001.Tool.StringUtils;
import com.example.applicationtest001.Class.User;
import com.example.applicationtest001.im.JWebSocketClient;
import com.example.applicationtest001.im.JWebSocketClientService;
import com.example.applicationtest001.modle.ChatMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    public static JWebSocketClientService jWebSClientService;
    private ChatMessageReceiver chatMessageReceiver;
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    private Context mContext;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //Log.e("MainActivity", "服务与活动成功绑定");
            binder = (JWebSocketClientService.JWebSocketClientBinder) iBinder;
            jWebSClientService = binder.getService();
            client = jWebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //Log.e("MainActivity", "服务与活动成功断开");
        }
    };

    private class ChatMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message=intent.getStringExtra("message");
            ChatMessage chatMessage=new ChatMessage();
            chatMessage.setContent(message);
            chatMessage.setIsMeSend(0);
            chatMessage.setIsRead(1);
            chatMessage.setTime(System.currentTimeMillis()+"");
            chatMessageList.add(chatMessage);
            Intent intent_1=new Intent(ChatActivity.action);
            intent_1.putExtra("content",message);
            sendBroadcast(intent_1);
            //initChatMsgListView("all");
        }
    }


    private void bindService() {
        Intent bindIntent = new Intent(mContext, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }
    /**
     * 启动服务（websocket客户端服务）
     */
    private void startJWebSClientService() {
        Intent intent = new Intent(mContext, JWebSocketClientService.class);
        startService(intent);
    }
    /**
     * 动态注册广播
     */
    private void doRegisterReceiver() {
        chatMessageReceiver = new ChatMessageReceiver();
        IntentFilter filter = new IntentFilter("com.xch.servicecallback.content");
        registerReceiver(chatMessageReceiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=MainActivity.this;
        startJWebSClientService();
        //绑定服务
        bindService();
        //注册广播
        doRegisterReceiver();

        Button btn1 = (Button) findViewById(R.id.SMSlogin);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button) findViewById(R.id.teamchat);

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        Button btn3 = (Button) findViewById(R.id.task);


        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (true) {
                    String username = ((EditText) findViewById(R.id.phonenumber1)).getText().toString().trim();
                    String password = ((EditText) findViewById(R.id.password1)).getText().toString().trim();
                    if (username.equals("") || password.equals(""))
                        Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    else {
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("ph", username);
                        jsonParam.put("pw", password);
                        String json = jsonParam.toJSONString();
                        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
                        RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
                        OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/LoginByPw", requestBody, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println(e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String data = response.body().string();
                                Looper.prepare();
                                if (data.equals("number not registered"))
                                    Toast.makeText(MainActivity.this, "账号未注册", Toast.LENGTH_SHORT).show();
                                else {
                                    if (data.equals("incorrect password"))
                                        Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    else {
                                        User.user = JSON.parseObject(data, User.class);
                                        Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                        SharedPreferences settings = getSharedPreferences("setting", 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("ph", username);
                                        editor.putString("password", password);
                                        editor.commit();
                                        Intent i = new Intent(MainActivity.this, MainpageActivity.class);
                                        startActivity(i);
                                    }
                                }
                                Looper.loop();

                            }
                        });
                    }

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences settings = getSharedPreferences("setting", 0);
        settings.edit().clear().commit();
    }

}
