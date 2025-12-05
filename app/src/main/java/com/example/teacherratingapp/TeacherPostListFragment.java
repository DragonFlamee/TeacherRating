package com.example.teacherratingapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.stu.entity.Teacher;
import com.stu.mapper.impl.TeacherMapperImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeacherPostListFragment extends Fragment {

    private TeacherAdapter adapter;
    private TeacherMapperImpl teacherMapper;
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_post_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        RecyclerView rvTeacherPosts = view.findViewById(R.id.rvTeacherPosts);
        rvTeacherPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseExecutor.execute(() -> {
            teacherMapper = new TeacherMapperImpl();
            List<Teacher> teacherList = teacherMapper.getAllTeachers(); // You need to implement this method

            mainThreadHandler.post(() -> {
                adapter = new TeacherAdapter(teacherList);
                rvTeacherPosts.setAdapter(adapter);

                adapter.setOnItemClickListener(teacher -> {
                    Fragment detailFragment = new TeacherDetailFragment();
                    Bundle args = new Bundle();
                    args.putLong("teacherId", teacher.getId());
                    detailFragment.setArguments(args);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_container, detailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseExecutor.execute(() -> {
            if (teacherMapper != null) {
                teacherMapper.close();
            }
        });
        databaseExecutor.shutdown();
    }
}
