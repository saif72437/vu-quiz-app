package com.saif.vuquizapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class setsAdapter extends BaseAdapter {
    int Sets = 0;
    String Subjects;
//    Context context;
//
//    public setsAdapter(int sets, Context context) {
//        Sets = sets;
//        this.context = context;
//    }

    public setsAdapter(int sets, String Subjects) {
        this.Subjects = Subjects;
        Sets = sets;

    }

    @Override
    public int getCount() {
        return Sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sets_design,parent,false);
        }else {
            view = convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent = new Intent(parent.getContext(),Questions.class);
                questionIntent.putExtra("text", Subjects);
                questionIntent.putExtra("setNo", position+1);
                parent.getContext().startActivity(questionIntent);
            }
        });
        ((TextView)view.findViewById(R.id.sets_text)).setText(String.valueOf(position+1));
        return view;
//        convertView = LayoutInflater.from(context).inflate(R.layout.sets_design,parent,false);
//        TextView sets_text = convertView.findViewById(R.id.sets_text);
//        return convertView;
    }
}
