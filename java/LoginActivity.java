package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.itextpdf.text.pdf.codec.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class LoginActivity extends AppCompatActivity implements Menu{
    String reg_id;
    String pass;
    TextInputEditText reg,pas;
    Button login;
    CoordinatorLayout cl;
    ScrollView sv;
    ProgressBar pb;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        reg=findViewById(R.id.reg);
        pas=findViewById(R.id.loginpass);
        login=findViewById(R.id.login);
        sv=findViewById(R.id.scroll);
       // cl=findViewById(R.id.cdlogin);
        pb=findViewById(R.id.progressBarlogin);
        pb.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }
    public  void login(View view)
    {
        reg_id=reg.getText().toString();
        pass=pas.getText().toString();
        reg_id=reg_id.trim();
        pass=pass.trim();
        if(reg_id.equals("")||pass.equals(""))
        {
            if(reg_id.equals(""))
            {
                Toast.makeText(getApplicationContext(),"Registration number must not be empty",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Fill the password section",Toast.LENGTH_LONG).show();
            }

        }

        else
        {
            if(reg_id.length()<5)
            {
                Toast.makeText(getApplicationContext(), "Invalid Registration no.", Toast.LENGTH_SHORT).show();
            }
            else{
                if(pass.length()<8){
                    Toast.makeText(getApplicationContext(),"Incorrect Password !! ",Toast.LENGTH_LONG).show();
                }
                else {
                    BackgroundLogin backgroundRegistration = new BackgroundLogin();
                    backgroundRegistration.execute();
                }
            }
            //do background here
        }
    }
    public class BackgroundLogin extends AsyncTask {
        String res="";
        InputStream inputStream;
        @Override
        protected Object doInBackground(Object[] objects) {

            String connectionUrl = getString(R.string.ip)+"login.php";

            Log.e("TAGG::", "hello Bro");
            try {
                URL url = new URL(connectionUrl);
                Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                Log.e("TAGG::", "hi");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGG::", "hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                Log.e("TAGG::", "here1");

                String data = URLEncoder.encode("registrationid", "UTF-8") + "=" + URLEncoder.encode(reg_id, "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                 inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAGGG", res +"res");
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

//            sv.setVisibility(View.GONE);
//            pb.setVisibility(View.VISIBLE);
            progressDialog = ProgressDialog.show(LoginActivity.this,"Checking Info","Please wait...",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {

            progressDialog.dismiss();
            super.onPostExecute(o);
            if(inputStream==null)
            {
                Toast.makeText(getApplicationContext(),"Server Not Responding",Toast.LENGTH_LONG).show();
                return ;
            }
            if (!(res==null)) {
                if (res.contains("1")) {
                    SharedPreferences sharedPreferences=getSharedPreferences("id", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("reg_no",reg_id);
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(i);
                } else if (res.contains("2")) {
                    Toast.makeText(getApplicationContext(), "You are not verified yet!!", Toast.LENGTH_LONG).show();
                } else if (res.contains("false")) {
                    Toast.makeText(getApplicationContext(), "You are not yet for registered !!", Toast.LENGTH_LONG).show();
                } else if (res.contains("4")) {
                    Toast.makeText(getApplicationContext(), "Your Credentials seems incorrect!", Toast.LENGTH_LONG).show();
                }
            } else  {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
            }


        }
    }
    public void forgotPass(View view)
    {
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "aniket468kr@gmail.com"));
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Forgot Pass");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "My Registration Number is ");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),"Error opening Gmail ",Toast.LENGTH_LONG).show();
            //TODO smth
        }
    }
}
