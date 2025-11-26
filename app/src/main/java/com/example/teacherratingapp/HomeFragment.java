package com.example.teacherratingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
            // 创建老师帖子列表的 Fragment
            Fragment teacherPostListFragment = new TeacherPostListFragment();

            // 获取 FragmentManager 并开始事务
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // 替换当前的 Fragment, 并添加到返回栈
            transaction.replace(R.id.fragment_container, teacherPostListFragment);
            transaction.addToBackStack(null); // 关键：这样才能通过返回键或返回箭头回到这里
            transaction.commit();
        });

        // 设置课程卡片的点击事件
        view.findViewById(R.id.card_courses).setOnClickListener(v -> {
            // TODO: 跳转到课程帖子列表页面
            Toast.makeText(getContext(), "跳转到课程类主页（待开发）", Toast.LENGTH_SHORT).show();
        });
    }
}
