package com.example.teacherratingapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.stu.entity.Evaluation;
import com.stu.entity.Teacher;
import com.stu.mapper.impl.EvaluationMapperImpl;
import com.stu.mapper.impl.TeacherMapperImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeacherDetailFragment extends Fragment {

    private TextView tvTeacherName, tvTeacherTitle, tvTeacherDepartment, tvTeacherResearchArea;
    private RatingBar rbAverageRating;
    private RecyclerView rvComments;
    private EvaluationAdapter evaluationAdapter;
    private TeacherMapperImpl teacherMapper;
    private EvaluationMapperImpl evaluationMapper;
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private long teacherId;
    private Teacher currentTeacher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            teacherId = getArguments().getLong("teacherId", -1);
        }

        if (teacherId == -1) {
            Toast.makeText(getContext(), "无法加载教师信息", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
            return;
        }

        initViews(view);
        setupToolbar(view);
        loadTeacherDetails();

        FloatingActionButton fabAddComment = view.findViewById(R.id.fabAddComment);
        fabAddComment.setOnClickListener(v -> showAddEvaluationDialog());
    }

    private void initViews(View view) {
        tvTeacherName = view.findViewById(R.id.tvTeacherName);
        tvTeacherTitle = view.findViewById(R.id.tvTeacherTitle);
        tvTeacherDepartment = view.findViewById(R.id.tvTeacherDepartment);
        tvTeacherResearchArea = view.findViewById(R.id.tvTeacherResearchArea);
        rbAverageRating = view.findViewById(R.id.rbAverageRating);
        rvComments = view.findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupToolbar(View view) {
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());
    }

    private void loadTeacherDetails() {
        databaseExecutor.execute(() -> {
            teacherMapper = new TeacherMapperImpl();
            evaluationMapper = new EvaluationMapperImpl();

            currentTeacher = teacherMapper.getByTeacherId(teacherId);
            List<Evaluation> evaluations = evaluationMapper.getByTeacherId(teacherId);

            mainThreadHandler.post(() -> {
                if (currentTeacher != null) {
                    tvTeacherName.setText(currentTeacher.getName());
                    tvTeacherTitle.setText(currentTeacher.getTitle());
                    tvTeacherDepartment.setText(currentTeacher.getDepartment());
                    tvTeacherResearchArea.setText(currentTeacher.getResearchArea());
                    updateEvaluations(evaluations);
                } else {
                    Toast.makeText(getContext(), "未找到教师信息", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void showAddEvaluationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_evaluation, null);

        final RatingBar dialogRatingBar = dialogView.findViewById(R.id.dialog_rating_bar);
        final TextInputEditText etComment = dialogView.findViewById(R.id.etComment);

        builder.setView(dialogView)
                .setPositiveButton("提交", (dialog, id) -> {
                    float rating = dialogRatingBar.getRating();
                    String comment = etComment.getText().toString().trim();
                    if (rating == 0) {
                        Toast.makeText(getContext(), "请至少选择一个评分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    submitEvaluation(rating, comment);
                })
                .setNegativeButton("取消", (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void submitEvaluation(double score, String content) {
        long studentId = requireActivity().getIntent().getLongExtra("userId", -1);
        if (studentId == -1) {
            Toast.makeText(getContext(), "无法获取用户信息，请重新登录", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseExecutor.execute(() -> {
            try {
                Evaluation newEval = new Evaluation(null, studentId, teacherId, score, content, new Timestamp(System.currentTimeMillis()));
                evaluationMapper.insert(newEval);

                // Reload evaluations
                List<Evaluation> updatedEvaluations = evaluationMapper.getByTeacherId(teacherId);
                mainThreadHandler.post(() -> {
                    Toast.makeText(getContext(), "评价成功", Toast.LENGTH_SHORT).show();
                    updateEvaluations(updatedEvaluations);
                });

            } catch (Exception e) {
                e.printStackTrace();
                mainThreadHandler.post(() -> Toast.makeText(getContext(), "评价失败，请检查日志", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void updateEvaluations(List<Evaluation> evaluations) {
        evaluationAdapter = new EvaluationAdapter(evaluations);
        rvComments.setAdapter(evaluationAdapter);
        calculateAndSetAverageRating(evaluations);
    }

    private void calculateAndSetAverageRating(List<Evaluation> evaluations) {
        if (evaluations == null || evaluations.isEmpty()) {
            rbAverageRating.setRating(0);
            return;
        }
        double sum = 0;
        for (Evaluation e : evaluations) {
            sum += e.getScore();
        }
        float average = (float) (sum / evaluations.size());
        rbAverageRating.setRating(average);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseExecutor.execute(() -> {
            if (teacherMapper != null) {
                teacherMapper.close();
            }
            if (evaluationMapper != null) {
                evaluationMapper.close();
            }
        });
        databaseExecutor.shutdown();
    }
}
