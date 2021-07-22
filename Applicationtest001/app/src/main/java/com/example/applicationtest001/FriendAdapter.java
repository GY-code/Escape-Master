package com.example.applicationtest001;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lw on 2017/4/14.
 */

public class FriendAdapter extends ArrayAdapter{
    private final int resourceId;

    public FriendAdapter(Context context, int textViewResourceId, List<Friend> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend = (Friend) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);//获取该布局内的图片视图
        TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);//获取该布局内的文本视图
        TextView fruitDes = (TextView) view.findViewById(R.id.fruit_des);
        fruitImage.setImageResource(friend.getImageId());//为图片视图设置图片资源
        fruitName.setText(friend.getName());//为文本视图设置文本内容
        fruitDes.setText(friend.getDescribe());
        return view;
    }
}