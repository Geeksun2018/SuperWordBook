package com.geeksun.superwordbook.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.adapter.WordAdapter;
import com.geeksun.superwordbook.model.Word;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private List<Word> words = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        words.add(new Word("Geek","[geek]","n.极客"));
        words.add(new Word("Hello","[halo]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        words.add(new Word("Geek","[geek]","n.极客"));
        words.add(new Word("Hello","[halo]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        words.add(new Word("Geek","[geek]","n.极客"));
        words.add(new Word("Hello","[halo]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        words.add(new Word("Geek","[geek]","n.极客"));
        words.add(new Word("Hello","[halo]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        words.add(new Word("Geek","[geek]","n.极客"));
        words.add(new Word("Hello","[halo]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        words.add(new Word("Geek","[geek]","n.极客"));
        words.add(new Word("Hello","[halo]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        words.add(new Word("Geek","[geek]","n.极客"));
        words.add(new Word("Hello","[halo]","n.你好"));
        words.add(new Word("H","[nihao]","n.你好"));
        WordAdapter wordAdapter = new WordAdapter(getActivity(),R.layout.word_item,words);
        ListView listView = getActivity().findViewById(R.id.list_view);
        listView.setAdapter(wordAdapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}