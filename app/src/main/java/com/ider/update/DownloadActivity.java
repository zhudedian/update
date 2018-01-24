package com.ider.update;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ider.update.circle.WaveProgress;
import com.ider.update.down.CustomerHttpClient;
import com.ider.update.down.HTTPFileDownloadTask;
import com.ider.update.util.UpdateUtil;
import com.ider.update.view.BaseRelative;

import org.apache.http.client.HttpClient;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{
    private String TAG = "DownloadActivity";
    private HTTPFileDownloadTask mHttpTask;
    private HttpClient mHttpClient;
    private TextView updating,downFail;
    private URI mHttpUri;
    private Context mContext;
    private WaveProgress waveProgress;
    private BaseRelative retry,cancel,update;
    private DownloadActivity.HTTPdownloadHandler mHttpDownloadHandler;
    private ProgressBar mProgressBar;
    private TextView mCompletedTV,mDownloadRateTV,mRemainTimeTV;
    public static boolean isUpdating = false;
    public static String FLASH_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        ActionBar actionBar= getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        mContext = this;
        updating = (TextView) findViewById(R.id.updating);
        updating.setOnKeyListener(this);
        waveProgress = (WaveProgress)findViewById(R.id.wave_progress_bar);
        retry = (BaseRelative) findViewById(R.id.retry);
        cancel = (BaseRelative) findViewById(R.id.cancel);
        update = (BaseRelative)findViewById(R.id.update_button);
        downFail= (TextView)findViewById(R.id.down_failed);
        cancel.setOnClickListener(this);
        retry.setOnClickListener(this);
        update.setOnClickListener(this);

        mCompletedTV = (TextView)findViewById(R.id.progress_completed);
        mDownloadRateTV = (TextView)findViewById(R.id.download_info_rate);
        mRemainTimeTV = (TextView)findViewById(R.id.download_info_remaining);
        mProgressBar = (ProgressBar)findViewById(R.id.progress_horizontal);
        mHttpClient = CustomerHttpClient.getHttpClient();
        mHttpDownloadHandler = new DownloadActivity.HTTPdownloadHandler();

        try {
            mHttpUri = new URI("http://192.168.2.27:8080/otaupdate/xml/download/up/1.zip");
//            mHttpUri = new URI(url);
        } catch (URISyntaxException e) {
            Toast.makeText(DownloadActivity.this,"网络异常，请重试！",Toast.LENGTH_LONG).show();
            finish();
            e.printStackTrace();
        }
        Log.i(TAG,FLASH_ROOT);
        mHttpTask = new HTTPFileDownloadTask(mHttpClient, mHttpUri,FLASH_ROOT, "/updata.zip", 1);
        mHttpTask.setProgressHandler(mHttpDownloadHandler);
        mHttpTask.start();
        isUpdating = true;
    }
    @Override
    public void onClick(View view){
        if (view.getId()==R.id.retry){
            mHttpTask = new HTTPFileDownloadTask(mHttpClient, mHttpUri,FLASH_ROOT, "/updata.zip", 1);
            mHttpTask.setProgressHandler(mHttpDownloadHandler);
            mHttpTask.start();
            retry.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            downFail.setVisibility(View.GONE);
        }else if (view.getId()==R.id.cancel){
            File f = new File("/data/updata.zip");
            if (f.exists()){
                f.delete();
            }
            isUpdating =false;
            finish();
        }else if(view.getId()==R.id.update_button){
            UpdateUtil.startUpdate("/data/update20180119.zip");
        }
    }
    private void setDownloadInfoViews(long contentLength, long receivedCount, long receivedPerSecond) {
        int percent = (int)(receivedCount * 100 / contentLength);
        mCompletedTV.setText(String.valueOf(percent) + "%");

        String rate = "";
        if(receivedPerSecond < 1024) {
            rate = String.valueOf(receivedPerSecond) + "B/S";
        }else if(receivedPerSecond/1024 > 0 && receivedPerSecond/1024/1024 == 0) {
            rate = String.valueOf(receivedPerSecond/1024) + "KB/S";
        }else if(receivedPerSecond/1024/1024 > 0) {
            rate = String.valueOf(receivedPerSecond/1024/1024) + "MB/S";
        }

        mDownloadRateTV.setText(rate);

        int remainSecond = (receivedPerSecond == 0) ? 0 : (int)((contentLength - receivedCount) / receivedPerSecond);
        String remainSecondString = "";
        if(remainSecond < 60) {
            remainSecondString = String.valueOf(remainSecond) + "s";
        }else if(remainSecond/60 > 0 && remainSecond/60/60 == 0) {
            remainSecondString = String.valueOf(remainSecond/60) + "min";
        }else if(remainSecond/60/60 > 0) {
            remainSecondString = String.valueOf(remainSecond/60/60) + "h";
        }
        remainSecondString = "剩余时间: " + remainSecondString;
        mRemainTimeTV.setText(remainSecondString);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            mHttpTask.stopDownload();
            return true;
        }
        return false;
    }


    private class HTTPdownloadHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            int whatMassage = msg.what;
            switch(whatMassage) {
                case HTTPFileDownloadTask.PROGRESS_UPDATE : {
                    Bundle b = msg.getData();
                    long receivedCount = b.getLong("ReceivedCount", 0);
                    long contentLength = b.getLong("ContentLength", 0);
                    long receivedPerSecond = b.getLong("ReceivedPerSecond", 0);
                    int percent = (int)(receivedCount * 100 / contentLength);
                    Log.d(TAG, "percent = " + percent);

                    setDownloadInfoViews(contentLength, receivedCount, receivedPerSecond);
//                    mProgressBar.setProgress(percent);
                    waveProgress.setValue(percent);

                }
                break;
                case HTTPFileDownloadTask.PROGRESS_DOWNLOAD_COMPLETE : {
                    waveProgress.setValue(100);
                    update.setVisibility(View.VISIBLE);
                }
                break;
                case HTTPFileDownloadTask.PROGRESS_DOWNLOAD_FAILED : {
//                    File f = new File("mnt/internal_sd/updata.zip");
//                    if (f.exists()){
//                        f.delete();
//                    }
                    Toast.makeText(DownloadActivity.this,"未能连接到服务器，请稍后再试",Toast.LENGTH_LONG).show();
//                    finish();
                }
                break;
//                case HTTPFileDownloadTask.PROGRESS_START_COMPLETE : {
//                    //mTxtState.setText("");
//                    mState = STATE_STARTED;
//                    mBtnControl.setText(getString(R.string.pause));
//                    mBtnControl.setClickable(true);
//                    mBtnControl.setFocusable(true);
//                    mBtnCancel.setClickable(true);
//                    mBtnCancel.setFocusable(true);
//                    setNotificationStrat();
//                    showNotification();
//                    mWakeLock.acquire();
//                }
//                break;
                case HTTPFileDownloadTask.PROGRESS_STOP_COMPLETE : {
                    Bundle b  = msg.getData();
                    int errCode = b.getInt("err", HTTPFileDownloadTask.ERR_NOERR);
                    if(errCode == HTTPFileDownloadTask.ERR_CONNECT_TIMEOUT) {
                        //mTxtState.setText("State: ERR_CONNECT_TIMEOUT");
//                        Toast.makeText(getApplicationContext(), getString(R.string.error_display), Toast.LENGTH_LONG).show();
                    }else if(errCode == HTTPFileDownloadTask.ERR_FILELENGTH_NOMATCH) {
                        //mTxtState.setText("State: ERR_FILELENGTH_NOMATCH");
                    }else if(errCode == HTTPFileDownloadTask.ERR_NOT_EXISTS) {
                        //mTxtState.setText("State: ERR_NOT_EXISTS");
//                        Toast.makeText(getApplicationContext(), getString(R.string.error_display), Toast.LENGTH_LONG).show();
                    }else if(errCode == HTTPFileDownloadTask.ERR_REQUEST_STOP) {
                        //mTxtState.setText("State: ERR_REQUEST_STOP");
                    }else if(errCode == HTTPFileDownloadTask.ERR_UNKNOWN) {
//                        Toast.makeText(getApplicationContext(), getString(R.string.error_display), Toast.LENGTH_LONG).show();
                    }
                    retry.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    downFail.setVisibility(View.VISIBLE);
                }
                break;
                default:
                    break;
            }
        }
    }
}
