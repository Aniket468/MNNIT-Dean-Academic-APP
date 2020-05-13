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


public class CSEFragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CSEFragment1() {
        // Required empty public constructor
    }
   //ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    EditText cem,gem,ceml,geml,ce,ge,cel,gel,cm,gm,cp,gp,cpl,gpl,cw,gw;
    Button submit;
    String reg_no;
    Context ctx;
    String string_cem,string_gem,string_ceml,string_geml,string_ce,string_ge,string_cel,string_gel,string_gw,string_cm,string_gm,string_cp,string_gp,string_cpl,string_gpl,string_cw;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_csefragment1, container, false);
        cem=view.findViewById(R.id.cem);
        gem=view.findViewById(R.id.gem);
        ceml=view.findViewById(R.id.ceml);
        geml=view.findViewById(R.id.geml);
        ce=view.findViewById(R.id.ce);
        ge=view.findViewById(R.id.ge);
        cel=view.findViewById(R.id.cel);
        gel=view.findViewById(R.id.gel);
        cm=view.findViewById(R.id.cm);
        gm=view.findViewById(R.id.gm);
        cp=view.findViewById(R.id.cp);
        gp=view.findViewById(R.id.gp);
        gpl=view.findViewById(R.id.gpl);
        cpl=view.findViewById(R.id.cpl);
        cw=view.findViewById(R.id.cw);
        gw=view.findViewById(R.id.gw);
        submit=view.findViewById(R.id.upload);
        ctx=getContext();
        SharedPreferences settings = ctx.getSharedPreferences("id", Context.MODE_PRIVATE);
        reg_no = settings.getString("reg_no", "");
        Log.e("TAGG",reg_no);
        new BackgroundCseSem1Data().execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 string_cem=cem.getText().toString().trim();
                 string_gem=gem.getText().toString().trim();
                 string_ceml=ceml.getText().toString().trim();
                 string_geml=geml.getText().toString().trim();
                string_ce=ce.getText().toString().trim();
                string_ge=ge.getText().toString().trim();
                 string_cel=cel.getText().toString().trim();
                 string_gel=gel.getText().toString().trim();
                string_cm=cm.getText().toString().trim();
                 string_gm=gm.getText().toString().trim();
                 string_cp=cp.getText().toString().trim();
                 string_gp=gp.getText().toString().trim();
                 string_cpl=cpl.getText().toString().trim();
                 string_gpl=gpl.getText().toString().trim();
                 string_cw=cw.getText().toString().trim();
                 string_gw=gw.getText().toString().trim();
                if(string_ce.equals("")||string_cel.equals("")||string_cem.equals("")||string_ceml.equals("")||
                        string_cm.equals("")||string_cp.equals("")||string_cpl.equals("")||string_cw.equals("")||
                        string_ge.equals("")||string_gel.equals("")||string_gem.equals("")||string_gem.equals("")||
                        string_gm.equals("")||string_gp.equals("")||string_gpl.equals("")||string_gw.equals("")||string_geml.equals(""))
                {
                    Toast.makeText(view.getContext(),"Are you Playing :( "+"\n"+"Fill All the field first",Toast.LENGTH_LONG).show();
                }
                else
                {
                      new BackgroundCseSem1Upload().execute();
                }

            }
        });
        return view;
    }
    public class BackgroundCseSem1Upload extends AsyncTask<String, Void, Void> {
        String res;
        @Override
        protected Void doInBackground(String... strings) {

            String connectionUrl=getString(R.string.ip)+"cseSem1Upload.php";
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
                        URLEncoder.encode("cem", "UTF-8") + "=" + URLEncoder.encode(string_cem, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gem", "UTF-8") + "=" + URLEncoder.encode(string_gem, "UTF-8")
                        + "&" +
                        URLEncoder.encode("ceml", "UTF-8") + "=" + URLEncoder.encode(string_ceml, "UTF-8")
                        + "&" +
                        URLEncoder.encode("geml", "UTF-8") + "=" + URLEncoder.encode(string_geml, "UTF-8")
                        + "&"+
                        URLEncoder.encode("ce", "UTF-8") + "=" + URLEncoder.encode(string_ce, "UTF-8")
                        + "&" +
                        URLEncoder.encode("ge", "UTF-8") + "=" + URLEncoder.encode(string_ge, "UTF-8") + "&" +

                        URLEncoder.encode("cel", "UTF-8") + "=" + URLEncoder.encode(string_cel, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gel", "UTF-8") + "=" + URLEncoder.encode(string_gel, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cm", "UTF-8") + "=" + URLEncoder.encode(string_cm, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gm", "UTF-8") + "=" + URLEncoder.encode(string_gm, "UTF-8")
                        + "&"+
                        URLEncoder.encode("cp", "UTF-8") + "=" + URLEncoder.encode(string_cp, "UTF-8") +"&" +

                        URLEncoder.encode("gp", "UTF-8") + "=" + URLEncoder.encode(string_gp, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cpl", "UTF-8") + "=" + URLEncoder.encode(string_cpl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gpl", "UTF-8") + "=" + URLEncoder.encode(string_gpl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cw", "UTF-8") + "=" + URLEncoder.encode(string_cw, "UTF-8")
                        + "&"+
                        URLEncoder.encode("gw", "UTF-8") + "=" + URLEncoder.encode(string_gw, "UTF-8");
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

            //progressDialog = ProgressDialog.show(ctx,"Uploading","Please wait...",false,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            super.onPostExecute(aVoid);
            //progressDialog.dismiss();
            Log.e("TAGG",res+"");
            Toast.makeText(ctx,res,Toast.LENGTH_SHORT).show();

        }
    }

    public class BackgroundCseSem1Data extends AsyncTask<String, Void, Void> {
        String res = "";

        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "getCseSem1Data.php";


            try {
                URL url = new URL(connectionUrl);
                //    Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                Log.e("TAGG::", "hi");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                 Log.e("TAGG::", "hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                 Log.e("TAGG::", "here1");

                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");


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

           // progressDialog=ProgressDialog.show(ctx,"Fetching Data","Please wait...",false,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //progressBarImage.setVisibility(View.GONE);
            if (res != null) {
                try {

                    // Toast.makeText(ctx,"res"+res,Toast.LENGTH_LONG).show();
                    Log.e("TAG",res);
                    JSONObject jobject=new JSONObject(res);
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject =jsonarray.getJSONObject(0);
                    cem.setText(jsonObject.getString("cem"));
                    gem.setText(jsonObject.getString("gem"));
                    ceml.setText(jsonObject.getString("ceml"));
                    geml.setText(jsonObject.getString("geml"));
                    ce.setText(jsonObject.getString("ce"));
                    ge.setText(jsonObject.getString("ge"));
                    cel.setText(jsonObject.getString("cel"));
                    gel.setText(jsonObject.getString("gel"));
                    cm.setText(jsonObject.getString("cm"));
                    gm.setText(jsonObject.getString("gm"));
                    cp.setText(jsonObject.getString("cp"));
                    gp.setText(jsonObject.getString("gm"));
                    cpl.setText(jsonObject.getString("cpl"));
                    gpl.setText(jsonObject.getString("gpl"));
                    cw.setText(jsonObject.getString("cw"));
                    gw.setText(jsonObject.getString("gw"));


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
