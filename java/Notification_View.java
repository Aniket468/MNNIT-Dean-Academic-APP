package com.example.aniketkumar.mnnit_portal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Notification_View extends AppCompatActivity {

    PDFView pdfView;
    Button button;
    String url;
   // LinearLayout container1,progressContainer;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notification__view);
        //container1=findViewById(R.id.container1);
//        progressContainer=findViewById(R.id.progressContainer);
//        container1.setVisibility(View.GONE);
//        progressContainer.setVisibility(View.VISIBLE);

        pdfView=findViewById(R.id.pdfView);
        button=findViewById(R.id.download);

        Intent intent=getIntent();
        url=intent.getStringExtra("link");
        isStoragePermissionGranted();
        new BackgroundPDFViewer().execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundPDFDownload().execute();
            }
        });
    }
    public class BackgroundPDFViewer extends AsyncTask<String, Void, InputStream> {

        InputStream inputStream;
        @Override
        protected InputStream doInBackground(String[] objects) {

            String fileUrl = getString(R.string.ip) +url;
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.e("TAG", "" + "yes");
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (FileNotFoundException e) {
                Log.e("TAG", "" + e);
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.e("TAG", "" + e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAG", "" + e);
                e.printStackTrace();
            }
            return inputStream;
        }


        @Override
        protected void onPreExecute() {
           progressDialog= progressDialog.show(Notification_View.this,"Fetching Data","Please Wait a while",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            Log.e("TAG","pdfview");
            super.onPostExecute(inputStream);
            if(inputStream==null)
            {
                Toast.makeText(getApplicationContext(),"Server Not responding",Toast.LENGTH_LONG).show();
                return ;
            }
            pdfView.fromStream(inputStream).load();
            if(progressDialog!=null)
            progressDialog.dismiss();
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    public class BackgroundPDFDownload extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String[] objects) {
            InputStream inputStream;
            try {
                String fileUrl = getString(R.string.ip)+url;
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.e("TAG", "" + "yes");
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String str = Environment.getExternalStorageDirectory().toString();
                File folder = new File(str, "mnnit1");

                if (folder.exists()) {
                    Log.e("TAG","Folder Exist");
                } else {
                    folder.mkdir();
                    Log.e("TAG","Folder Created");
                }
                Date date=new Date();
                long a=date.getTime();

                File file = new File(folder, a+"MnnitNotice.pdf");

                if (file.createNewFile()) {
                    Log.e("TAG","File Creatint");
                } else {
                    Log.e("TAG","File exits");
                }
                Log.e("TAG", "" + file.getAbsolutePath());
                FileOutputStream f = new FileOutputStream(file);
                byte[] buffer = new byte[1024*1024];
                int len = 0;

                while ((len = inputStream.read(buffer)) > 0) {
                    f.write(buffer, 0, len);
                    Log.e("TAG","hi");
                }
                Log.e("TAG",""+buffer);
                f.flush();
                f.getFD().sync();
                f.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }


        @Override
        protected void onPreExecute() {
           // progressDialog=progressDialog.show(Notification_View.this,"Downloading","Please Wait a while",false,false);

            Toast.makeText(getApplicationContext(),"Downloading",Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           // progressDialog.dismiss();
            Toast.makeText(Notification_View.this,"Download finished",Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }
}
