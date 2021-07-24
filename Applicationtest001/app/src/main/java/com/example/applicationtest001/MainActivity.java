package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.button4);

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button)findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        Button btn3 = (Button)findViewById(R.id.button);

        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username=((EditText)findViewById(R.id.phonenumber1)).getText().toString().trim();
                String password=((EditText)findViewById(R.id.password1)).getText().toString().trim();
                if(username.equals("")||password.equals(""))
                    Toast.makeText(MainActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                else
                {
                    new Thread(){

                        private HttpURLConnection connection;


                        @Override
                        public void run() {

                            try {
                                //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                                // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                                String data2= "ph="+ URLEncoder.encode(username,"utf-8")+"&pw="+ URLEncoder.encode(password,"utf-8");
                                connection=HttpConnectionUtils.getConnection(data2,"Login");
                                int code = connection.getResponseCode();
                                if(code==200){
                                    InputStream inputStream = connection.getInputStream();
                                    String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                                    Message message = Message.obtain();//更新UI就要向消息机制发送消息
                                    message.what=0;//用来标志是哪个消息
                                    message.obj=str;//消息主体
                                    System.out.println(str);
                                    handler.sendMessage(message);
                                }
                                else {
                                    Message message = Message.obtain();
                                    message.what=1;
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                                e.printStackTrace();
                                Message message = Message.obtain();
                                message.what=2;
                                handler.sendMessage(message);
                            }
                        }
                    }.start();//不要忘记开线程
                }


            }
        });

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if(msg.obj.equals("Login failed"))
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    else
                    {
                        Toast.makeText(MainActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this,MainpageActivity.class);
                        startActivity(i);
                    }
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "Internet error", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

}
