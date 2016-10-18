package com.example.ghostchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.ghostchat.model.Friends;
import com.example.ghostchat.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class FriendAdapter extends BaseAdapter implements SectionIndexer{

    List<Friends> mList;
    Context mContext;

    public FriendAdapter(Context context,List<Friends> mList) {
        this.mList = mList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Friends getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friends friend = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.friend_item, null);
            viewHolder = new ViewHolder();
            viewHolder.friendImage = (ImageView) view.findViewById(R.id.friend_image);
            viewHolder.friendName = (TextView) view.findViewById(R.id.friend_name);
            viewHolder.fristLetter = (TextView) view.findViewById(R.id.tv_letter);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.friendImage.setImageResource(friend.getImageId());
        viewHolder.friendName.setText(friend.getName());

        //获得当前position是属于哪个分组
        int sectionForPosition = getSectionForPosition(position);
        //获得该分组第一项的position
        int positionForSection = getPositionForSection(sectionForPosition);
        //查看当前position是不是当前item所在分组的第一个item
        //如果是，则显示showLetter，否则隐藏
        if (position == positionForSection) {
            if (friend.getFirstLetter().equals("搜")){
                viewHolder.fristLetter.setVisibility(View.GONE);
            }else {
                viewHolder.fristLetter.setVisibility(View.VISIBLE);
                viewHolder.fristLetter.setText(friend.getFirstLetter());
            }
        } else {
            viewHolder.fristLetter.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        String firstLetter = mList.get(position).getFirstLetter();
        if (firstLetter.equals("搜")){
            return 0;
        }else {
            return firstLetter.charAt(0);
        }
    }

    class ViewHolder {
        ImageView friendImage;
        TextView friendName;
        TextView fristLetter;
    }

}
