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


public class CSEFragment3 extends Fragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

    String reg_no;
    private String mParam1;
    private String mParam2;
    EditText cds,gds,cfolt,gfolt,ctw,gtw,csl,gsl,cdsl,gdsl,cand,gand,candl,gandl,cmit,gmit;
    Button submit;



    public CSEFragment3() {
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
   String string_cds,string_gds,string_cfolt,string_gfolt,string_ctw,string_gtw,
           string_csl,string_gsl,string_cdsl,string_gdsl,string_cand,string_gand,string_candl,
           string_gandl,string_cmit,string_gmit;
    Context ctx;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_csefragment3, container, false);
        cds=view.findViewById(R.id.cds);
        gds=view.findViewById(R.id.gds);
        cfolt=view.findViewById(R.id.cfolt);
        gfolt=view.findViewById(R.id.gfolt);
        ctw=view.findViewById(R.id.ctw);
        gtw=view.findViewById(R.id.gtw);
        csl=view.findViewById(R.id.csl);
        gsl=view.findViewById(R.id.gsl);
        cdsl=view.findViewById(R.id.cdsl);
        gdsl=view.findViewById(R.id.gdsl);
        cand=view.findViewById(R.id.cand);
        gand=view.findViewById(R.id.gand);
        candl=view.findViewById(R.id.candl);
        gandl=view.findViewById(R.id.gandl);
        cmit=view.findViewById(R.id.cmit);
        gmit=view.findViewById(R.id.gmit);
        submit=view.findViewById(R.id.upload);
        ctx=view.getContext();
        SharedPreferences settings = view.getContext().getSharedPreferences("id", Context.MODE_PRIVATE);
        reg_no = settings.getString("reg_no", "");
        new BackgroundCseSem3Data().execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string_cds=cds.getText().toString().trim();
                string_gds=gds.getText().toString().trim();
                string_cfolt=cfolt.getText().toString().trim();
                string_gfolt=gfolt.getText().toString().trim();
                string_ctw=ctw.getText().toString().trim();
                string_gtw=gtw.getText().toString().trim();
                string_csl=csl.getText().toString().trim();
                string_gsl=gsl.getText().toString().trim();
                string_cdsl=cdsl.getText().toString().trim();
                string_gdsl=gdsl.getText().toString().trim();
                string_cand=cand.getText().toString().trim();
                string_gand=gand.getText().toString().trim();
                string_candl=candl.getText().toString().trim();
                string_gandl=gandl.getText().toString().trim();
                string_cmit=cmit.getText().toString().trim();
                string_gmit=gmit.getText().toString().trim();
                if(string_gmit.equals(""))
                {
                    Toast.makeText(ctx,"Fill all the Field",Toast.LENGTH_LONG).show();
                }
                else
                {
                  new BackgroundCseSem3Upload().execute();
                }

            }
        });
         return view;
    }
    public class BackgroundCseSem3Upload extends AsyncTask<String, Void, Void> {
        String res;
        @Override
        protected Void doInBackground(String... strings) {

            String connectionUrl=getString(R.string.ip)+"cseSem3Upload.php";
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
                        URLEncoder.encode("cds", "UTF-8") + "=" + URLEncoder.encode(string_cds, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gds", "UTF-8") + "=" + URLEncoder.encode(string_gds, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cfolt", "UTF-8") + "=" + URLEncoder.encode(string_cfolt, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gfolt", "UTF-8") + "=" + URLEncoder.encode(string_gfolt, "UTF-8")
                        + "&"+
                        URLEncoder.encode("ctw", "UTF-8") + "=" + URLEncoder.encode(string_ctw, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gtw", "UTF-8") + "=" + URLEncoder.encode(string_gtw, "UTF-8") + "&" +

                        URLEncoder.encode("csl", "UTF-8") + "=" + URLEncoder.encode(string_csl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gsl", "UTF-8") + "=" + URLEncoder.encode(string_gsl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cdsl", "UTF-8") + "=" + URLEncoder.encode(string_cdsl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gdsl", "UTF-8") + "=" + URLEncoder.encode(string_gdsl, "UTF-8")
                        + "&"+
                        URLEncoder.encode("cand", "UTF-8") + "=" + URLEncoder.encode(string_cand, "UTF-8") +"&" +

                        URLEncoder.encode("gand", "UTF-8") + "=" + URLEncoder.encode(string_gand, "UTF-8")
                        + "&" +
                        URLEncoder.encode("candl", "UTF-8") + "=" + URLEncoder.encode(string_candl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gandl", "UTF-8") + "=" + URLEncoder.encode(string_gandl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cmit", "UTF-8") + "=" + URLEncoder.encode(string_cmit, "UTF-8")
                        + "&"+
                        URLEncoder.encode("gmit", "UTF-8") + "=" + URLEncoder.encode(string_gmit, "UTF-8");

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

    public class BackgroundCseSem3Data extends AsyncTask<String, Void, Void> {
        String res = "";

        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "getCseSem3Data.php";
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

                    Log.e("TAGGGcse3",res);
                    JSONObject jobject=new JSONObject(res);
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject =jsonarray.getJSONObject(0);
                    cds.setText(jsonObject.getString("cds"));
                    gds.setText(jsonObject.getString("gds"));
                    cfolt.setText(jsonObject.getString("cfolt"));
                    gfolt.setText(jsonObject.getString("gfolt"));
                    ctw.setText(jsonObject.getString("ctw"));
                    gtw.setText(jsonObject.getString("gtw"));
                    csl.setText(jsonObject.getString("csl"));
                    gsl.setText(jsonObject.getString("gsl"));
                    cdsl.setText(jsonObject.getString("cdsl"));
                    gdsl.setText(jsonObject.getString("gdsl"));
                    cand.setText(jsonObject.getString("cand"));
                    gand.setText(jsonObject.getString("gand"));
                    candl.setText(jsonObject.getString("candl"));
                    gandl.setText(jsonObject.getString("gandl"));
                    cmit.setText(jsonObject.getString("cmit"));
                    gmit.setText(jsonObject.getString("gmit"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAGCSE3", "" + e.toString());
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
