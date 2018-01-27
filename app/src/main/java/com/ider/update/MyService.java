package com.ider.update;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ider.update.util.DeviceUtil;

import java.io.File;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        String firmwareVersion = DeviceUtil.getFirmwareVersion();
        File file = new File("/data/update"+firmwareVersion+".zip");
        if (file.exists()){
            file.delete();
        }
    }
}
