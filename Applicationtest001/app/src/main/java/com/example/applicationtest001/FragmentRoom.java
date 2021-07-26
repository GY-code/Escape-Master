package com.example.applicationtest001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentRoom extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room,container,false);
        listView = (ListView)view.findViewById(R.id.room_list_view);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new RoomAdapter(getActivity(), list));
        listView.setOnItemClickListener(this);
        return view;

    }

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
        Intent intent = new Intent(getActivity(), MatchActivity.class);
        startActivity(intent);
    }
}
