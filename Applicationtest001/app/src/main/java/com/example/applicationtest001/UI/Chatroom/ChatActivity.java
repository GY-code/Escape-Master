package com.example.applicationtest001.UI.Chatroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.applicationtest001.Class.Msg;
import com.example.applicationtest001.Adapter.MsgAdapter;
import com.example.applicationtest001.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;

    private List<Msg> msgList = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_chat);

        initMsgs();
        adapter = new MsgAdapter(ChatActivity.this, R.layout.msg_item, msgList);
        inputText = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send);
        msgListView = (ListView)findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello, how are you?", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Fine, thank you, and you?", Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3 = new Msg("I am fine, too!", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}