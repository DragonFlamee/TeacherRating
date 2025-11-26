package com.example.teacherratingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddPostFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnSubmitPost = view.findViewById(R.id.btnSubmitPost);
        btnSubmitPost.setOnClickListener(v -> {
            // TODO: 获取输入框内容并执行发布逻辑
            Toast.makeText(getContext(), "帖子发布成功（虚拟）", Toast.LENGTH_SHORT).show();
            
            // 发布成功后可以切换回主页
            // getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        });
    }
}
