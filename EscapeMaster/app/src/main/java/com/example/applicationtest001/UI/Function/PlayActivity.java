package com.example.applicationtest001.UI.Function;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.LocationMark;
import com.example.applicationtest001.PermissUtil;
import com.example.applicationtest001.R;
import com.example.applicationtest001.Tool.OkHttpUtils;
import com.example.applicationtest001.UI.Chatroom.ChatActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlayActivity extends AppCompatActivity {

    IntentFilter filter;
    private Timer timer;
    private TimerTask timerTask;
    LinearLayout frameLayout;
    ConstraintLayout constraintLayout;
    ImageView imageView;
    LocationMark locationMark ;
    HashMap<String, Integer> rssiMap = new HashMap<>();
    SharedPreferences settings;
    String phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings=getSharedPreferences("setting",0);
        phone_number= settings.getString("ph","");

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        frameLayout = (LinearLayout)findViewById(R.id.play_frame);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constrainLayout);
        imageView = new ImageView(this);
        locationMark = new LocationMark(this);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.drawable.room_map);
        frameLayout.addView(imageView);
        constraintLayout.addView(locationMark);


        PermissUtil.checkPermissions(this, PermissUtil.appNeedPermissions);

        rssiMap.put("0e:69:6c:d4:39:e6", -100);
        rssiMap.put("12:69:6c:d6:a3:7c", -100);
        rssiMap.put("0e:69:6c:d6:8d:97", -100);
        rssiMap.put("0e:69:6c:d6:8d:98", -100);
        rssiMap.put("16:69:6c:d4:21:9e", -100);
        rssiMap.put("16:69:6c:d6:a1:2f", -100);
        rssiMap.put("12:69:6c:d6:a5:30", -100);
        rssiMap.put("16:69:6c:d6:a5:2f", -100);
        rssiMap.put("12:69:6c:b9:70:d7", -100);
        rssiMap.put("16:69:6c:b9:70:d7", -100);
        rssiMap.put("0e:69:6c:d6:a3:03", -100);
        rssiMap.put("12:69:6c:d4:47:f6", -100);
        rssiMap.put("0e:69:6c:d4:47:f6", -100);
        rssiMap.put("0e:69:6c:d6:9c:bb", -100);
        rssiMap.put("16:69:6c:d6:9d:84", -100);
        rssiMap.put("16:69:6c:b9:70:c5", -100);
        rssiMap.put("16:69:6c:d6:8f:17", -100);
        rssiMap.put("0e:69:6c:d6:94:93", -100);
        rssiMap.put("0e:69:6c:b9:70:c5", -100);

        filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        registerReceiver(rssiReceiver, filter);
        Button btn1 = (Button)findViewById(R.id.teamchat);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PlayActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });



        Button btn2 = (Button)findViewById(R.id.task);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PlayActivity.this,TaskActivity.class);
                startActivity(i);
            }
        });
        Button btn3 = (Button)findViewById(R.id.editperson);

        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //startTimeTask();
                //((WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE)).startScan();
                //btn3.setClickable(false);
                //registerReceiver(rssiReceiver, filter);
            }
        });

        Button btn4 = (Button)findViewById(R.id.plot);

        btn4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PlayActivity.this,PlotActivity.class);
                startActivity(i);
            }
        });

        Button btn5 = (Button)findViewById(R.id.button2);

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayActivity.this,EndActivity.class);
                startActivity(i);
            }
        });

        startTimeTask();
    }
    private void sendWiFi(String phone_number, HashMap<String, Integer> rssiMap)
    {
        JSONObject jsonParam=new JSONObject();
        jsonParam.put("phone_number",phone_number);
        jsonParam.put("room_id","1001");
        jsonParam.put("s1",rssiMap.get("0e:69:6c:d4:39:e6"));
        jsonParam.put("s2",rssiMap.get("12:69:6c:d6:a3:7c"));
        jsonParam.put("s3",rssiMap.get("0e:69:6c:d6:8d:97"));
        jsonParam.put("s4",rssiMap.get("0e:69:6c:d6:8d:98"));
        jsonParam.put("s5",rssiMap.get("16:69:6c:d4:21:9e"));
        jsonParam.put("s6",rssiMap.get("16:69:6c:d6:a1:2f"));
        jsonParam.put("s7",rssiMap.get("12:69:6c:d6:a5:30"));
        jsonParam.put("s8",rssiMap.get("16:69:6c:d6:a5:2f"));
        jsonParam.put("s9",rssiMap.get("12:69:6c:b9:70:d7"));
        jsonParam.put("s10",rssiMap.get("16:69:6c:b9:70:d7"));
        jsonParam.put("s11",rssiMap.get("0e:69:6c:d6:a3:03"));
        jsonParam.put("s12",rssiMap.get("12:69:6c:d4:47:f6"));
        jsonParam.put("s13",rssiMap.get("0e:69:6c:d4:47:f6"));
        jsonParam.put("s14",rssiMap.get("0e:69:6c:d6:9c:bb"));
        jsonParam.put("s15",rssiMap.get("16:69:6c:d6:9d:84"));
        jsonParam.put("s16",rssiMap.get("16:69:6c:b9:70:c5"));
        jsonParam.put("s17",rssiMap.get("16:69:6c:d6:8f:17"));
        jsonParam.put("s18",rssiMap.get("0e:69:6c:d6:94:93"));
        jsonParam.put("s19",rssiMap.get("0e:69:6c:b9:70:c5"));
        String json = jsonParam.toJSONString();
        MediaType mediaType=MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody requestBody=RequestBody.Companion.create(json,mediaType);
        OkHttpUtils.sendOkHttpResponse("http://o414e98134.wicp.vip/user/getPosition", requestBody,  new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e("11111",e+"");
            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                String point[]=data.split(",");


                double x=Double.parseDouble(point[0]);
                double y=Double.parseDouble(point[1]);
                DisplayMetrics metric=new DisplayMetrics();
                //901 3478  650 1553
                getWindowManager().getDefaultDisplay().getRealMetrics(metric);
                int width=imageView.getWidth();
                int height=imageView.getHeight();
                locationMark.bitmapX=256.45453f - 23.0f + 711.0f*(float)x/1543.0f;
                locationMark.bitmapY=0f - 58.0f + 2007.0f*(float) y/4334.0f;
                System.out.println(locationMark.bitmapX+" "+locationMark.bitmapY);
                locationMark.invalidate();
            }
        });
    }

    private void startTimeTask() {
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    //TextView textView = (TextView)findViewById(R.id.textView25);
                    //textView.setText("abc");
                    ((WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE)).startScan();
                    obtainWifiInfo();
                    sendWiFi("phone_number",rssiMap);
                    Log.i("refresh","refresh");
                }
            };
        }
        timer.schedule(timerTask, 100, 3* 1000);
    }

    private void obtainWifiInfo()
    {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        List<ScanResult> results = wifiManager.getScanResults();
        System.out.println("111111111111111111111111111111111");
        rssiMap.put("0e:69:6c:d4:39:e6", -100);
        rssiMap.put("12:69:6c:d6:a3:7c", -100);
        rssiMap.put("0e:69:6c:d6:8d:97", -100);
        rssiMap.put("0e:69:6c:d6:8d:98", -100);
        rssiMap.put("16:69:6c:d4:21:9e", -100);
        rssiMap.put("16:69:6c:d6:a1:2f", -100);
        rssiMap.put("12:69:6c:d6:a5:30", -100);
        rssiMap.put("16:69:6c:d6:a5:2f", -100);
        rssiMap.put("12:69:6c:b9:70:d7", -100);
        rssiMap.put("16:69:6c:b9:70:d7", -100);
        rssiMap.put("0e:69:6c:d6:a3:03", -100);
        rssiMap.put("12:69:6c:d4:47:f6", -100);
        rssiMap.put("0e:69:6c:d4:47:f6", -100);
        rssiMap.put("0e:69:6c:d6:9c:bb", -100);
        rssiMap.put("16:69:6c:d6:9d:84", -100);
        rssiMap.put("16:69:6c:b9:70:c5", -100);
        rssiMap.put("16:69:6c:d6:8f:17", -100);
        rssiMap.put("0e:69:6c:d6:94:93", -100);
        rssiMap.put("0e:69:6c:b9:70:c5", -100);
        for (ScanResult result : results) {
            if (rssiMap.containsKey(result.BSSID)) {
                rssiMap.put(result.BSSID,result.level);
            }
            Log.i("AB",result.SSID+" "+result.level);
        }

    }
    public BroadcastReceiver rssiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.RSSI_CHANGED_ACTION) || action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)
                    || action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                //obtainWifiInfo();
/*0
                SharedPreferences settings=getSharedPreferences("setting",0);
                String phone_number= settings.getString("ph","");
                obtainWifiInfo();
                sendWiFi(phone_number,rssiMap);
*/
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(rssiReceiver, new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));
        Log.d("Rssi", "Registered");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(rssiReceiver);
        Log.d("Rssi", "Unregistered");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }
        unregisterReceiver(rssiReceiver);
    }

}