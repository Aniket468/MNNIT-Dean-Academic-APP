package com.example.aniketkumar.mnnit_portal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Verification extends AppCompatActivity {
    ProgressBar mainbar, progressBarImage;
    private TextView nameT, reg_noT, passT, categoryT, genderT, statusT, dobT;
    TextView account, ifsc, contactH, contactP, bloodGroup, religion, nationality;
    Button submit;
    CircleImageView imageView;
    public String st="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verification);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String pass = bundle.getString("pass");
        String reg_no = bundle.getString("reg_no");
        String category = bundle.getString("category");
        String gender = bundle.getString("gender");
        String status = bundle.getString("status");
        String dob = bundle.getString("dob");
        String imagepath = bundle.getString("imagepath");


        //assigning id

        nameT = findViewById(R.id.profile_name);
        reg_noT = findViewById(R.id.profile_regNum);
        dobT = findViewById(R.id.profile_dob);
        passT = findViewById(R.id.profile_pass);
        categoryT = findViewById(R.id.profile_category);
        genderT = findViewById(R.id.profile_gender);
        statusT = findViewById(R.id.profile_veri);
        imageView = findViewById(R.id.profile_image);
        account = findViewById(R.id.profile_bank);
        ifsc = findViewById(R.id.profile_IFSC);
        mainbar = findViewById(R.id.mainbar);
        progressBarImage = findViewById(R.id.progressBarImage);
        contactH = findViewById(R.id.profile_Contact_home);
        contactP = findViewById(R.id.profile_Contact);
        bloodGroup = findViewById(R.id.profile_blood);
        religion = findViewById(R.id.profile_religion);
        nationality = findViewById(R.id.profile_Nationality);
        submit = findViewById(R.id.submit);
        progressBarImage.setVisibility(View.VISIBLE);
        if (status.equals("0")) {
            submit.setText("Verify it");
            submit.setBackgroundColor(Color.parseColor("#63F709"));
        } else {
            submit.setText("Change to not verified");
            submit.setBackgroundColor(Color.parseColor("#F5160F"));

        }
        nameT.setText(name);
        passT.setText(pass);
        reg_noT.setText(reg_no);
        dobT.setText(dob);
        categoryT.setText(category);
        genderT.setText(gender);
        if (status.equals("0")) {
            statusT.setText("Not Verified");
            statusT.setTextColor(Color.parseColor("#F5160F"));
        } else {
            statusT.setText("VeriFied");
            statusT.setTextColor(Color.parseColor("#0D47A1"));
        }
        String url = getString(R.string.ip) + imagepath;
        new BackgroundFetchedImage().execute(url);
        new BackgroundGetVariableData().execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st=statusT.getText().toString();
                int p=0;
                if(st.equals("VeriFied"))
                {
                    p=0;
                }
                else
                {
                    p=1;
                }
                new BackgroundUpdateStatus(p).execute();
            }
        });


    }

    public class BackgroundFetchedImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay = strings[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;


        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBarImage.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }

    public class BackgroundGetVariableData extends AsyncTask<String, Void, Void> {
        private String res = "";



        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "getVariableData.php";

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

                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_noT.getText().toString(), "UTF-8");

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
            mainbar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            mainbar.setVisibility(View.GONE);
            if (res != null) {
                try {
                    // Toast.makeText(ctx,"res"+res,Toast.LENGTH_LONG).show();
                    if (res.contains("not")) {
                        //  Toast.makeText(getApplicationContext(),"Update Your Details",Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("TAG", res);
                        JSONObject jobject = new JSONObject(res);
                        JSONArray jsonarray = jobject.getJSONArray("result");
                        JSONObject jsonObject = jsonarray.getJSONObject(0);
                        account.setText(jsonObject.getString("account"));
                        ifsc.setText(jsonObject.getString("ifsc"));
                        contactP.setText(jsonObject.getString("local"));
                        contactH.setText(jsonObject.getString("home"));
                        bloodGroup.setText(jsonObject.getString("blood"));
                        religion.setText(jsonObject.getString("religion"));
                        nationality.setText(jsonObject.getString("nationality"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "" + e.toString());
                }

                super.onPostExecute(aVoid);
            } else {
                Toast.makeText(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
        }


    }


    public class BackgroundUpdateStatus extends AsyncTask<String, Void, Void> {
        private String res = "";
        int status;
        public BackgroundUpdateStatus(int status)
        {
            this.status=status;
        }






        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "updateStatus.php";

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

                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_noT.getText().toString(), "UTF-8")
                        + "&" +
                        URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(""+status, "UTF-8");

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
            mainbar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Log.e("TAG","res:-"+res);
            mainbar.setVisibility(View.GONE);
            if (res != null) {
                 Toast.makeText(getApplicationContext(),"Status:"+res,Toast.LENGTH_LONG).show();
                 if(res.contains("Success"))
                 {
//                     startActivity(new Intent(getApplicationContext(),After_Admin_Login.class));
//                     finish();
                 }
                super.onPostExecute(aVoid);
            } else {
                Toast.makeText(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
