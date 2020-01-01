package com.geeksun.superwordbook.ui.home;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.adapter.HomeWordAdapter;
import com.geeksun.superwordbook.adapter.WordAdapter;
import com.geeksun.superwordbook.config.AppConfig;
import com.geeksun.superwordbook.util.HttpUtil;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private HomeViewModel homeViewModel;
    private Button translate;
    private EditText input;
    private TextView wordContentItem;
    private TextView home_american_voice;
    private TextView home_english_voice;
    private Button home_american_radio;
    private Button home_english_radio;

    private String englishVoiceUrl;
    private String americanVoiceUrl;
    private String home_english_voice_str;
    private String wordContentItemStr;
    private String home_american_voice_str;

    private List<String> explains;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        input = getActivity().findViewById(R.id.getInput);
        wordContentItem = getActivity().findViewById(R.id.home_content);
        translate = getActivity().findViewById(R.id.translate);
        home_american_voice = getActivity().findViewById(R.id.home_american_voice);
        home_english_voice = getActivity().findViewById(R.id.home_english_voice);
        home_american_radio = getActivity().findViewById(R.id.home_american_radio);
        home_english_radio = getActivity().findViewById(R.id.home_english_radio);
        final Handler handler = new Handler();
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = input.getText().toString();
                if (word.split(" ").length > 1){
                    //这是一个句子，应该翻译成中文。
                }else {
                    HttpUtil.sendOkHttpRequest(AppConfig.ip +"/translateWord?uid=1&q=" + word, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //给用户显示网络异常！
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = response.body().string();
                            JSONObject jsonObject = JSON.parseObject(json);
//                            Log.d("Geek", "onResponse: " +json );
                            wordContentItemStr = jsonObject.getJSONObject("data").getJSONObject("wordItem").get("wordContent").toString();
                            home_american_voice_str = jsonObject.getJSONObject("data").getJSONObject("wordItem").get("englishSignal").toString();
                            home_english_voice_str = jsonObject.getJSONObject("data").getJSONObject("wordItem").get("americanSignal").toString();
                            englishVoiceUrl = jsonObject.getJSONObject("data").getJSONObject("wordItem").get("englishVoice").toString();
                            americanVoiceUrl = jsonObject.getJSONObject("data").getJSONObject("wordItem").get("americanVoice").toString();
                            explains = jsonObject.getJSONObject("data").getJSONObject("wordItem").getJSONArray("explains").toJavaList(String.class);
                            handler.post(runnableUi);
                        }
                    });
                }
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    Runnable runnableUi = new  Runnable(){
        @Override
        public void run() {
            //更新界面
            wordContentItem.setText(wordContentItemStr);
            home_american_voice.setText(home_american_voice_str);
            home_english_voice.setText(home_english_voice_str);
            home_american_radio.setOnClickListener(HomeFragment.this);
            home_english_radio.setOnClickListener(HomeFragment.this);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            HomeWordAdapter homeWordAdapter = new HomeWordAdapter(explains);
            RecyclerView recyclerView = getActivity().findViewById(R.id.include_word_item_list);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(homeWordAdapter);
        }

    };

    @Override
    public void onClick(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer();;
        if (view.getId() == R.id.home_english_radio){
            try {

                mediaPlayer.setDataSource(americanVoiceUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }else{
            try {
                mediaPlayer.setDataSource(englishVoiceUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }
}