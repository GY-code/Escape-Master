package com.example.applicationtest001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        requestData();
    }

    private void requestData() {
        ArrayList<HashMap<String, String>> datas = new ArrayList<HashMap<String,String>>();
        for(int i = 1; i <= 10; i++){
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("phoneType", "密室" + i + "");
            item.put("discount", "9");
            item.put("price", (2000 + i) + "");
            item.put("time", "2016020" + i);
            item.put("num", (300 - i) + "");
            datas.add(item);
        }

        ListView lvProduct = (ListView) findViewById(R.id.lv_products);
        OneExpandAdapter adapter = new OneExpandAdapter(this, datas);
        lvProduct.setAdapter(adapter);
    }
}