package com.geeksun.superwordbook.ui.gallery;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.adapter.HomeWordAdapter;
import com.geeksun.superwordbook.adapter.WordAdapter;
import com.geeksun.superwordbook.model.Word;
import com.geeksun.superwordbook.ui.home.HomeFragment;
import com.geeksun.superwordbook.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private List<Word> words;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Handler handler = new Handler();
        HttpUtil.sendOkHttpRequest("http://47.107.108.67:8080/getWordBook?uid=1", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //给用户显示网络异常！
                e.printStackTrace();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObject = JSON.parseObject(json);
                words = jsonObject.getJSONObject("data").getJSONArray("words").toJavaList(Word.class);
                handler.post(runnableUi);
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        return root;
    }

    Runnable runnableUi = new  Runnable(){
        @Override
        public void run() {
            //更新界面
            WordAdapter wordAdapter = new WordAdapter(getActivity(),R.layout.word_item,words);
            ListView listView = getActivity().findViewById(R.id.list_view);
            listView.setAdapter(wordAdapter);
        }

    };
}