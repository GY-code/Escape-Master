package com.example.applicationtest001.Class;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.applicationtest001.Tool.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Response;

public class Friend {


    public static User user=null;
    public static ArrayList<User> getFriendlist() {
        return friendlist;
    }
    public static String name;

    public static void setFriendlist(ArrayList<User> friendlist) {
        Friend.friendlist = friendlist;
    }

    private static ArrayList<User> friendlist;
    public static void getFriend(String phone_number)
    {

        OkHttpUtils.sendOkHttpRequest("http://192.168.43.72:8080/friend/getFriend/"+phone_number, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                System.out.println(data);
                ArrayList<User> arrayList=new ArrayList<>();
                List<User> list= JSON.parseArray(data,User.class);
                for(int i=0;i<list.size();i++)
                {
                    arrayList.add(list.get(i));
                }
                setFriendlist(arrayList);

            }
        });
    }
}
