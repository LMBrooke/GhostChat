package com.example.ghostchat.activity;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.ghostchat.R;
import com.example.ghostchat.fragment.DefaultFragment;
import com.example.ghostchat.fragment.FriendFragment;
import com.example.ghostchat.fragment.SettingFragment;
import com.hyphenate.chat.EMClient;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int USERNAME = 1;
    TextView tv_back,tv_exit,tv_text;
    /*LinearLayout ll_talk,ll_set,ll_friend;*/
    TextView tv_talk,tv_set,tv_friend;
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        initView();
        setListener();
    }

    private void setListener() {
        tv_friend.setOnClickListener(this);
        tv_talk.setOnClickListener(this);
        tv_set.setOnClickListener(this);
    }

    private void initView() {
        tv_text = (TextView) findViewById(R.id.title_text);
        tv_back = (TextView) findViewById(R.id.title_back);
        tv_back.setVisibility(View.INVISIBLE);
        tv_exit = (TextView) findViewById(R.id.title_edit);
        tv_exit.setVisibility(View.INVISIBLE);
        tv_friend = (TextView) findViewById(R.id.tv_friend);
        tv_talk = (TextView) findViewById(R.id.tv_talk);
        tv_set = (TextView) findViewById(R.id.tv_set);
        DefaultFragment fragment2 = new DefaultFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_layout, fragment2);
        transaction.commit();
        tv_talk.setBackgroundColor(Color.GRAY);
        ((TextView)findViewById(R.id.tv_talk)).setTextColor(Color.GREEN);
        tv_talk.setSelected(true);
        tv_text.setText("会话");

    }

    private void setView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        initTextView();
        Log.i(TAG,"来了");
        switch (v.getId()){
            case R.id.tv_friend:
                Log.i(TAG,"来了friend");
                friend();
                break;
            case R.id.tv_set:
                Log.i(TAG,"来了set");
                setting();
                break;
            case R.id.tv_talk:
                Log.i(TAG,"来了aaa");
                talk();
                break;
            default:
                break;
        }
    }

    private void initTextView() {
        tv_talk.setSelected(false);
        tv_set.setSelected(false);
        tv_friend.setSelected(false);
        tv_talk.setBackgroundColor(Color.BLACK);
        tv_friend.setBackgroundColor(Color.BLACK);
        tv_set.setBackgroundColor(Color.BLACK);
        tv_friend.setTextColor(Color.WHITE);
        tv_talk.setTextColor(Color.WHITE);
        tv_set.setTextColor(Color.WHITE);
    }

    private void talk() {
        tv_talk.setSelected(true);
        tv_talk.setBackgroundColor(Color.GRAY);
        tv_talk.setTextColor(Color.GREEN);
        DefaultFragment fragment2 = new DefaultFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, fragment2);
        transaction.commit();
        tv_exit.setVisibility(View.INVISIBLE);
        tv_text.setText("会话");
    }

    private void setting() {
        tv_set.setSelected(true);
        tv_set.setBackgroundColor(Color.GRAY);
        tv_set.setTextColor(Color.GREEN);
        SettingFragment fragment1 = new SettingFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, fragment1);
        transaction.commit();
        tv_exit.setVisibility(View.INVISIBLE);
        tv_text.setText("设置");
    }

    private void friend() {
        tv_friend.setSelected(true);
        tv_friend.setBackgroundColor(Color.GRAY);
        tv_friend.setTextColor(Color.GREEN);
        FriendFragment fragment = new FriendFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, fragment);
        transaction.commit();
        tv_exit.setText("");
        SpannableString ss = new SpannableString("ab");
        Drawable d = getResources().getDrawable(R.mipmap.em_add);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);//这里的参数很重要
        ss.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_exit.setText(ss);
        tv_exit.setTextSize(50);
        tv_exit.setGravity(Gravity.CENTER);
        tv_exit.setVisibility(View.VISIBLE);
        tv_text.setText("通讯录");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().logout(true);
    }
}
