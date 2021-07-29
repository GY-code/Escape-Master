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

public class CommentAdapter extends BaseAdapter {
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;


    public CommentAdapter(Context context, List<Map<String, Object>> data) {

        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public final class Comment{
        public ImageView image;
        public TextView nickname;
        public TextView ctime;
        public TextView commentcontent;
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
        Comment comment=null;
        if(convertView==null){
            comment=new Comment();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.comment_item, null);
            comment.image=(ImageView)convertView.findViewById(R.id.userimage);
            comment.nickname = (TextView)convertView.findViewById(R.id.nickname);
            comment.ctime=(TextView)convertView.findViewById(R.id.commenttime);
            comment.commentcontent=(TextView)convertView.findViewById(R.id.comment);
            convertView.setTag(comment);

        }else{
            comment=(Comment)convertView.getTag();
        }
        //绑定数据
        comment.image.setBackgroundResource((Integer)data.get(position).get("image"));
        comment.nickname.setText((String)data.get(position).get("nickname"));
        comment.ctime.setText((String)data.get(position).get("ctime"));
        comment.commentcontent.setText((String)data.get(position).get("commencontent"));

        return convertView;
    }
}
