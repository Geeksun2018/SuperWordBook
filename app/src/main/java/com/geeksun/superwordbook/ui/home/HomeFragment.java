package com.geeksun.superwordbook.ui.home;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.activity.MainActivity;
import com.geeksun.superwordbook.adapter.HomeWordAdapter;
import com.geeksun.superwordbook.adapter.WordAdapter;
import com.geeksun.superwordbook.config.AppConfig;
import com.geeksun.superwordbook.util.HttpUtil;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment implements View.OnClickListener,GestureDetector.OnGestureListener{
    private HomeViewModel homeViewModel;
    private Button translate;//翻译按钮
    private EditText input;//输入框
    private TextView wordContentItem;//单词
    private TextView home_american_voice;//美式发音字段
    private TextView home_english_voice;//英式发音字段
    private Button home_american_radio;//美式发音按钮
    private Button home_english_radio;//英式发音按钮
    private CheckBox favor_checkbox;//喜欢

    private String englishVoiceUrl;
    private String americanVoiceUrl;
    private String home_english_voice_str;
    private String wordContentItemStr;
    private String home_american_voice_str;

    private List<String> explains;

    private GestureDetector gestureDetector;
    private int minDis = 100;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        input = getActivity().findViewById(R.id.getInput);
        wordContentItem = getActivity().findViewById(R.id.home_content);
        translate = getActivity().findViewById(R.id.translate);
        favor_checkbox = getActivity().findViewById(R.id.favor);
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
                }
                else if (word.split(" ").length == 0){
                    //什么都不做
                } else {
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

        favor_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String favorWord = wordContentItem.getText().toString();//点击喜欢按钮，添加的是翻译后的原单词，而不是输入框内的
                if(b == true){
                    HttpUtil.sendOkHttpRequest("http://47.107.108.67:8080/insertCollection?uid=1&content="+ favorWord, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Looper.prepare();
                            Toast.makeText(getActivity(),"后台发生错误，请联系管理员",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Looper.prepare();
                            Toast.makeText(getActivity(),"已添加到收藏夹",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    });
                }
                else{
                    HttpUtil.sendOkHttpRequest("http://localhost:8080/deleteCollection?uid=1&content="+ favorWord, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Looper.prepare();
                            Toast.makeText(getActivity(),"后台发生错误，请联系管理员",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Looper.prepare();
                            Toast.makeText(getActivity(),"已从收藏夹取消",Toast.LENGTH_SHORT).show();
                            Looper.loop();
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

        gestureDetector = new GestureDetector(getActivity(),this);
        MainActivity.myFragmentTouchListener touchListener = new MainActivity.myFragmentTouchListener() {
            @Override
            public boolean onTouch(MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        ((MainActivity) getActivity()).registerMyOnTouchListener(touchListener);

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
            wordContentItem.setVisibility(View.VISIBLE);
            home_american_voice.setVisibility(View.VISIBLE);
            home_english_voice.setVisibility(View.VISIBLE);
            home_american_radio.setVisibility(View.VISIBLE);
            home_english_radio.setVisibility(View.VISIBLE);
            favor_checkbox.setVisibility(View.VISIBLE);

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


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(motionEvent.getX()-motionEvent1.getX()<-minDis){
            MainActivity.getDrawer().openDrawer(Gravity.LEFT);
        }
        else if(motionEvent.getX()-motionEvent1.getX()>minDis){
            MainActivity.getDrawer().closeDrawer(Gravity.LEFT);
        }
        return false;
    }
}