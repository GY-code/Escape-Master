package com.example.applicationtest001.UI.Chatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.Adapter.Adapter_ChatMessage;
import com.example.applicationtest001.Class.Friend;
import com.example.applicationtest001.Class.Msg;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Adapter.fMsgAdapter;
import com.example.applicationtest001.UI.Login.MainActivity;
import com.example.applicationtest001.im.JWebSocketClient;
import com.example.applicationtest001.im.JWebSocketClientService;
import com.example.applicationtest001.modle.ChatMessage;
import com.example.applicationtest001.util.Util;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FChatActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String action = "PersonChat.action";
    private Context mContext;
    private JWebSocketClient client;
    private JWebSocketClientService jWebSClientService;
    private EditText et_content;
    private ListView listView;
    private Button btn_send;
    private TextView friendname;
    private List<ChatMessage> chatMessageList = new ArrayList<>();//消息列表
    private Adapter_ChatMessage adapter_chatMessage;
    PersonReceiver personReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();


        setContentView(R.layout.activity_fchat);
        client = MainActivity.client;
        jWebSClientService = MainActivity.jWebSClientService;
        mContext = FChatActivity.this;
        doRegisterReceiver();
        //checkNotification(mContext);
        findViewById();
        initView();

    }

    private void doRegisterReceiver() {
        personReceiver = new PersonReceiver();
        IntentFilter filter = new IntentFilter(action);
        registerReceiver(personReceiver, filter);

    }

    private void findViewById() {
        friendname=findViewById(R.id.friendname);
        listView = findViewById(R.id.msg_list_view1);
        btn_send = findViewById(R.id.send1);
        et_content = findViewById(R.id.input_text1);
        btn_send.setOnClickListener(this);
    }
    private void initView() {
        friendname.setText(Friend.name);
        //监听输入框的变化
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_content.getText().toString().length() > 0) {
                    btn_send.setVisibility(View.VISIBLE);
                } else {
                    btn_send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send1:
                String content = et_content.getText().toString();
                String receiver=((TextView)findViewById(R.id.friendname)).getText().toString().trim();
                if (content.length() <= 0) {
                    Util.showToast(mContext, "消息不能为空哟");
                    return;
                }

                if (client != null && client.isOpen()) {

                    try {
                        SharedPreferences settings=getSharedPreferences("setting",0);
                        String sender=settings.getString("ph","0");
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("sender",sender);
                        jsonObject.put("id","123");
                        Date date=new Date();
                        SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                        String datestring=formatter.format(date);
                        jsonObject.put("sendtime",datestring);
                        jsonObject.put("content",content);
                        jsonObject.put("saver",receiver);
                        String js=jsonObject.toJSONString();
                        jWebSClientService.sendMsg(js);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //暂时将发送的消息加入消息列表，实际以发送成功为准（也就是服务器返回你发的消息时）
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setContent(content);
                    chatMessage.setIsMeSend(1);
                    chatMessage.setIsRead(1);
                    chatMessage.setTime(System.currentTimeMillis() + "");
                    chatMessageList.add(chatMessage);
                    initChatMsgListView();
                    et_content.setText("");
                } else {
                    Util.showToast(mContext, "连接已断开，请稍等或重启App哟");
                }
                break;
            default:
                break;
        }
    }
    private class PersonReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("content");
            String sender=intent.getStringExtra("sender");
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent(message);
            chatMessage.setSender(sender);
            chatMessage.setIsMeSend(0);
            chatMessage.setIsRead(1);
            chatMessage.setTime(System.currentTimeMillis() + "");
            chatMessageList.add(chatMessage);
            initChatMsgListView();
        }
    }

    public void initChatMsgListView() {
        adapter_chatMessage = new Adapter_ChatMessage(mContext, chatMessageList);
        listView.setAdapter(adapter_chatMessage);
        listView.setSelection(chatMessageList.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(personReceiver);

    }
}