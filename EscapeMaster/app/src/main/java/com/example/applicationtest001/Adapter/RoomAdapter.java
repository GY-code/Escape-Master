package com.example.applicationtest001.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.applicationtest001.R;

import java.util.List;
import java.util.Map;

public class RoomAdapter extends BaseAdapter {


    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;


    public RoomAdapter(Context context, List<Map<String, Object>> data) {

        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public class Room{
        public ImageView image;
        public TextView title;
        public TextView info;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Room room=null;
        if(convertView==null){
            room=new Room();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.room_item, null);
            room.image=(ImageView)convertView.findViewById(R.id.roomimage);
            room.title= (TextView)convertView.findViewById(R.id.roomtitle);
            room.info=(TextView)convertView.findViewById(R.id.roominfo);
            convertView.setTag(room);

        }else{
            room=(Room)convertView.getTag();
        }
        //绑定数据
        room.image.setBackgroundResource((Integer)data.get(position).get("image"));
        room.title.setText((String)data.get(position).get("title"));
        room.info.setText((String)data.get(position).get("info"));


        return convertView;
    }
}
