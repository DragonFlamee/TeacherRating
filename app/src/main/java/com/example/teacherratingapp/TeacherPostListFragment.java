package com.example.teacherratingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class TeacherPostListFragment extends Fragment {

    private TeacherPostAdapter adapter;
    private List<TeacherPost> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_post_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            // 返回到上一个 Fragment (HomeFragment)
            getParentFragmentManager().popBackStack();
        });

        RecyclerView rvTeacherPosts = view.findViewById(R.id.rvTeacherPosts);
        rvTeacherPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        // 1. 创建虚拟数据
        createDummyData();

        // 2. 创建并设置适配器
        adapter = new TeacherPostAdapter(postList);
        rvTeacherPosts.setAdapter(adapter);

        // 3. 设置点击事件监听器
        adapter.setOnItemClickListener(post -> {
            // 当一个帖子被点击时，跳转到详情页
            Fragment detailFragment = new TeacherDetailFragment();

            // 您可以在这里通过 Bundle 传递数据到详情页
            // Bundle args = new Bundle();
            // args.putString("teacherName", post.getName());
            // detailFragment.setArguments(args);

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null); // 添加到返回栈以便可以返回
            transaction.commit();
        });
    }

    private void createDummyData() {
        postList = new ArrayList<>();
        postList.add(new TeacherPost("张三", "计算机学院", 4.7f));
        postList.add(new TeacherPost("李四", "外国语学院", 4.2f));
        postList.add(new TeacherPost("王五", "物理学院", 4.9f));
    }
}
