package com.example.teacherratingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;

public class TeacherDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            // 返回到上一个 Fragment (TeacherPostListFragment)
            getParentFragmentManager().popBackStack();
        });

        // TODO: 接收从列表页传递过来的数据，并更新UI
        // TODO: 设置评论区的 RecyclerView
        // TODO: 实现点赞、评论、收藏等按钮的点击逻辑
    }
}
