
package com.guyi.helloworld;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static final String TAG = "abc";
    TextView tv;
    private Timer timer;
    private TimerTask timerTask;
    private static final int MY_PERMISSION_REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public BroadcastReceiver rssiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            gainWiFiInfo();
        }
    };

    private void startTimeTask() {
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    ((WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE)).startScan();
                }
            };
        }
        timer.schedule(timerTask, 0, 5 * 1000);
    }

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
        Log.d("Rssi", "Destroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTimeTask();
        checkPermission();
        gainWiFiInfo();

        IntentFilter filter;
        filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        registerReceiver(rssiReceiver, filter);

        Button refreshButton = (Button) findViewById(R.id.button);
        refreshButton.setOnClickListener(v -> {
            gainWiFiInfo();
        });
        Button writeButton = (Button) findViewById(R.id.button2);
        writeButton.setOnClickListener(v -> {
            try {
                writeInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        boolean isGranted = true;
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)
                isGranted = false;
        }
        if (!isGranted) {
            //申请权限
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS,
                    MY_PERMISSION_REQUEST_CODE
            );
        } else {
            //Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "checkPermission: 已经授权！");
        }
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)
                isGranted = false;
        }
        if (isGranted == false) {
            Log.i(TAG, "checkPermission: 授权失败！");
        }
        /*//用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
        }*/
    }

    List<ScanResult> results = null;

    private void gainWiFiInfo() {

        String wserviceName = Context.WIFI_SERVICE;
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        wm.createWifiLock(3, "wifilock");

        tv = (TextView) findViewById(R.id.textView1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss\n");
        Date curDate = new Date(System.currentTimeMillis());
        results = wm.getScanResults();
        String time = formatter.format(curDate);
        String otherwifi = "Network--" + time;

        for (ScanResult result : results) {
            if (result.SSID.equals("phone.wlan.bjtu") || result.SSID.equals("local.wlan.bjtu") || result.SSID.equals("web.wlan.bjtu"))
                otherwifi += result.SSID + "(" + result.BSSID + "):" + result.level + "\n";
        }

        tv.setText(otherwifi);
    }

    private void writeInfo() throws IOException {
        SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd");
        SimpleDateFormat timeformat = new SimpleDateFormat("MM-dd-HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String date = dateformat.format(curDate);
        String time = timeformat.format(curDate);
        writeFile(date + ".txt", time);
        Log.i(TAG, "filename:" + date + ".txt");
    }

    BufferedWriter bw = null;

    public void writeFile(String fileName, String time) throws IOException {

        EditText et = (EditText) findViewById(R.id.editTextNumber);
        if (et.getText().toString().trim().equals("")) {
            showMyDialog("请输入位置码");
            return;
        }
        try {
            File file = new File(getExternalFilesDir("").getAbsolutePath() + "/" + fileName);
            if (bw == null)
                bw = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file, true)));
            bw.newLine();
            if (results == null)
                return;
            boolean flag=false;
            for (ScanResult result : results) {
                if (result.SSID.equals("phone.wlan.bjtu") || result.SSID.equals("local.wlan.bjtu") || result.SSID.equals("web.wlan.bjtu"))
                {
                    if(flag) {
                        bw.newLine();
                    }
                    flag=true;
                    bw.write(et.getText().toString() + " " + time  + " " + result.BSSID + " " + result.level);
                    bw.flush();
                }
            }
            showMyDialog("已保存到"+getExternalFilesDir("").getAbsolutePath() + "/" + fileName);


        } catch (Exception e) {
            e.printStackTrace();
        }

    /*    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(getExternalFilesDir("").getAbsolutePath() + "/" + fileName)));
        out.write(time);
        out.flush();*/
    }

    private void showMyDialog(String text) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(text)
                .setPositiveButton("确定", null)
                .show();
    }


}