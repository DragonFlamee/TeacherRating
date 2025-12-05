package com.example.teacherratingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stu.entity.Evaluation;
import java.util.List;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationViewHolder> {

    private final List<Evaluation> evaluationList;

    public EvaluationAdapter(List<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
    }

    @NonNull
    @Override
    public EvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation, parent, false);
        return new EvaluationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationViewHolder holder, int position) {
        Evaluation evaluation = evaluationList.get(position);
        holder.rbScore.setRating(evaluation.getScore().floatValue());
        holder.tvContent.setText(evaluation.getContext());
    }

    @Override
    public int getItemCount() {
        return evaluationList.size();
    }

    static class EvaluationViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        RatingBar rbScore;

        public EvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            rbScore = itemView.findViewById(R.id.rbScore);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}
