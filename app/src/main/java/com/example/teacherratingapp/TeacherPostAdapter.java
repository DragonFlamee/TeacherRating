package com.example.teacherratingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherPostAdapter extends RecyclerView.Adapter<TeacherPostAdapter.PostViewHolder> {

    private final List<TeacherPost> posts;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TeacherPost post);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public TeacherPostAdapter(List<TeacherPost> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        TeacherPost post = posts.get(position);
        holder.tvTeacherName.setText(post.getName());
        holder.tvTeacherCollege.setText(post.getCollege());
        holder.ratingBar.setRating(post.getRating());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeacherName;
        TextView tvTeacherCollege;
        RatingBar ratingBar;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvTeacherCollege = itemView.findViewById(R.id.tvTeacherCollege);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
