package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.applicationtest001.R;
import com.example.applicationtest001.UI.Login.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent it = new Intent(this, MainActivity.class); //下一步转向Mainctivity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it);finish(); //执行意图
            }
        };
        timer.schedule(task, 1000 * 1); //3秒后跳转，这里根据自己需要设定时间

    }

}
