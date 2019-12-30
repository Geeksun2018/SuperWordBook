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
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView wordContent = view.findViewById(R.id.wordContent);
        TextView phoneticSymbol = view.findViewById(R.id.phoneticSymbol);
        TextView description = view.findViewById(R.id.description);
        wordContent.setText(word.getWordContent());
        description.setText(word.getDescription());
        phoneticSymbol.setText(word.getPhoneticSymbol());
        return view;
    }
}
