package com.example.applicationtest001.UI.Function;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.applicationtest001.Adapter.CommentAdapter;
import com.example.applicationtest001.Adapter.RoomAdapter;
import com.example.applicationtest001.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomInfoActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        listView = findViewById(R.id.comment_list);
        List<Map<String, Object>> list = getData();
        listView.setAdapter(new CommentAdapter(this, list));

    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.ic_baseline_person_24);
            map.put("nickname", "hang");
            map.put("ctime", "jintian");
            map.put("commencontent","这个密室真的很好啊");
            list.add(map);
        }
        return list;
    }
}