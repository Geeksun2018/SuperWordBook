package com.geeksun.superwordbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.model.Word;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    private int resourceId;

    public WordAdapter(Context context, int textViewResourceId, List<Word> words){
        super(context,textViewResourceId,words);
        resourceId = textViewResourceId;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word word = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.description = view.findViewById(R.id.description);
            viewHolder.phoneticSymbol = view.findViewById(R.id.phoneticSymbol);
            viewHolder.wordContent = view.findViewById(R.id.wordContent);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.wordContent.setText(word.getWordContent());
        viewHolder.description.setText(word.getDescription());
        viewHolder.phoneticSymbol.setText(word.getPhoneticSymbol());
        return view;
    }

    class ViewHolder{
        TextView wordContent;
        TextView phoneticSymbol;
        TextView description;
    }
}
