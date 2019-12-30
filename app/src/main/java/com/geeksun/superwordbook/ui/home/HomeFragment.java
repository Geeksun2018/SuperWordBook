package com.geeksun.superwordbook.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private Button translate;
    private EditText input;
    private TextView output;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        input = getActivity().findViewById(R.id.getInput);
       // output = getActivity().findViewById(R.id.outputResult);
        translate = getActivity().findViewById(R.id.translate);
        //output.setText("Hello World");
//        translate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String word = input.getText().toString();
//                if (word.split(" ").length > 1){
//                    //这是一个句子，应该翻译成中文。
//                }else {
//                    HttpUtil.sendOkHttpRequest("http://10.0.2.2:8080/translateWord?q=" + word, new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            //给用户显示网络异常！
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            String responseText = response.body().string();
//                            Log.d("Test", "onResponse: "+responseText);
//                        }
//                    });
//                }
//            }
//        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
    private void sendMessage(String msg) {
        output.setText(msg);
    }
}