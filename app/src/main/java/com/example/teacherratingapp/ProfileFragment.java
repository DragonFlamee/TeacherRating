package com.example.teacherratingapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stu.entity.User;
import com.stu.mapper.impl.UserMapperImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {

    private TextView tvUsername;
    private UserMapperImpl userMapper;
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tvUsername);

        loadUserData();

        // 设置“我的收藏”的点击事件
        view.findViewById(R.id.card_favorites).setOnClickListener(v -> {
            // TODO: 跳转到收藏列表页面
            Toast.makeText(getContext(), "跳转到我的收藏（待开发）", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserData() {
        long userId = requireActivity().getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(getContext(), "无法获取用户信息", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseExecutor.execute(() -> {
            userMapper = new UserMapperImpl();
            User user = userMapper.getById(userId);
            mainThreadHandler.post(() -> {
                if (user != null) {
                    tvUsername.setText(user.getNickname());
                } else {
                    tvUsername.setText("用户不存在");
                }
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseExecutor.execute(() -> {
            if (userMapper != null) {
                userMapper.close();
            }
        });
        databaseExecutor.shutdown();
    }
}
