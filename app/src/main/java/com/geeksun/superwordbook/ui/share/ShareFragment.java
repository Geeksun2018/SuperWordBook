package com.geeksun.superwordbook.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geeksun.superwordbook.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        // 比如发送文本形式的数据内容
        // 指定发送的内容
        sendIntent.putExtra(Intent.EXTRA_TEXT, "如想使用本软件请联系孙旺995700257");
        // 指定发送内容的类型
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent,"Share to..."));
    }

}