package com.example.ghostchat.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghostchat.R;

/**
 * Created by Administrator on 2016/10/11.
 */
public class DefaultFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talk, container, false);
        return view;
    }
}