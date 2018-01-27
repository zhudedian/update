package com.ider.update.util;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.ider.update.db.ServerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.R.id.list;
import static android.content.ContentValues.TAG;

/**
 * Created by Eric on 2018/1/18.
 */

public class PostUtil {

    private static String TAG = "PostUtil";
    private static HandleResult handleResult;
    public static void sendPost(final String url,HandleResult handleResult){
        PostUtil.handleResult = handleResult;
        Log.i(Thread.currentThread() .getStackTrace()[2].getClassName(),Thread.currentThread().getStackTrace()[2].getMethodName());
        new Thread(){
            @Override
            public void run(){
                try {
                    String postInfo = getPostInfo();
                    Log.i(TAG,postInfo);
                    String result = post(url,postInfo);
                    Log.i(TAG,"result="+result);
                    Message message = mHandler.obtainMessage();
                    message.what = 0;
                    Bundle data = new Bundle();
                    data.putString("result",result);
                    message.setData(data);
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static ServerInfo handleResponse(String response){
        try {
            return new Gson().fromJson(response,ServerInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static String post(String url,String params)throws IOException {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response="";
        try {
            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
            //创建URL
            httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //POST请求
            out = new OutputStreamWriter(
                    conn.getOutputStream());
            out.write(params);
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                response+=lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();


        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(reader!=null){
                    reader.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return response;
    }
    private static String getPostInfo(){
        return "{\n" +
                "    \"deviceModel\":\""+ Build.MODEL+"\",\n" +
//                "    \"deviceModel\":\""+ "molly"+"\",\n" +
                "    \"vendorID\":\"0\",\n" +
                "    \"firmwareVersion\":\""+DeviceUtil.getFirmwareVersion()+"\",\n" +
                "    \"serialNumber\":\"\",\n" +
                "    \"launcherPackage\":\"com.ider.overlauncher\",\n" +
                "    \"launcherVersion\":\"3.1.5\",\n" +
                "    \"mac\":\""+NetUtil.getEthMac()+"\",\n" +
                "    \"ip\":\"204.81.69.141\",\n" +
                "    \"peripheral\":{\n" +
                "      \"wifi\":{\n" +
                "            \"wifiRemote\":\"0\",\n" +
                "            \"wifiSocket\":\"0\",\n" +
                "            \"wifiRelay\":\"0\",\n" +
                "            \"wifiPowerstrip\":\"0\",\n" +
                "            \"wifiLight\":\"0\",\n" +
                "            \"wifiSoundbox\":\"0\",\n" +
                "            \"wifiMicrophone\":\"0\",\n" +
                "            \"wifiGateway\":\"0\"\n" +
                "        },\n"+
                "        \"bluetooth\":{\n" +
                "            \"bluetoothRemote\":\"1\",\n" +
                "            \"bluetoothSocket\":\"0\",\n" +
                "            \"bluetoothRelay\":\"0\",\n" +
                "            \"bluetoothPowerstrip\":\"0\",\n" +
                "            \"bluetoothLight\":\"0\",\n" +
                "            \"bluetoothSoundbox\":\"0\",\n" +
                "            \"bluetoothMicrophone\":\"0\",\n" +
                "            \"bluetoothGateway\":\"0\",\n" +
                "            \"bluetoothWristband\":\"0\"\n" +
                "        },\n" +
                "        \"zigbee\":{\n" +
                "            \"zigbeeRemote\":\"0\",\n" +
                "            \"zigbeeSocket\":\"0\",\n" +
                "            \"zigbeeRelay\":\"0\",\n" +
                "            \"zigbeePowerstrip\":\"0\",\n" +
                "            \"zigbeeLight\":\"0\",\n" +
                "            \"zigbeeSoundbox\":\"0\",\n" +
                "            \"zigbeeMicrophone\":\"0\",\n" +
                "            \"zigbeeGateway\":\"0\",\n" +
                "            \"zigbeeWristband\":\"0\"\n" +
                "        },\n"+
                "        \"upart\":{\n" +
                "            \"upart\":\"0\",\n" +
                "            \"vpart\":\"0\"\n" +
                "        },\n" +
                "        \"rf\":{\n" +
                "            \"315\":\"1\",\n" +
                "            \"433\":\"0\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"remote\":\"1\",\n" +
                "    \"reportTime\":\""+TimeUtil.getStrTime()+"\",\n" +
                "    \"sign\":\""+MD5Util.getMD5(NetUtil.getEthMac()+TimeUtil.getStrTime())+"\"\n"+
                "}";
    }
    private static Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 0:
                    Bundle data = msg.getData();
                    if (data == null) {
                        return;
                    }
                    ServerInfo serverInfo = handleResponse(data.getString("result"));
                    handleResult.resultHandle(serverInfo);
                    break;
                case 1:

                    break;
            }
        }
    };
    public interface HandleResult{
        void resultHandle(ServerInfo serverInfo);
    }
}
