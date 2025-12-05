package com.example.teacherratingapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.stu.entity.Teacher;
import com.stu.mapper.impl.TeacherMapperImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddPostFragment extends Fragment {

    private TextInputEditText etTeacherName, etTitle, etDepartment, etResearchArea;
    private Button btnSubmitTeacher;
    private TeacherMapperImpl teacherMapper;

    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        databaseExecutor.execute(() -> teacherMapper = new TeacherMapperImpl());

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        btnSubmitTeacher.setOnClickListener(v -> {
            submitTeacher();
        });
    }

    private void initViews(View view) {
        etTeacherName = view.findViewById(R.id.etTeacherName);
        etTitle = view.findViewById(R.id.etTitle);
        etDepartment = view.findViewById(R.id.etDepartment);
        etResearchArea = view.findViewById(R.id.etResearchArea);
        btnSubmitTeacher = view.findViewById(R.id.btnSubmitTeacher);
    }

    private void submitTeacher() {
        String name = etTeacherName.getText().toString().trim();
        String title = etTitle.getText().toString().trim();
        String department = etDepartment.getText().toString().trim();
        String researchArea = etResearchArea.getText().toString().trim();

        if (name.isEmpty() || department.isEmpty()) {
            Toast.makeText(getContext(), "教师姓名和所属院系为必填项", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseExecutor.execute(() -> {
            try {
                // Check if teacher already exists
                if (teacherMapper.getByName(name) != null) {
                    mainThreadHandler.post(() -> Toast.makeText(getContext(), "该教师已存在", Toast.LENGTH_SHORT).show());
                    return;
                }

                Teacher newTeacher = new Teacher(null, name, title, department, researchArea);
                teacherMapper.insert(newTeacher);

                mainThreadHandler.post(() -> {
                    Toast.makeText(getContext(), "教师添加成功", Toast.LENGTH_SHORT).show();
                    // Navigate back to the previous screen
                    getParentFragmentManager().popBackStack();
                });

            } catch (Exception e) {
                e.printStackTrace();
                mainThreadHandler.post(() -> Toast.makeText(getContext(), "添加失败，请检查日志", Toast.LENGTH_SHORT).show());
            }
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
