package com.example.applicationtest001;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentPeople extends Fragment {
    private List<Friend> friendList = new ArrayList<Friend>();
    @Nullable
//    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
//            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_people,container,false);
        getActivity().setContentView(R.layout.fragment_people);
        initFruits(); // 初始化水果数据
        FriendAdapter adapter = new FriendAdapter(this.getContext(), R.layout.friend_item, friendList);
        ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        return v;
    }

    private void initFruits() {
        Friend apple = new Friend("Apple","pingguo1111111111111", R.drawable.weixin);
        friendList.add(apple);
        Friend banana = new Friend("Banana","pingguo", R.drawable.weixin);
        friendList.add(banana);
        Friend orange = new Friend("Orange", "pingguo",R.drawable.weixin);
        friendList.add(orange);
        Friend watermelon = new Friend("Watermelon","pingguo", R.drawable.weixin);
        friendList.add(watermelon);
        Friend pear = new Friend("Pear", "pingguo",R.drawable.weixin);
        friendList.add(pear);
        Friend grape = new Friend("Grape","pingguo", R.drawable.weixin);
        friendList.add(grape);
        Friend pineapple = new Friend("Pineapple","pingguo", R.drawable.weixin);
        friendList.add(pineapple);
        Friend strawberry = new Friend("Strawberry","pingguo", R.drawable.weixin);
        friendList.add(strawberry);
        Friend cherry = new Friend("Cherry", "pingguo",R.drawable.weixin);
        friendList.add(cherry);
        Friend mango = new Friend("Mango","pingguo", R.drawable.weixin);
        friendList.add(mango);
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
}
