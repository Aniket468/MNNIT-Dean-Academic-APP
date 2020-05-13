package com.example.aniketkumar.mnnit_portal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import static java.lang.String.valueOf;

public class ChooseSlot extends AppCompatActivity {
    Button choose1,choose2,choose3,confirmandsubmit,pickdate;
    CardView cslot1,cslot2,cslot3;
    TextView tchoose;
    TextView  avail1,avail2,avail3;
    int flag=0;
    String reg_no,slot,s1,s2,s3;
    //ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_slot);
        choose1=findViewById(R.id.choose1);
        choose2=findViewById(R.id.choose2);
        choose3=findViewById(R.id.choose3);
        cslot1=findViewById(R.id.cslot1);
        cslot2=findViewById(R.id.cslot2);
        cslot3=findViewById(R.id.cslot3);
        tchoose=findViewById(R.id.tchoose);
        avail1=findViewById(R.id.avail1);
        avail2=findViewById(R.id.avail2);
        avail3=findViewById(R.id.avail3);
        pickdate=findViewById(R.id.pickdate);
        confirmandsubmit=findViewById(R.id.confirmandsubmit);
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        reg_no = sharedPreferences.getString("reg_no", "0");

        new BackgroundTaskGender().execute();
        new BackgroundTaskAvail().execute();
        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tchoose.setText("you have choosed slot 1 be on time");
                if(flag==0) {
                    s1 = avail1.getText().toString();
                    int t = Integer.parseInt(s1);
                    t = t - 1;
                    s1 = String.valueOf(t);
                    avail1.setText(s1);
                    flag = 1;
                }
                else if(flag==1)
                {
                    s1 = avail1.getText().toString();
                    int t = Integer.parseInt(s1);
                    t = t + 1;
                    s1 = String.valueOf(t);
                    avail1.setText(s1);
                    s1 = avail1.getText().toString();
                     t = Integer.parseInt(s1);
                    t = t - 1;
                    s1 = String.valueOf(t);
                    avail1.setText(s1);
                    flag = 1;
                }
                else if(flag==2)
                {
                    s2 = avail2.getText().toString();
                    int t = Integer.parseInt(s2);
                    t = t + 1;
                    s2 = String.valueOf(t);
                    avail2.setText(s2);
                    s1 = avail1.getText().toString();
                    t = Integer.parseInt(s1);
                    t = t - 1;
                    s1 = String.valueOf(t);
                    avail1.setText(s1);
                    flag = 1;
                }

                else if(flag==3)
                {
                    s3 = avail3.getText().toString();
                    int t = Integer.parseInt(s3);
                    t = t + 1;
                    s3 = String.valueOf(t);
                    avail3.setText(s3);
                    s1 = avail1.getText().toString();
                    t = Integer.parseInt(s1);
                    t = t - 1;
                    s1 = String.valueOf(t);
                    avail1.setText(s1);
                    flag = 1;
                }
            }
        });
        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tchoose.setText("you have choosed slot 2 be on time");
                if(flag==0) {
                    s2 = avail2.getText().toString();
                    int t = Integer.parseInt(s2);
                    t = t - 1;
                    s2 = String.valueOf(t);
                    avail2.setText(s2);
                    flag = 2;
                }
                else if(flag==1)
                {
                    s1 = avail1.getText().toString();
                    int t = Integer.parseInt(s1);
                    t = t + 1;
                    s1 = String.valueOf(t);
                    avail1.setText(s1);
                    s2 = avail2.getText().toString();
                    t = Integer.parseInt(s2);
                    t = t - 1;
                    s2 = String.valueOf(t);
                    avail2.setText(s2);
                    flag = 2;
                }
                else if(flag==2)
                {
                    s2 = avail2.getText().toString();
                    int t = Integer.parseInt(s2);
                    t = t + 1;
                    s2 = String.valueOf(t);
                    avail2.setText(s2);
                    s2 = avail2.getText().toString();
                    t = Integer.parseInt(s2);
                    t = t - 1;
                    s2 = String.valueOf(t);
                    avail2.setText(s2);
                    flag = 2;
                }

                else if(flag==3)
                {
                    s3 = avail3.getText().toString();
                    int t = Integer.parseInt(s3);
                    t = t + 1;
                    s3 = String.valueOf(t);
                    avail3.setText(s3);
                    s2 = avail2.getText().toString();
                    t = Integer.parseInt(s2);
                    t = t - 1;
                    s2 = String.valueOf(t);
                    avail2.setText(s2);
                    flag = 2;
                }
            }
        });
        choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tchoose.setText("you have choosed slot 3 be on time");
                if(flag==0) {
                    s3 = avail3.getText().toString();
                    int t = Integer.parseInt(s3);
                    t = t - 1;
                    s3 = String.valueOf(t);
                    avail3.setText(s3);
                    flag = 3;
                }
                else if(flag==1)
                {
                    s1 = avail1.getText().toString();
                    int t = Integer.parseInt(s1);
                    t = t + 1;
                    s1 = String.valueOf(t);
                    avail1.setText(s1);
                    s3 = avail3.getText().toString();
                    t = Integer.parseInt(s3);
                    t = t - 1;
                    s3 = String.valueOf(t);
                    avail3.setText(s3);
                    flag = 3;
                }
                else if(flag==2)
                {
                    s2 = avail2.getText().toString();
                    int t = Integer.parseInt(s2);
                    t = t + 1;
                    s2 = String.valueOf(t);
                    avail2.setText(s2);
                    s3 = avail3.getText().toString();
                    t = Integer.parseInt(s3);
                    t = t - 1;
                    s3 = String.valueOf(t);
                    avail3.setText(s3);
                    flag = 3;
                }

                else if(flag==3)
                {
                    s3 = avail3.getText().toString();
                    int t = Integer.parseInt(s3);
                    t = t + 1;
                    s3 = String.valueOf(t);
                    avail3.setText(s3);
                    s3 = avail3.getText().toString();
                    t = Integer.parseInt(s3);
                    t = t - 1;
                    s3 = String.valueOf(t);
                    avail3.setText(s3);
                    flag = 3;
                }
            }
        });
        confirmandsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    confirm();
            }
        });
    }
    public void confirm(){
       new BackgroundTaskChooseslot().execute();
    }
    public class BackgroundTaskGender extends AsyncTask<String,Void,Void> {

        private String result;
        String gender;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"detectgender.php";

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
           //
            //progressDialog = ProgressDialog.show(getApplicationContext(),"Sending message","Please wait...",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           // progressDialog.dismiss();
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
            if(result==null)
            {
               Toast.makeText(getApplicationContext(),"connection error",Toast.LENGTH_LONG).show();
            }
            else if(result.contains("Failed"))
            {
               Toast.makeText(getApplicationContext(),"Authentication error",Toast.LENGTH_LONG).show();
            }
            else
            {

                gender=result;
                Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
                if(gender.contains("Male"))
                {
                    setslot1visible();

                }
                else{
                    setslot2visible();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    public class BackgroundTaskAvail extends AsyncTask<String,Void,Void> {

        private String result;
        String gender;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"showavailibility.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);

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
          //  progressDialog = ProgressDialog.show(getApplicationContext(),"Checking","Please wait...",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressDialog.dismiss();
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
            if(result==null)
            {
                Toast.makeText(getApplicationContext(),"connection error",Toast.LENGTH_LONG).show();
            }
            else if(result.contains("false"))
            {
                Toast.makeText(getApplicationContext(),"Authentication error",Toast.LENGTH_LONG).show();
            }
            else
            {
                try {
                    JSONObject  jsonObject=new JSONObject(result);
                    JSONArray jsonArray=jsonObject.getJSONArray("result");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    avail1.setText(jsonObject1.getString("slot1"));
                    avail2.setText(jsonObject1.getString("slot2"));
                    avail3.setText(jsonObject1.getString("slot3"));
                    pickdate.setText(jsonObject1.getString("date"));
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
    public class BackgroundTaskChooseslot extends AsyncTask<String,Void,Void> {

        private String result;


        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"slotchosen.php";
            s1=avail1.getText().toString();
            s2=avail2.getText().toString();
            s3=avail3.getText().toString();
            if(flag==1)
            {
                slot="slot1";

            }
            else if(flag==2)
            {
                slot="slot2";
            }
            else if(flag==3)
            {
                slot="slot3";
            }

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                  OutputStream out = httpURLConnection.getOutputStream();
                   BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                 String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8")+"&"+
                         URLEncoder.encode("slot", "UTF-8") + "=" + URLEncoder.encode(slot, "UTF-8")
                         +"&"+
                         URLEncoder.encode("slot1", "UTF-8") + "=" + URLEncoder.encode(s1, "UTF-8")
                         +"&"+
                         URLEncoder.encode("slot2", "UTF-8") + "=" + URLEncoder.encode(s2, "UTF-8")
                         +"&"+
                         URLEncoder.encode("slot3", "UTF-8") + "=" + URLEncoder.encode(s3, "UTF-8");
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
           // progressDialog = ProgressDialog.show(getApplicationContext(),"Confirming Your Slot","Please wait...",false,false);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
            if(result==null)
            {
                Toast.makeText(getApplicationContext(),"connection error",Toast.LENGTH_LONG).show();
            }
            else if(result.contains("false"))
            {
                Toast.makeText(getApplicationContext(),"Authentication error",Toast.LENGTH_LONG).show();
            }
            else
            {
                confirmandsubmit.setEnabled(false);
               Intent i=new Intent(getApplicationContext(),GenerateForm.class);
               startActivity(i);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public void setslot1visible(){
        cslot1.setVisibility(View.VISIBLE);
        choose1.setVisibility(View.VISIBLE);
        cslot2.setVisibility(View.GONE);
        choose2.setVisibility(View.GONE);
        cslot3.setVisibility(View.VISIBLE);
        choose3.setVisibility(View.VISIBLE);
    }
    public void setslot2visible(){
        cslot1.setVisibility(View.GONE);
        choose1.setVisibility(View.GONE);
        cslot3.setVisibility(View.GONE);
        choose3.setVisibility(View.GONE);
        cslot2.setVisibility(View.VISIBLE);
        choose2.setVisibility(View.VISIBLE);
    }


}
