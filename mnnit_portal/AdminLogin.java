package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class AdminLogin extends AppCompatActivity {

    TextInputEditText adid,adpass;
    Button adlog;
    String sadid,sadpass;
    ScrollView cl;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        adid=findViewById(R.id.adid);
        adpass=findViewById(R.id.adpass);
        adlog=findViewById(R.id.adlog);
        cl=findViewById(R.id.cl);
        adlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login(view);
            }
        });

    }
    public  void login(View view)
    {
        sadid=adid.getText().toString();
        sadpass=adpass.getText().toString();
        sadid=sadid.trim();
        sadpass=sadpass.trim();
        if(sadid.equals("")||sadpass.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Fill all Credential",Toast.LENGTH_LONG).show();
        }
        else
        {

            AdminLoginBackGroundTask adbackgrroundtask=new AdminLoginBackGroundTask();
            adbackgrroundtask.execute();
        }
    }
    public class AdminLoginBackGroundTask extends AsyncTask<String,Void,Void> {

        private String result;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"adlogin1.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("adid", "UTF-8") + "=" + URLEncoder.encode(sadid, "UTF-8") + "&" +
                        URLEncoder.encode("adpass", "UTF-8") + "=" + URLEncoder.encode(sadpass, "UTF-8");
                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG",result+"");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
            progressDialog = ProgressDialog.show(AdminLogin.this,"Checking Info","Please wait...",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
          progressDialog.dismiss();
            if(result==null)
            {
                Log.d("Tag","null");
                // sign_up.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(cl, "Connection Error!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                login(view);
                            }
                        });

                // Changing message text color
                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else if(result.contains("Failed"))
            {
                Log.d("Tag","Failed");
                Snackbar snackbar = Snackbar
                        .make(cl, "Authentication Error!!", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else
            {
                //   Toast.makeText(getApplicationContext(),result+"",Toast.LENGTH_LONG).show();

                Intent intent=new Intent(getApplicationContext(),After_Admin_Login.class);
                intent.putExtra("json",result);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
