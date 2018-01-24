package com.ider.update.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eric on 2018/1/18.
 */

public class TimeUtil {
    public static String getStrTime(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
