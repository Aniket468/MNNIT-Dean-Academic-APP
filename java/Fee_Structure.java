package com.example.aniketkumar.mnnit_portal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Fee_Structure extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button download;
    PDFView pdfView;
    Context ctx;
    InputStream iss;
    ProgressDialog progressDialog;

    public Fee_Structure() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View retView = inflater.inflate(R.layout.fragment_fee_structure, container, false);
        ctx=retView.getContext();
        download=retView.findViewById(R.id.download);
        pdfView=retView.findViewById(R.id.pdfView);
        isStoragePermissionGranted();
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStoragePermissionGranted();
                new BackgroundPDFDownload().execute();

            }

        });

        new BackgroundPDFViewer().execute();

        return  retView;
    }
    public class BackgroundPDFViewer extends AsyncTask<String, Void, InputStream> {

        InputStream inputStream;
        @Override
        protected InputStream doInBackground(String[] objects) {

            String fileUrl = getString(R.string.ip) + "Data/Fee_Structure/FeeStructure.pdf";
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

            progressDialog = ProgressDialog.show(ctx,"Fetchind Data","Please wait...",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            Log.e("TAG","pdfview");
            iss=inputStream;
            progressDialog.dismiss();
            if(inputStream==null)
            {
                Toast.makeText(ctx,"Server Not Responding",Toast.LENGTH_LONG).show();
                return ;
            }
            pdfView.fromStream(inputStream).load();
            super.onPostExecute(inputStream);

        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ctx.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
                String fileUrl = getString(R.string.ip) + "Data/Fee_Structure/FeeStructure.pdf";
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
                File file = new File(folder, "Fee_Structure.pdf");

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
           // progressDialog = ProgressDialog.show(ctx,"Downloading","Please wait...",false,false);

            Toast.makeText(ctx,"Downloading",Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          //  progressDialog.dismiss();
            Toast.makeText(ctx,"Download finished",Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }




}
