package com.example.teacherratingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stu.entity.Teacher;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private final List<Teacher> teacherList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Teacher teacher);
    }

    public TeacherAdapter(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        holder.tvTeacherName.setText(teacher.getName());
        holder.tvTeacherTitle.setText(teacher.getTitle());
        holder.tvTeacherDepartment.setText(teacher.getDepartment());
        holder.tvTeacherResearchArea.setText(teacher.getResearchArea());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(teacher);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeacherName, tvTeacherTitle, tvTeacherDepartment, tvTeacherResearchArea;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvTeacherTitle = itemView.findViewById(R.id.tvTeacherTitle);
            tvTeacherDepartment = itemView.findViewById(R.id.tvTeacherDepartment);
            tvTeacherResearchArea = itemView.findViewById(R.id.tvTeacherResearchArea);
        }
    }
}
