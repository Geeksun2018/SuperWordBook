package com.geeksun.superwordbook.ui.Login.loginFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.geeksun.superwordbook.R;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener,
WelcomeFragment.OnFragmentInteractionListener{

    private Button login;
    private Button register;



    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment loginFragment;
    private Fragment welcomeFragment;
    private Fragment registerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        welcomeFragment = new WelcomeFragment();


        transaction.add(R.id.login_mainLay,welcomeFragment,"login_home");



        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                if (loginFragment==null){
                    loginFragment = new LoginFragment();
                    transaction.add(R.id.login_mainLay,loginFragment,"login");
                }
                transaction.show(loginFragment);
                transaction.commit();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                if (registerFragment==null){
                    registerFragment = new RegisterFragment();
                    transaction.add(R.id.login_mainLay,registerFragment,"register");
                }
                transaction.show(registerFragment);
                transaction.commit();
            }
        });

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
