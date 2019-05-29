package com.example.akatsuki.androidservice;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Akatsuki on 2019/4/19 10:29.
 */
public class OpenService extends Service {

    private static final String TAG = "OpenService";

    public class LocalBinder extends Binder {
        OpenService getService() {
            return OpenService.this;
        }
    }

    private LocalBinder localBinder = new LocalBinder();
    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "onBind()");
        return localBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    public void downloadFile(String url, String path, String fileName) {
        Log.d(TAG, "downloadFile()");
        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(path, fileName, new ImageCompleteListener() {
            @Override
            public void onImageSaveComplete(Bitmap bitmap) {
                Log.d(TAG, "success!!");
            }

            @Override
            public void onImageSaveFailed(Exception exception) {
                Log.e(TAG, "Failed!!");
            }
        });

        imageDownloaderTask.execute(url);
    }
}
