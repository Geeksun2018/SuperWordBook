package com.geeksun.superwordbook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geeksun.superwordbook.R;

import java.util.List;

public class HomeWordAdapter extends RecyclerView.Adapter<HomeWordAdapter.ViewHolder> {

    private List<String> explains;

    public HomeWordAdapter(List<String> explains){
        this.explains = explains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String explain = explains.get(position);
        holder.textView.setText(explain);
    }

    @Override
    public int getItemCount() {
        return explains.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.home_list_item_txt);
        }


    }


}
