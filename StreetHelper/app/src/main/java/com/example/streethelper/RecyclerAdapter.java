package com.example.streethelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.StreetProblemViewHolder>{

    private List<String> list;

    public RecyclerAdapter(List<String> list)
    {
        this.list = list;
    }
    @NonNull
    @Override
    public StreetProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_layout,parent,false);
        StreetProblemViewHolder streetProblemViewHolder = new StreetProblemViewHolder(textView);

        return streetProblemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StreetProblemViewHolder holder, int position) {
        holder.VersionName.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class StreetProblemViewHolder extends RecyclerView.ViewHolder {

        TextView VersionName;
        public StreetProblemViewHolder(@NonNull TextView itemView) {
            super(itemView);
            VersionName = itemView;
        }
    }
}
