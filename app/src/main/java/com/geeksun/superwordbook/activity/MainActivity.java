package com.geeksun.superwordbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;

import com.geeksun.superwordbook.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    public static DrawerLayout drawer;//侧边栏主窗口
    NavigationView navigationView;//侧边栏亮色部分
    private AppBarConfiguration mAppBarConfiguration;

    private ArrayList<myFragmentTouchListener> onTouchListeners = new ArrayList<myFragmentTouchListener>(
            10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.mainFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.mainFragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(drawer == null){
            return;
        }
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();}
    }

    public interface myFragmentTouchListener{
        public boolean onTouch(MotionEvent event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (myFragmentTouchListener listener : onTouchListeners) {
            if(listener != null) {
                listener.onTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public void registerMyOnTouchListener(myFragmentTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }
    public void unregisterMyOnTouchListener(myFragmentTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener) ;
    }
    public static  DrawerLayout getDrawer(){
        return drawer;
    }
}

