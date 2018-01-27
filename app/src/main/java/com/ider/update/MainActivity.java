package com.ider.update;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ider.update.db.ServerInfo;
import com.ider.update.util.DeviceUtil;
import com.ider.update.util.PostUtil;
import com.ider.update.util.UpdateUtil;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private Button update,back;
    private TextView textView1,textView2,textView3;
    private String url = "http://www.trehere.com:8089/ydh/service/api.html";
    private String downloadUrl;
    private String Md5;
    private String newVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar= getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {

        }
        update = (Button)findViewById(R.id.update_button);
        back = (Button)findViewById(R.id.back_button);
        textView1 = (TextView)findViewById(R.id.text1);
        textView2 = (TextView)findViewById(R.id.text2);
        textView3 = (TextView)findViewById(R.id.text3);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DownloadActivity.class);
                intent.putExtra("url",downloadUrl);
                intent.putExtra("Md5",Md5);
                intent.putExtra("newVersion",newVersion);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        PostUtil.sendPost(url, new PostUtil.HandleResult() {
            @Override
            public void resultHandle(ServerInfo serverInfo) {
                textView1.setText(DeviceUtil.getLocalDeviceInfo());
                String info = "新固件版本：";
                if (serverInfo!= null&&serverInfo.getUrl()!=null){
                    long size = Long.parseLong(serverInfo.getSize());
                    info += serverInfo.getNewVersion()+"\n";
                    info += "升级包大小："+getSize(size)+"\n";
                    info += "升级说明："+serverInfo.getMsg();
                    textView2.setText("检测到新版本");
                    textView3.setText(info);
                    downloadUrl=serverInfo.getUrl();
                    Md5 = serverInfo.getMd5();
                    newVersion = serverInfo.getNewVersion();
                    update.setVisibility(View.VISIBLE);
                }else {
                    textView2.setText("已经是最新版本");
                    update.setVisibility(View.GONE);
                }
            }
        });
//        new Thread(){
//            @Override
//            public void run(){
//                try {
//                   sleep(3000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Intent intent = new Intent("alibaba.ailabs.genisdk.android.install.missapp");
//                intent.putExtra("packageName","com.qclive.tv");
//                sendBroadcast(intent);
//            }
//        }.start();
    }
    public static String getSize(long size){
        return getSize((float)size);
    }
    public static String getSize(float size){
        if (size<1024){
            return size+"B";
        }else if (size<1024*1024){
            float s = size/1024;
            return String.format("%.2f",s)+"K";
        }else if (size<1024*1024*1024){
            float s = size/1024/1024;
            return String.format("%.2f",s)+"M";
        }else if (size/1024<1024*1024*1024){

            float s = size/1024/1024/1024;
            return String.format("%.2f",s)+"G";
        }else {
            float s = size/1024/1024/1024/1024;
            return String.format("%.2f",s)+"T";
        }
    }
}
