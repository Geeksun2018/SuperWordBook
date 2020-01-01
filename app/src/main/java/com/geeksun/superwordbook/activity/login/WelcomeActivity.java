package com.geeksun.superwordbook.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.config.AppConfig;

public class WelcomeActivity extends AppCompatActivity {

    private Button login;
    private Button register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        AppConfig.getProperties(this);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(toLogin);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(WelcomeActivity.this,RegisterActivity.class);
                startActivity(toRegister);
            }
        });
    }

}
