package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class CSEFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button button;
    EditText ccp,gcp,ceg,geg,cp,gp,ccpl,gcpl,ccsw,gcsw,cee,gee,cc,gc,cm,gm,ccl,gcl;
    String string_ccp,string_gcp,string_ceg,string_geg,string_cp,string_gp,string_ccpl,string_gcpl,
            string_ccsw,string_gcsw,string_cee,string_gee,string_cc,string_gc,string_cm,string_gm,string_ccl,string_gcl;
    Context ctx;
    public CSEFragment2() {
        // Required empty public constructor
    }
    String reg_no;

   ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_csefragment2, container, false);
        ccp=view.findViewById(R.id.ccp);
        gcp=view.findViewById(R.id.gcp);
        ceg=view.findViewById(R.id.ceg);
        geg=view.findViewById(R.id.geg);
        cp=view.findViewById(R.id.cp);
        gp=view.findViewById(R.id.gp);
        ccpl=view.findViewById(R.id.ccpl);
        gcpl=view.findViewById(R.id.gcpl);
        ccsw=view.findViewById(R.id.ccsw);
        gcsw=view.findViewById(R.id.gcsw);
        cee=view.findViewById(R.id.cee);
        gee=view.findViewById(R.id.gee);
        cc=view.findViewById(R.id.cc);
        gc=view.findViewById(R.id.gc);
        ccl=view.findViewById(R.id.ccl);
        gcl=view.findViewById(R.id.gcl);
        cm=view.findViewById(R.id.cm);
        gm=view.findViewById(R.id.gm);
        ctx=view.getContext();
        button=view.findViewById(R.id.upload);
        SharedPreferences settings = view.getContext().getSharedPreferences("id", Context.MODE_PRIVATE);
        reg_no = settings.getString("reg_no", "");
        new BackgroundCseSem2Data().execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string_ccp=ccp.getText().toString().trim();
                string_gcp=gcp.getText().toString().trim();
                string_ceg=ceg.getText().toString().trim();
                string_geg=geg.getText().toString().trim();
                string_cp=cp.getText().toString().trim();
                string_gp=gp.getText().toString().trim();
                string_ccpl=ccpl.getText().toString().trim();
                string_gcpl=gcpl.getText().toString().trim();
                string_ccsw=ccsw.getText().toString().trim();
                string_gcsw=gcsw.getText().toString().trim();
                string_cee=cee.getText().toString().trim();
                string_gee=gee.getText().toString().trim();
                string_cc=cc.getText().toString().trim();
                string_gc=gc.getText().toString().trim();
                string_ccl=ccl.getText().toString().trim();
                string_gcl=gcl.getText().toString().trim();
                string_cm=cm.getText().toString().trim();
                string_gm=gm.getText().toString().trim();
                if(string_cp.equals("")||string_cm.equals("")||string_ceg.equals("")||string_cee.equals("")||string_ccsw.equals("")
                        ||string_ccpl.equals("")||string_ccl.equals("")||string_gc.equals("")||string_gcl.equals("")||string_gcp.equals("")
                        ||string_gcpl.equals("")||string_gcsw.equals("")||string_gee.equals("")||string_geg.equals("")||string_gm.equals("")
                        ||string_gp.equals(""))
                {
                    Toast.makeText(view.getContext(),"Fill All the Fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                    new BackgroundCseSem2Upload().execute();
                }
            }
        });

        return view;
    }
    public class BackgroundCseSem2Upload extends AsyncTask<String, Void, Void> {
        String res;
        @Override
        protected Void doInBackground(String... strings) {

            String connectionUrl=getString(R.string.ip)+"cseSem2Upload.php";
            Log.e("TAGG::","hello Bro");
            try {
                URL url = new URL(connectionUrl);
                Log.e("TAGG::","hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                Log.e("TAGG::","hi");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGG::","hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                Log.e("TAGG::","here1");

                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8") + "&" +
                        URLEncoder.encode("ccp", "UTF-8") + "=" + URLEncoder.encode(string_ccp, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gcp", "UTF-8") + "=" + URLEncoder.encode(string_gcp, "UTF-8")
                        + "&" +
                        URLEncoder.encode("ceg", "UTF-8") + "=" + URLEncoder.encode(string_ceg, "UTF-8")
                        + "&" +
                        URLEncoder.encode("geg", "UTF-8") + "=" + URLEncoder.encode(string_geg, "UTF-8")
                        + "&"+
                        URLEncoder.encode("cp", "UTF-8") + "=" + URLEncoder.encode(string_cp, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gp", "UTF-8") + "=" + URLEncoder.encode(string_gp, "UTF-8") + "&" +

                        URLEncoder.encode("ccpl", "UTF-8") + "=" + URLEncoder.encode(string_ccpl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gcpl", "UTF-8") + "=" + URLEncoder.encode(string_gcpl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("ccsw", "UTF-8") + "=" + URLEncoder.encode(string_ccsw, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gcsw", "UTF-8") + "=" + URLEncoder.encode(string_gcsw, "UTF-8")
                        + "&"+
                        URLEncoder.encode("cee", "UTF-8") + "=" + URLEncoder.encode(string_cee, "UTF-8") +"&" +

                        URLEncoder.encode("gee", "UTF-8") + "=" + URLEncoder.encode(string_gee, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cc", "UTF-8") + "=" + URLEncoder.encode(string_cc, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gc", "UTF-8") + "=" + URLEncoder.encode(string_gc, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cm", "UTF-8") + "=" + URLEncoder.encode(string_cm, "UTF-8")
                        + "&"+
                        URLEncoder.encode("gm", "UTF-8") + "=" + URLEncoder.encode(string_gm, "UTF-8")
                        +"&"+
                        URLEncoder.encode("ccl", "UTF-8") + "=" + URLEncoder.encode(string_ccl, "UTF-8")
                        +"&"+
                        URLEncoder.encode("gcl", "UTF-8") + "=" + URLEncoder.encode(string_gcl, "UTF-8");

                Log.d("tagg", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAGG", res + "");
            } catch (MalformedURLException e) {
                Log.e("TAGG","exception 1"+e.toString());
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.e("TAGG","exception 2"+e.toString());
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("TAGG","exception 3"+e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAGG","exception 4"+e.toString());
                e.printStackTrace();

            }
            catch (Exception e)
            {
                Log.e("TAGG",e.toString());
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

            progressDialog = ProgressDialog.show(ctx,"Uploading","Please wait...",false,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Log.e("TAGG",res+"");


        }
    }

    public class BackgroundCseSem2Data extends AsyncTask<String, Void, Void> {
        String res = "";

        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "getCseSem2Data.php";

            try {
                URL url = new URL(connectionUrl);
                //    Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                Log.e("TAGGG2::", "hi");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGGG2::", "hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                Log.e("TAGGG2::", "here1");

                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAGGG2", res + "");
            } catch (MalformedURLException e) {
                Log.e("TAG", "exception 1" + e.toString());
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.e("TAGGG", "exception 2" + e.toString());
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("TAGGG", "exception 3" + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAGGG", "exception 4" + e.toString());
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

            // progressDialog=ProgressDialog.show(ctx,"Fetching Data","Please wait...",false,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //progressBarImage.setVisibility(View.GONE);
            if (res != null) {
                try {

                     Toast.makeText(ctx,"res"+res,Toast.LENGTH_LONG).show();
                    // string_ccp,string_gcp,string_ceg,string_geg,string_cp,string_gp,string_ccpl,string_gcpl,
                    //     string_ccsw,string_gcsw,string_cee,string_gee,string_cc,string_gc,string_cm,string_gm,string_ccl,string_gcl;

                    Log.e("TAGGG",res);
                    JSONObject jobject=new JSONObject(res);
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject =jsonarray.getJSONObject(0);
                    ccp.setText(jsonObject.getString("ccp"));
                    gcp.setText(jsonObject.getString("gcp"));
                    ceg.setText(jsonObject.getString("ceg"));
                    geg.setText(jsonObject.getString("geg"));
                    cp.setText(jsonObject.getString("cp"));
                    gp.setText(jsonObject.getString("gp"));
                    ccpl.setText(jsonObject.getString("ccpl"));
                    gcpl.setText(jsonObject.getString("gcpl"));
                    ccsw.setText(jsonObject.getString("ccsw"));
                    gcsw.setText(jsonObject.getString("gcsw"));
                    cee.setText(jsonObject.getString("cee"));
                    gee.setText(jsonObject.getString("gee"));
                    cc.setText(jsonObject.getString("cc"));
                    gc.setText(jsonObject.getString("gc"));
                    cm.setText(jsonObject.getString("cm"));
                    gm.setText(jsonObject.getString("gm"));
                    ccl.setText(jsonObject.getString("ccl"));
                    gcl.setText(jsonObject.getString("gcl"));



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "" + e.toString());
                }



            }
            else
            {
                Toast.makeText(ctx, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
            //progressDialog.dismiss();
        }


    }

}
