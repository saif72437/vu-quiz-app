package com.saif.vuquizapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class subjectsAdapters extends RecyclerView.Adapter<subjectsAdapters.ViewHolder> {
ArrayList<model> list;
Context context;
int lastPostion = -1;
    public subjectsAdapters(ArrayList<model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(list.get(position).getUrl(), list.get(position).getName(), list.get(position).getSets());
        setAnimation(holder.itemView, position);
        //    model m = list.get(position);
//    holder.image.setImageResource(m.getImage());
//    holder.text.setText(m.getText());
//    holder.itemView.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(context, Sets.class);
//            context.startActivity(intent);
//        }
//    });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.subject_text);
            image = itemView.findViewById(R.id.suject_profile);

        }
        public void setData(String url, String text, final int sets){
            Glide.with(itemView.getContext()).load(url).into(image);
            this.text.setText(text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Sets.class);
                    intent.putExtra("sets", sets);
                    intent.putExtra("text", text);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

    }
   public void setAnimation(View view, int position){
        if(position>lastPostion) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.recycler_view_animation);
            view.startAnimation(animation);
        lastPostion = position;
        }
   }
}
