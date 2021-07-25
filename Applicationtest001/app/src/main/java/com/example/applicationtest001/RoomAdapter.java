package com.example.applicationtest001;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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

    public final class Zujian{
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
        Zujian zujian=null;
        if(convertView==null){
            zujian=new Zujian();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.room_item, null);
            zujian.image=(ImageView)convertView.findViewById(R.id.roomimage);
            zujian.title= (TextView)convertView.findViewById(R.id.roomtitle);
            zujian.info=(TextView)convertView.findViewById(R.id.roominfo);
            convertView.setTag(zujian);

        }else{
            zujian=(Zujian)convertView.getTag();
        }
        //绑定数据
        zujian.image.setBackgroundResource((Integer)data.get(position).get("image"));
        zujian.title.setText((String)data.get(position).get("title"));
        zujian.info.setText((String)data.get(position).get("info"));


        return convertView;
    }
}
