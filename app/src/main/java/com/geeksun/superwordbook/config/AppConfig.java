package com.geeksun.superwordbook.config;

import android.content.Context;
import android.util.Log;

import java.util.Properties;

public class AppConfig {

        public static String ip;

        public static void getProperties(Context context) {

            Properties props = new Properties();
            try {

                props.load(context.getAssets().open("app.properties"));
                ip = props.getProperty("contrastIP");
                Log.d("Test", "getProperties: " + props.getProperty("contrastIP"));
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }



}
