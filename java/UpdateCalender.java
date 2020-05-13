package com.example.aniketkumar.mnnit_portal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class UpdateCalender extends AppCompatActivity {
    String encodedBase64 = "";
    TextView calender;
    String fee = "";
    String s1="",s2="",s3="",s4="",s5="",s6="",s7="";
    Button uploadCalender, uploadStructure;
    TextView structure;
    TextView t1, t2, t3, t4, t5, t6, t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_calender);
        calender = findViewById(R.id.calender);
        uploadCalender = findViewById(R.id.uploadCalender);
        structure = findViewById(R.id.structure);
        uploadStructure = findViewById(R.id.uploadStructure);
        t1 = findViewById(R.id.card1);
        t2 = findViewById(R.id.card2);
        t3 = findViewById(R.id.card3);
        t4 = findViewById(R.id.card4);
        t5 = findViewById(R.id.card5);
        t6 = findViewById(R.id.card6);
        t7 = findViewById(R.id.card7);

    }

    public void chooseCalender(View view) {
        Intent intent = new Intent();

        intent.setType("pdf/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Calender"), 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TAG1", "" + resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            calender.setText(uri.getLastPathSegment());
            Log.e("TAG", uri.getPath());


            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                encodedBase64 = android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }


        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            structure.setText(uri.getLastPathSegment());
            Log.e("TAG", uri.getPath());
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                fee = android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }
        else if(requestCode==3&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
             Uri uri=data.getData();
             t1.setText(uri.getLastPathSegment());
             Toast.makeText(getApplicationContext(),uri.getLastPathSegment(),Toast.LENGTH_LONG).show();
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                s1= android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }
        else if(requestCode==4&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            Uri uri=data.getData();
            t2.setText(uri.getLastPathSegment());
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                s2= android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }
        else if(requestCode==5&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            Uri uri=data.getData();
            t3.setText(uri.getLastPathSegment());
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                s3= android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }
        else if(requestCode==6&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            Uri uri=data.getData();
            t4.setText(uri.getLastPathSegment());
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                s4= android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }
        else if(requestCode==7&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            Uri uri=data.getData();
            t5.setText(uri.getLastPathSegment());
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                s5= android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }
        else if(requestCode==8&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            Uri uri=data.getData();
            t6.setText(uri.getLastPathSegment());
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                s6= android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }
        else if(requestCode==9&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            Uri uri=data.getData();
            t7.setText(uri.getLastPathSegment());
            try {
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                s7= android.util.Base64.encodeToString(bytes, 0);
                Toast.makeText(getApplicationContext(), "PDF ENCODED", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1", e.toString());
            } catch (IOException e) {
                Log.e("TAG2", e.toString());

            }
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public void uploadCalender(View view) {
        if (encodedBase64.equals("")) {
            Toast.makeText(getApplicationContext(), "Select Pdf first", Toast.LENGTH_LONG).show();
        } else {
            //background to push calender in database
            new BackgroundPushCalender().execute();
        }
    }

    public void chooseFee(View view) {
        Intent intent = new Intent();

        intent.setType("pdf/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Calender"), 2);
    }

    public void uploadFee(View view) {
        if (fee.equals("")) {
            Toast.makeText(getApplicationContext(), "Select Pdf first", Toast.LENGTH_LONG).show();
        } else {
            //background to push calender in database
            new BackgroundPushFee().execute();
        }
    }

    public void choose(int i) {
        Intent intent = new Intent();

        intent.setType("pdf/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select File"), i);
    }

    public class BackgroundPushCalender extends AsyncTask<String, Void, Void> {
        String res;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl = getString(R.string.ip) + "pushCalender.php";

            Log.e("TAGG::", "hello Bro");
            try {
                URL url = new URL(connectionUrl);
                Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                Log.e("TAGG::", "hi");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGG::", "hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                Log.e("TAGG::", "here1");

                String data = URLEncoder.encode("calender", "UTF-8") + "=" + URLEncoder.encode(encodedBase64, "UTF-8");

                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG", res + "");
            } catch (MalformedURLException e) {
                Log.e("TAG", "exception 1" + e.toString());
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.e("TAG", "exception 2" + e.toString());
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("TAG", "exception 3" + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAG", "exception 4" + e.toString());
                e.printStackTrace();

            } catch (Exception e) {
                Log.e("TAG", e.toString());
            }
            return null;
        }

        private String convertStreamToString(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder("");
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPreExecute() {

            uploadCalender.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            uploadCalender.setVisibility(View.VISIBLE);
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();

        }
    }

    public class BackgroundPushFee extends AsyncTask<String, Void, Void> {
        String res;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl = getString(R.string.ip) + "pushFee.php";

            Log.e("TAGG::", "hello Bro");
            try {
                URL url = new URL(connectionUrl);
                Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                Log.e("TAGG::", "hi");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGG::", "hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                Log.e("TAGG::", "here1");

                String data = URLEncoder.encode("fee", "UTF-8") + "=" + URLEncoder.encode(fee, "UTF-8");

                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG", res + "");
            } catch (MalformedURLException e) {
                Log.e("TAG", "exception 1" + e.toString());
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.e("TAG", "exception 2" + e.toString());
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("TAG", "exception 3" + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAG", "exception 4" + e.toString());
                e.printStackTrace();

            } catch (Exception e) {
                Log.e("TAG", e.toString());
            }
            return null;
        }

        private String convertStreamToString(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder("");
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPreExecute() {

            uploadStructure.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            uploadStructure.setVisibility(View.VISIBLE);
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();

        }
    }

    public void card1(View view) {
     choose(3);
    }

    public void card2(View view)
    {
        choose(4);
    }
    public void card3(View view) {
        choose(5);
    }
    public void card4(View view) {
        choose(6);
    }
    public void card5(View view) {

        choose(7);
    }
    public void card6(View view) {
        choose(8);
    }
    public void card7(View view) {
        choose(9);
    }

    public void upload1(View view)
    {
        if(s1.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Select pdf First",Toast.LENGTH_LONG).show();
        }
        else
        {
            new BackgroundPushDownloads().execute(s1,"card1");
        }
    }
    public void upload2(View view)
    {
        if(s2.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Select pdf First",Toast.LENGTH_LONG).show();
        }
        else
        {
            new BackgroundPushDownloads().execute(s2,"card2");
        }
    }
    public void upload3(View view)
    {
        if(s3.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Select pdf First",Toast.LENGTH_LONG).show();
        }
        else
        {
            new BackgroundPushDownloads().execute(s3,"card3");
        }
    }
    public void upload4(View view)
    {
        if(s4.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Select pdf First",Toast.LENGTH_LONG).show();
        }
        else
        {
            new BackgroundPushDownloads().execute(s4,"card4");
        }
    }
    public void upload5(View view)
    {
        if(s5.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Select pdf First",Toast.LENGTH_LONG).show();
        }
        else
        {
            new BackgroundPushDownloads().execute(s5,"card5");
        }
    }
    public void upload6(View view)
    {
        if(s6.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Select pdf First",Toast.LENGTH_LONG).show();
        }
        else
        {
            new BackgroundPushDownloads().execute(s6,"card6");
        }
    }
    public void upload7(View view)
    {
        if(s7.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Select pdf First",Toast.LENGTH_LONG).show();
        }
        else
        {
            new BackgroundPushDownloads().execute(s7,"card7");
        }
    }

    public class BackgroundPushDownloads extends AsyncTask<String, Void, Void> {
        String res;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl = getString(R.string.ip) + "pushDownload.php";
             String data=strings[0];
             String name=strings[1];

            Log.e("TAGG::", "hello Bro");
            try {
                URL url = new URL(connectionUrl);
                Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                Log.e("TAGG::", "hi");
                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGG::", "hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                Log.e("TAGG::", "here1");

                String dat = URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8")+ "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                Log.d("data", dat);
                bufferedWriter.write(dat);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG", res + "");
            } catch (MalformedURLException e) {
                Log.e("TAG", "exception 1" + e.toString());
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.e("TAG", "exception 2" + e.toString());
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("TAG", "exception 3" + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAG", "exception 4" + e.toString());
                e.printStackTrace();

            } catch (Exception e) {
                Log.e("TAG", e.toString());
            }
            return null;
        }

        private String convertStreamToString(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder("");
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPreExecute() {

          //  uploadCalender.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Uploading ",Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
         //   uploadCalender.setVisibility(View.VISIBLE);
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Uploading completed", Toast.LENGTH_SHORT).show();

        }
    }




}
