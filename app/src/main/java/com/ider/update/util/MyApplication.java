package com.ider.update.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Eric on 2018/1/18.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
