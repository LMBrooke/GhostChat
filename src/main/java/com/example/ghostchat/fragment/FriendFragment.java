package com.example.ghostchat.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghostchat.activity.SingerChatActivity;
import com.example.ghostchat.model.Friends;
import com.example.ghostchat.view.LetterIndexView;
import com.example.ghostchat.R;
import com.example.ghostchat.adapter.FriendAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class FriendFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "FriendFragment";
    private static final int USERNAME = 1;
    private View view;
    private ListView lv_friend;
    private TextView tv_letter;
    private LetterIndexView mLetterIndexView;
    private List<Friends> mList;
    List<String> mUserNames;
    FriendAdapter mAdapter;

    Intent mIntent;
    Handler mHandler = new Handler(){

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what){
                case USERNAME:
                    for (String string : mUserNames) {
                        Friends friends = new Friends(string,R.mipmap.em_default_avatar);
                        Log.i(TAG,"进入选择"+(String) msg.obj);
                        String first = string.substring(0,1).toUpperCase();
                        if (first.matches("[A-Z]")) {
                            friends.setFirstLetter(first);
                        }else{
                            friends.setFirstLetter("#");
                        }
                        mList.add(friends);

                    }
                    mAdapter.notifyDataSetChanged();
                    Collections.sort(mList, new Comparator<Friends>() {
                        @Override
                        public int compare(Friends lhs, Friends rhs) {
                            if (lhs.getFirstLetter().contains("搜")){
                                return 1;
                            }else if (rhs.getFirstLetter().contains("搜")){
                                return 0;
                            } else if (lhs.getFirstLetter().contains("#")) {
                                return 1;
                            } else if (rhs.getFirstLetter().contains("#")) {
                                return -1;
                            }else{
                                return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                            }
                        }
                    });

                    break;

            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        lv_friend = (ListView) view.findViewById(R.id.lv_friend);
        tv_letter = (TextView) view.findViewById(R.id.show_letter);
        mLetterIndexView = (LetterIndexView) view.findViewById(R.id.liv_letter);
        mLetterIndexView.setTextViewDialog(tv_letter);
        setListener();
        mList = new ArrayList<>();
        initData();
        mAdapter = new FriendAdapter(getActivity(),mList);
        lv_friend.setAdapter(mAdapter);
        lv_friend.setOnItemClickListener(this);
        return view;
    }

    private void setListener() {
        mLetterIndexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = mAdapter.getPositionForSection(currentChar.charAt(0));
                lv_friend.setSelection(positionForSection);
            }
        });
        lv_friend.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int position = lv_friend.getFirstVisiblePosition();
                Friends friends = mAdapter.getItem(position);
                String firstLetter = friends.getFirstLetter();
                mLetterIndexView.updateLetterIndexView(firstLetter);
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int sectionForPosition = mAdapter.getSectionForPosition(firstVisibleItem);

            }
        });
    }


    public void initData() {
        Friends friend1 = new Friends("申请与通知",R.mipmap.em_new_friends_icon,"搜");
        Friends friend2 = new Friends("群聊",R.mipmap.em_groups_icon,"搜");
        Friends friend3 = new Friends("聊天室",R.mipmap.em_groups_icon,"搜");
        Friends friend4 = new Friends("环信小助手",R.mipmap.em_groups_icon,"搜");
        mList.add(friend1);
        mList.add(friend2);
        mList.add(friend3);
        mList.add(friend4);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    Log.i(TAG,"进入选择");
                    mUserNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.i(TAG,"进行的到数据");
                    if (mUserNames==null||mUserNames.size()==0){
                        Log.i(TAG,"为空");
                        return;
                    }
                    mHandler.obtainMessage(USERNAME).sendToTarget();
                    /*for (int i= 0;i<mUserNames.size();i++){
                        Message msg = new Message();
                        msg.what = USERNAME;
                        msg.obj = mUserNames.get(i);
                        mHandler.sendMessage(msg);
                    }*/

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String firstLetter = mList.get(position).getFirstLetter();
        String userName = mList.get(position).getName();
        if (TextUtils.isEmpty(firstLetter)){
            Log.i(TAG,"报错");
            return;
        }else if(firstLetter.equals("搜")){
            Log.i(TAG,"不一样的");
            Toast.makeText(getActivity(),userName,Toast.LENGTH_SHORT).show();
            return;
        }else {
            mIntent = new Intent(getActivity(), SingerChatActivity.class);
            mIntent.putExtra("userName",userName);
            startActivity(mIntent);
        }
    }
}
