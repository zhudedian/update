package com.ider.update.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

/**
 * Created by Eric on 2018/1/27.
 */

public class DeviceUtil {
    public static String getLocalDeviceInfo(){
        return "当前版本型号："+Build.MODEL+"\n"+
                "当前桌面版本："+getLauncherVersionName()+"\n"+
                "当前固件版本："+getFirmwareVersion();
    }
    public static String getFirmwareVersion(){
        String version = null;
        try {
            Method method = Build.class.getDeclaredMethod("getString", String.class);
            method.setAccessible(true);
            version = (String) method.invoke(new Build(), "ro.build.display.id");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "get display:" + version);
        return version;
    }
    private static String getLauncherVersionName(){
        PackageInfo packageInfo ;
        try {
            packageInfo = MyApplication.getContext().getPackageManager().getPackageInfo("com.ider.overlauncher",0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo!=null){
            return packageInfo.versionName;
        }
        return "";
    }

}
