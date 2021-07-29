package com.example.applicationtest001.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.applicationtest001.Adapter.RoomAdapter;
import com.example.applicationtest001.UI.Function.MatchActivity;
import com.example.applicationtest001.R;
import com.example.applicationtest001.UI.Function.RoomInfoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentRoom extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private SearchView searchView;
    private RoomAdapter roomAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room,container,false);
        //searchView = (SearchView) view.findViewById(R.id.search_view);
        listView = (ListView)view.findViewById(R.id.room_list_view);
        List<Map<String, Object>> list=getData();
        roomAdapter=new RoomAdapter(getActivity(), list);
        listView.setAdapter(roomAdapter);
        listView.setOnItemClickListener(this);
        return view;

    }

//    public void onStart() {
//        super.onStart();
//
//        //设置SearchView自动缩小为图标
//        searchView.setIconifiedByDefault(true);//设为true则搜索栏 缩小成俄日一个图标点击展开
//        //设置该SearchView显示搜索按钮
//        searchView.setSubmitButtonEnabled(true);
//        //设置默认提示文字
//        searchView.setQueryHint("输入您想查找的内容");
//    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.earth);
            map.put("title", "密室"+i);
            map.put("info", "密室信息" + i);
            list.add(map);
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), RoomInfoActivity.class);
        startActivity(intent);
    }
}
