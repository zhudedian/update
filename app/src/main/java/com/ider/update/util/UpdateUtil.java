package com.ider.update.util;

import android.content.Context;
import android.os.PowerManager;
import android.os.storage.StorageManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Eric on 2018/1/18.
 */

public class UpdateUtil {
    public static void startUpdate(String path){
        File zip = new File(path);
        if (!zip.exists()){
            return;
        }
//        if (!path.equals("/data/update20180119.zip")){
//            copyFile(path,"/data/update20180119.zip");
//        }
        try {
            Runtime.getRuntime().exec("chmod 644 " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createRecoveryCommand(path);
        try {
            Thread.sleep(3000);
            Class SystemProperties = Class.forName("android.os.SystemProperties");
            Method set = SystemProperties.getMethod("set", String.class, String.class);
            set.invoke(null,"sys.powerctl", "reboot,recovery");
//            Toast.makeText(MyApplication.getContext(),"设置成功",Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        SystemProperties.set("sys.powerctl", "reboot,recovery");
//        PowerManager pManager=(PowerManager) MyApplication.getContext().getSystemService(Context.POWER_SERVICE);
//        pManager.reboot("recovery");
//        pManager.reboot("update");
    }
    private static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newFile = new File(newPath);
            if (newFile.exists()){
                newFile.delete();
                newFile.createNewFile();
            }else {
                newFile.createNewFile();
            }
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[4096];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static boolean createRecoveryCommand(String path) {
        File file;
        String res = "";
        file = new File("/cache/recovery/command");
        try{
            File parent = file.getParentFile();
            if(!parent.exists()){
                parent.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        res += "--update_package=";
        res += path;
        try {
            FileOutputStream fout = new FileOutputStream(file);
            byte[] buffer = res.getBytes();
            fout.write(buffer);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
