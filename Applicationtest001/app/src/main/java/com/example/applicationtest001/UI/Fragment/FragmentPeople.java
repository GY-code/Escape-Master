package com.example.applicationtest001.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.applicationtest001.Adapter.FriendAdapter;
import com.example.applicationtest001.R;
import com.example.applicationtest001.UI.Chatroom.FChatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentPeople extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_people , container, false);
        listView = (ListView)view.findViewById(R.id.list_view);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new FriendAdapter(getActivity(), list));
        listView.setOnItemClickListener(this);
        return view;
    }


    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.weixin);
            map.put("title", "这是一个标题"+i);
            map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(), FChatActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
}
