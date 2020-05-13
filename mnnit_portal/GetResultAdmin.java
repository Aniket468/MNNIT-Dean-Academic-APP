package com.example.aniketkumar.mnnit_portal;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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

public class GetResultAdmin extends AppCompatActivity {
    String name1,reg_no;
    TextView tname,tregno;
    LinearLayout ll;
    Button update;
    EditText sem1,sem2,sem3,sem4,sem5,sem6,sem7,sem8;
    String ss1,ss2,ss3,ss4,ss5,ss6,ss7,ss8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_result_admin);
       // Intent i=new Intent();
        Bundle bundle = getIntent().getExtras();
        name1=bundle.getString("name");
        reg_no=bundle.getString("reg_no");
        tname=findViewById(R.id.name);
        tregno=findViewById(R.id.reg_no);
        tname.setText(name1);
        tregno.setText(reg_no);
        ll=findViewById(R.id.ll);
        sem1=findViewById(R.id.sem1);
        sem2=findViewById(R.id.sem2);
        sem3=findViewById(R.id.sem3);
        sem4=findViewById(R.id.sem4);
        sem5=findViewById(R.id.sem5);
        sem6=findViewById(R.id.sem6);
        sem7=findViewById(R.id.sem7);
        sem8=findViewById(R.id.sem8);
        update=findViewById(R.id.update);
        retry();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateBackgroundResult().execute();
            }
        });
    }


    public  void retry()
    {
        new BackgroundResult().execute();
    }

    public class BackgroundResult extends AsyncTask<String,Void,Void> {

        private String result;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"result.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");
                Log.e("TAGG",""+data);
                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.e("TAG",result+"");
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
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
            if(result==null)
            {
                sem1.setText(result+"2");
                Log.d("Tag","null");
                // sign_up.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(ll, "Connection Error!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retry();
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
                sem1.setText(result+"1");
                Snackbar snackbar = Snackbar
                        .make(ll, "Authentication Error!!", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else
            {
              //  sem1.setText(result+"5");
                //   Toast.makeText(getApplicationContext(),result+"",Toast.LENGTH_LONG).show();

                try {
                    JSONObject jobject = new JSONObject(result+"0");
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject = jsonarray.getJSONObject(0);
                    sem1.setText(jsonObject.getString("sem1"));
                    sem2.setText(jsonObject.getString("sem2"));
                    sem3.setText(jsonObject.getString("sem3"));
                    sem4.setText(jsonObject.getString("sem4"));
                    sem5.setText(jsonObject.getString("sem5"));
                    sem6.setText(jsonObject.getString("sem6"));
                    sem7.setText(jsonObject.getString("sem7"));
                    sem8.setText(jsonObject.getString("sem8"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



    public class UpdateBackgroundResult extends AsyncTask<String,Void,Void> {

        private String result;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"updateresult.php";
            ss1=sem1.getText().toString();
            ss2=sem2.getText().toString();
            ss3=sem3.getText().toString();
            ss4=sem4.getText().toString();
            ss5=sem5.getText().toString();
            ss6=sem6.getText().toString();
            ss7=sem7.getText().toString();
            ss8=sem8.getText().toString();
            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8")+"&"+
                        URLEncoder.encode("sem1", "UTF-8") + "=" + URLEncoder.encode(ss1, "UTF-8")+"&"+
                        URLEncoder.encode("sem2", "UTF-8") + "=" + URLEncoder.encode(ss2, "UTF-8")+"&"+
                        URLEncoder.encode("sem3", "UTF-8") + "=" + URLEncoder.encode(ss3, "UTF-8")+"&"+
                        URLEncoder.encode("sem4", "UTF-8") + "=" + URLEncoder.encode(ss4, "UTF-8")+"&"+
                        URLEncoder.encode("sem5", "UTF-8") + "=" + URLEncoder.encode(ss5, "UTF-8")+"&"+
                        URLEncoder.encode("sem6", "UTF-8") + "=" + URLEncoder.encode(ss6, "UTF-8")+"&"+
                        URLEncoder.encode("sem7", "UTF-8") + "=" + URLEncoder.encode(ss7, "UTF-8")
                        +"&"+
                        URLEncoder.encode("sem8", "UTF-8") + "=" + URLEncoder.encode(ss8, "UTF-8");
                Log.e("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.e("TAG",result+"");
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
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
            if(result==null)
            {
                Log.d("Tag","null");
                // sign_up.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(ll, "Connection Error!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retry();
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
                        .make(ll, "Authentication Error!!", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else if(result.contains("1"))
            {
                Toast.makeText(getApplicationContext(),"update successful",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
