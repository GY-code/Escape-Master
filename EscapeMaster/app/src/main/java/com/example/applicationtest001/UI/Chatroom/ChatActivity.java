package com.example.applicationtest001.UI.Chatroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.Adapter.Adapter_ChatMessage;
import com.example.applicationtest001.Class.Msg;
import com.example.applicationtest001.Adapter.MsgAdapter;
import com.example.applicationtest001.R;


import com.example.applicationtest001.UI.Login.MainActivity;
import com.example.applicationtest001.im.JWebSocketClient;
import com.example.applicationtest001.im.JWebSocketClientService;
import com.example.applicationtest001.modle.ChatMessage;
import com.example.applicationtest001.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    //    private ListView msgListView;
//    private EditText inputText;
//    private Button send;
//    private MsgAdapter adapter;
//
//    private List<Msg> msgList = new ArrayList<Msg>();
    public static final String action = "RoomChat.action";
    private Context mContext;
    private JWebSocketClient client;
    private JWebSocketClientService jWebSClientService;
    private EditText et_content;
    private ListView listView;
    private Button btn_send;
    private List<ChatMessage> chatMessageList = new ArrayList<>();//消息列表
    private Adapter_ChatMessage adapter_chatMessage;
    RoomReceiver roomReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);
        client = MainActivity.client;
        jWebSClientService = MainActivity.jWebSClientService;
        mContext = ChatActivity.this;
        doRegisterReceiver();
        //checkNotification(mContext);
        findViewById();
        initView();
    }



    /**
     * 动态注册广播
     */
    private void doRegisterReceiver() {
        roomReceiver = new RoomReceiver();
        IntentFilter filter = new IntentFilter(action);
        registerReceiver(roomReceiver, filter);

    }

    private void findViewById() {
        listView = findViewById(R.id.msg_list_view);
        btn_send = findViewById(R.id.send);
        et_content = findViewById(R.id.input_text);
        btn_send.setOnClickListener(this);
    }

    private void initView() {
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
            case R.id.send:
                String content = et_content.getText().toString();
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
                        jsonObject.put("saver","all");
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

    private class RoomReceiver extends BroadcastReceiver {

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

    //    private void checkNotification(final Context context) {
//        if (!isNotificationEnabled(context)) {
//            new AlertDialog.Builder(context).setTitle("温馨提示")
//                    .setMessage("你还未开启系统通知，将影响消息的接收，要去开启吗？")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            setNotification(context);
//                        }
//                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            }).show();
//        }
//    }
//    private void setNotification(Context context) {
//        Intent localIntent = new Intent();
//        //直接跳转到应用通知设置的代码：
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
//            localIntent.putExtra("app_package", context.getPackageName());
//            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
//        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
//            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
//        } else {
//            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
//            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (Build.VERSION.SDK_INT >= 9) {
//                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
//            } else if (Build.VERSION.SDK_INT <= 8) {
//                localIntent.setAction(Intent.ACTION_VIEW);
//                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
//                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
//            }
//        }
//        context.startActivity(localIntent);
//    }
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private boolean isNotificationEnabled(Context context) {
//
//        String CHECK_OP_NO_THROW = "checkOpNoThrow";
//        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
//
//        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//        ApplicationInfo appInfo = context.getApplicationInfo();
//        String pkg = context.getApplicationContext().getPackageName();
//        int uid = appInfo.uid;
//
//        Class appOpsClass = null;
//        try {
//            appOpsClass = Class.forName(AppOpsManager.class.getName());
//            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
//                    String.class);
//            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
//
//            int value = (Integer) opPostNotificationValue.get(Integer.class);
//            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(roomReceiver);

    }
}