package com.example.aniketkumar.mnnit_portal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
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

public class PDFVIEW extends AppCompatActivity {
    ImageView iv;
    WebView webview;

    LinearLayout linearLayout,progressContainer;
    WebView mWebView;
    byte[] buffer;
    PDFView pdfView;
    File file;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pdfview);
        linearLayout=findViewById(R.id.linearContainer);
        linearLayout.setVisibility(View.GONE);
        pdfView=findViewById(R.id.pdfView);
        progressContainer=findViewById(R.id.progressContainer);
        progressContainer.setVisibility(View.VISIBLE);
        isStoragePermissionGranted();


         file = new File(getFilesDir() + File.separator + "AcademicCalender.pdf");
        try {
            if(!file.exists()) {
                if(file.createNewFile())
                {
                    Toast.makeText(getApplicationContext(),"file Created ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"not file Created ",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"file already Exist"+file.getPath(),Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"file not  Created ",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

            new BackgroundPDFViewer().execute();

    }
    public class BackgroundPDFViewer extends AsyncTask<String, Void, InputStream> {

        InputStream inputStream;
        @Override
        protected InputStream doInBackground(String[] objects) {

            final int MEGABYTE = 1024 * 1024;
            String fileUrl = getString(R.string.ip) + "Data/Calender/AcademicsCalender.pdf";



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

            linearLayout.setVisibility(View.GONE);
            progressContainer.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            Log.e("TAG","pdfview");
            if(inputStream!=null) {
                linearLayout.setVisibility(View.VISIBLE);
                pdfView.fromStream(inputStream).load();
                super.onPostExecute(inputStream);
                try {
                    Thread.sleep(3000);
                    progressContainer.setVisibility(View.GONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
    public void download(View view)
    {
        new BackgroundPDFDownload().execute();
    }


    public class BackgroundPDFDownload extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String[] objects) {
            InputStream inputStream;
            try {
                String fileUrl = getString(R.string.ip) + "Data/Calender/AcademicsCalender.pdf";
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
                File file = new File(folder, "Academics"+"MnnitDoc.pdf");

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
            //progressDialog = ProgressDialog.show(Download_Section.this,"Downloading","Please wait...",false,false);
            Toast.makeText(getApplicationContext(),"Downloading",Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          //  progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Download finished",Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }




}
