package com.androidcodr.mvvmcalculator.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcodr.mvvmcalculator.R;
import com.androidcodr.mvvmcalculator.model.HistoryModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<HistoryModel> userArrayList;

    public RecyclerViewAdapter(Activity context, ArrayList<HistoryModel> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryModel user = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;
        viewHolder.txtView_title.setText(user.getCalculation());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView txtView_title;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView_title = itemView.findViewById(R.id.txtView_title);


        }
    }
}