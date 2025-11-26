package com.example.teacherratingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载这个Fragment的布局
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 设置老师卡片的点击事件
        view.findViewById(R.id.card_teachers).setOnClickListener(v -> {
            // TODO: 跳转到老师帖子列表页面 (Page 4)
            // Intent intent = new Intent(getActivity(), TeacherPostListActivity.class);
            // startActivity(intent);
            Toast.makeText(getContext(), "跳转到老师类主页（待开发）", Toast.LENGTH_SHORT).show();
        });

        // 设置课程卡片的点击事件
        view.findViewById(R.id.card_courses).setOnClickListener(v -> {
            // TODO: 跳转到课程帖子列表页面
            Toast.makeText(getContext(), "跳转到课程类主M页（待开发）", Toast.LENGTH_SHORT).show();
        });
    }
}
