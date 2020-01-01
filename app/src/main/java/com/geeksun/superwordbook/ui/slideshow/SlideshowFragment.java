package com.geeksun.superwordbook.ui.slideshow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.adapter.WordAdapter;
import com.geeksun.superwordbook.config.AppConfig;
import com.geeksun.superwordbook.model.Word;
import com.geeksun.superwordbook.util.HttpUtil;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private List<Word> myFavorList;
    private TextView tip;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Handler handler = new Handler();
        HttpUtil.sendOkHttpRequest("http://47.107.108.67:8080/getCollections?uid=1", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                tip = getActivity().findViewById(R.id.tip_slideshow);
                tip.setText("网络异常，请联系管理员");
                tip.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObject = JSON.parseObject(json);
                myFavorList = jsonObject.getJSONObject("data").getJSONArray("collections").toJavaList(Word.class);
                if(myFavorList.size()<=0){
                    tip.setText("您还未添加任何收藏单词，快去查单词吧！");
                    tip.setVisibility(View.VISIBLE);
                }else {
                    handler.post(runnableUI);
                }
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        return root;
    }

    Runnable runnableUI = new Runnable() {
        @Override
        public void run() {
            final WordAdapter myFavorAdapter = new WordAdapter(getActivity(),R.layout.word_item,myFavorList);
            ListView listView = getActivity().findViewById(R.id.myfavor_list);
            listView.setAdapter(myFavorAdapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String aimWord = myFavorList.get(i).getWordContent();
                    myFavorList.remove(i);
                    myFavorAdapter.notifyDataSetChanged();
                    toSeverDeleteWord(aimWord);
                    return false;
                }
            });
        }
    };


    public void toSeverDeleteWord(String aimWord){
        HttpUtil.sendOkHttpRequest(AppConfig.ip + "/deleteCollection?uid=1&content="+aimWord, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(),"后台发生错误，请联系管理员",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
}