package com.example.aniketkumar.mnnit_portal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aniket Kumar on 26-Sep-18.
 */

public class NotificationService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Thread t = new Thread() {
            @Override
            public void run(){

              //  new BackGroundNotificationCheck().execute();
            }
        };
        t.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = getSharedPreferences("number", Context.MODE_PRIVATE);

        String p = sharedPreferences.getString("count", "0");

        if (p.equals("0")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("count", "0");
            editor.apply();
        }

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }


}
