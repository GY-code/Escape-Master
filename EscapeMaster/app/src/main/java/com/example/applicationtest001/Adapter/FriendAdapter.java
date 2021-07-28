package com.example.applicationtest001.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applicationtest001.R;

import java.util.List;
import java.util.Map;

/**
 * Created by lw on 2017/4/14.
 */

public class FriendAdapter extends BaseAdapter{
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public FriendAdapter(Context context, List<Map<String, Object>> data) {

        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public class Friend{
        public ImageView image;
        public TextView title;

        public TextView info;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend=null;
        if(convertView==null){
            friend=new Friend();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.friend_item, null);
            friend.image=(ImageView)convertView.findViewById(R.id.image);
            friend.title=(TextView)convertView.findViewById(R.id.title);
            friend.info=(TextView)convertView.findViewById(R.id.info);
            convertView.setTag(friend);
        }else{
            friend=(Friend)convertView.getTag();
        }
        //绑定数据
        friend.image.setBackgroundResource((Integer)data.get(position).get("image"));
        friend.title.setText((String)data.get(position).get("title"));
        friend.info.setText((String)data.get(position).get("info"));
        return convertView;
    }

}