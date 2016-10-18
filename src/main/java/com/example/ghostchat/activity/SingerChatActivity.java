package com.example.ghostchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ghostchat.R;

/**
 * Created by Administrator on 2016/10/15.
 */
public class SingerChatActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title_back,title_exit,title_text;
    EditText et_text;
    Button btn_sent;
    ListView lv_singer_chat;

    Intent mIntent;
    String mFriendName;
    String mContact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        initView();
        init();
        setListener();
    }

    private void setListener() {
        btn_sent.setOnClickListener(this);
    }

    private void init() {
        mIntent = getIntent();
        mFriendName = mIntent.getStringExtra("userName");
        title_text.setText(mFriendName);
    }

    private void initView() {
        title_back = (TextView) findViewById(R.id.title_back);
        title_text = (TextView) findViewById(R.id.title_text);
        title_exit = (TextView) findViewById(R.id.title_edit);
        title_exit.setVisibility(View.INVISIBLE);
        et_text = (EditText) findViewById(R.id.et_talk);
        btn_sent = (Button) findViewById(R.id.btn_sent);
        lv_singer_chat = (ListView) findViewById(R.id.lv_singer_chat);
    }

    private void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_singer_chat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sent:

                break;
            default:
                break;
        }
    }
}
