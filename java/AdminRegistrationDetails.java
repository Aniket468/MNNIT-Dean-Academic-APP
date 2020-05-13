package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

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

public class AdminRegistrationDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
     Button done;
     LinearLayout pickdate;
     EditText slot11,slot21,slot31;
     TextView date1;
    String date;

    ScrollView ll;
    String s1,s2,s3;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration_details);
        pickdate=findViewById(R.id.pickdate);
        slot11=findViewById(R.id.slot1);
        slot21=findViewById(R.id.slot2);
        slot31=findViewById(R.id.slot3);
        date1=findViewById(R.id.date);
        done=findViewById(R.id.done);
        ll=findViewById(R.id.ll);
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickdate.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        // Calender fragment loads for date picking
                        Calendar now = Calendar.getInstance();
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                (DatePickerDialog.OnDateSetListener) AdminRegistrationDetails.this,
                                now.get(Calendar.YEAR), // Initial year selection
                                now.get(Calendar.MONTH), // Initial month selection
                                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                        );
                        dpd.show(getFragmentManager(), "Datepickerdialog");
                    }
                });

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date11=date1.getText().toString().trim();
                if(date11.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Pick date first ", Toast.LENGTH_SHORT).show();
                }
                else {
                    new UpdateBackgroundResult().execute();
                }
            }
        });

    }
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        date1.setText(date);
        load();
    }

    public void load(){
        new DateBackgroundTask().execute();
    }

    public class DateBackgroundTask extends AsyncTask<String,Void,Void> {

        private String result;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"dateload.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
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
            progressDialog = ProgressDialog.show(AdminRegistrationDetails.this,"Sending message","Please wait...",false,false);

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
                        .make(ll, "Connection Error!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                load();
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
            else
            {
                   //Toast.makeText(getApplicationContext(),result+"", Toast.LENGTH_LONG).show();
                JSONObject jobject = null;
                try {
                    jobject = new JSONObject(result+"0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonarray = null;
                try {
                    jsonarray = jobject.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String s1,s2,s3;
                    JSONObject jsonObject = jsonarray.getJSONObject(0);
                    s1=jsonObject.getString("slot1");
                    s2=jsonObject.getString("slot2");
                    s3=jsonObject.getString("slot3");
                    if(s1.trim().equals("null")&&s2.trim().equals("null")&&s3.trim().equals("null")){
                        slot11.setText("0");
                        slot21.setText("0");
                        slot31.setText("0");
                    }
                    else{
                        slot11.setText(s1);
                        slot21.setText(s2);
                        slot31.setText(s3);
                    }
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
            String connectionUrl=getString(R.string.ip)+"dateupload.php";
            s1=slot11.getText().toString();
            s2=slot21.getText().toString();
            s3=slot31.getText().toString();

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+"&"+
                        URLEncoder.encode("slot1", "UTF-8") + "=" + URLEncoder.encode(s1, "UTF-8")+"&"+
                        URLEncoder.encode("slot2", "UTF-8") + "=" + URLEncoder.encode(s2, "UTF-8")+"&"+
                        URLEncoder.encode("slot3", "UTF-8") + "=" + URLEncoder.encode(s3, "UTF-8");
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
            progressDialog = ProgressDialog.show(AdminRegistrationDetails.this,"Sending message","Please wait...",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            super.onPostExecute(aVoid);

            if(result==null)
            {
                Log.d("Tag","null");
                // sign_up.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(ll, "Connection Error!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                load();
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
            else
            {
                Toast.makeText(getApplicationContext(),"Successfully updated"+result,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
