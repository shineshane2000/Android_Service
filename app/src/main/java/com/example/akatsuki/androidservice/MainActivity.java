package com.example.akatsuki.androidservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //這裡處理儲存位置
    String path = Environment.getExternalStorageDirectory().toString();
    //這裡設置
    String filename = "myFileName.jpg";
    //設置下載地址
    String downloadUrl = "https://i.imgur.com/GTMs4pm.jpg";


    private OpenService openService = null;

    private String TAG = "MainActivity";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
            openService = ((OpenService.LocalBinder) serviceBinder).getService();
            Log.d(TAG, "onServiceConnected()" + name.getClassName());

        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected()" + name.getClassName());
        }
    };

    private Button startServiceButton, stopServiceButton, bindServiceButton, unbindServiceButton, callServiceButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServiceButton = (Button) findViewById(R.id.start_service_button);
        stopServiceButton = (Button) findViewById(R.id.stop_service_button);
        bindServiceButton = (Button) findViewById(R.id.bind_service_button);
        unbindServiceButton = (Button) findViewById(R.id.unbind_service_button);
        callServiceButton = (Button) findViewById(R.id.call_service_button);

        startServiceButton.setOnClickListener(this);
        stopServiceButton.setOnClickListener(this);
        bindServiceButton.setOnClickListener(this);
        unbindServiceButton.setOnClickListener(this);
        callServiceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service_button:
                openService = null;
                Intent intent = new Intent(MainActivity.this, OpenService.class);
                startService(intent);
                break;

            case R.id.stop_service_button:
                openService = null;
                Intent it = new Intent(MainActivity.this, OpenService.class);
                stopService(it);
                break;

            case R.id.bind_service_button:
                openService = null;
                Intent intent1 = new Intent(MainActivity.this, OpenService.class);
                bindService(intent1, serviceConnection, BIND_AUTO_CREATE);
                break;

            case R.id.unbind_service_button:
                openService = null;
                unbindService(serviceConnection);
                break;

            case R.id.call_service_button:
                if (openService != null) {
                    openService.downloadFile(downloadUrl, path, filename);
                } else {
                    Log.w(TAG, "Service is null.");
                }
                break;
        }
    }
}
