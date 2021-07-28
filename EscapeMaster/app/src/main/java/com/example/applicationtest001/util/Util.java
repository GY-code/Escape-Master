package com.example.applicationtest001.util;

import android.content.Context;
import android.widget.Toast;

public class Util {
    public static final String ws = "ws://106.14.105.16:8080/chat";//websocket测试地址

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
