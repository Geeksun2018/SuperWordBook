package com.geeksun.superwordbook.ui.Login.loginFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geeksun.superwordbook.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView text;
    private EditText accountInput;
    private EditText passwordInput;
    private Button RegisterConfirm;
    private Button RegisterCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        text = findViewById(R.id.login_toolbar_text);
        text.setText("注册");

        accountInput = findViewById(R.id.account_register);
        passwordInput = findViewById(R.id.password_register);
        RegisterConfirm = findViewById(R.id.btn_register_confirm);
        RegisterCancel = findViewById(R.id.btn_toolbar_cancel);

        RegisterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
