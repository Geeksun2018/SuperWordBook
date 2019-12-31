package com.geeksun.superwordbook.ui.Login.loginFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geeksun.superwordbook.R;

public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private Button loginConfirm;
    private Button loginCancel;
    private TextView text;

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

        loginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
