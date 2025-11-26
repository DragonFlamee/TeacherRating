package com.example.teacherratingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 设置“我的收藏”的点击事件
        view.findViewById(R.id.card_favorites).setOnClickListener(v -> {
            // TODO: 跳转到收藏列表页面
            Toast.makeText(getContext(), "跳转到我的收藏（待开发）", Toast.LENGTH_SHORT).show();
        });
    }
}
