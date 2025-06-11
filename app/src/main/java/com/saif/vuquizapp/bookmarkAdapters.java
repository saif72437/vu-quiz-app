package com.saif.vuquizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class bookmarkAdapters extends RecyclerView.Adapter<bookmarkAdapters.ViewHolder> {
    List<questionsModel> list;
    Context context;
    int lastPosition = -1;

    public bookmarkAdapters(List<questionsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public bookmarkAdapters(List<questionsModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(list.get(position).getQuestion(),list.get(position).getCorrectOption(), position);
        setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView question, answer;
        private ImageButton delete_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
        private void setData(String question, String answer, int position){
            this.question.setText(question);
            this.answer.setText(answer);
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    public void setAnimation(View view, int position){
        if(position>lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.recycler_view_animation);
            view.setAnimation(animation);
            lastPosition = position;
        }
    }
}
