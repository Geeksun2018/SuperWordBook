package com.geeksun.superwordbook.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.activity.MainActivity;
import com.geeksun.superwordbook.config.AppConfig;
import com.geeksun.superwordbook.model.SearchAmount;
import com.geeksun.superwordbook.util.HttpUtil;

import java.io.IOException;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private Button loginConfirm;
    private Button loginCancel;
    private TextView text;

    private String accountStr;
    private String passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        text = findViewById(R.id.login_toolbar_text);
        text.setText("登录");
        account = findViewById(R.id.account_login);
        password = findViewById(R.id.password_login);
        loginConfirm = findViewById(R.id.btn_login);
        loginCancel = findViewById(R.id.btn_toolbar_cancel);

        loginConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountStr = account.getText().toString();
                passwordStr = password.getText().toString();
                if(accountStr.equals("")|| accountStr.length() > 12||passwordStr.equals("")|| passwordStr.length() > 20){
                    Toast toast=Toast.makeText(getApplicationContext(),"输入不合法", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                HttpUtil.sendOkHttpRequest(AppConfig.ip + "/login?username="+accountStr + "&password=" + passwordStr, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //给用户显示网络异常！
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        JSONObject jsonObject = JSON.parseObject(json);
                        if((jsonObject.get("code")).equals(0)){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Looper.prepare();
                            Toast toast=Toast.makeText(getApplicationContext(),"账号或密码错误", Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                    }
                });
            }
        });

        loginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
