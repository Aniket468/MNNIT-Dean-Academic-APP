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


public class CSEFragment4 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText caa,gaa,cgc,ggc,cco,gco,cat,gat,ccit,gcit,csl,gsl,caal,gaal,catl,gatl,ccf,gcf,ccfl,gcfl;
    String string_caa,string_gaa,string_cgc,string_ggc,string_cco,string_gco,string_cat,string_gat,string_ccit,string_gcit,string_csl,string_gsl,
            string_caal,string_gaal,string_catl,string_gatl,string_ccf,string_gcf,string_ccfl,string_gcfl;

    Context context;
    Button submit;
    String reg_no;
   ProgressDialog progressDialog;
    public CSEFragment4() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CSEFragment4 newInstance(String param1, String param2) {
        CSEFragment4 fragment = new CSEFragment4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_csefragment4, container, false);
        caa=view.findViewById(R.id.caa);
        gaa=view.findViewById(R.id.gaa);
        cgc=view.findViewById(R.id.cgc);
        ggc=view.findViewById(R.id.ggc);
        cco=view.findViewById(R.id.cco);
        gco=view.findViewById(R.id.gco);
        cat=view.findViewById(R.id.cat);
        gat=view.findViewById(R.id.gat);
        ccit=view.findViewById(R.id.ccit);
        gcit=view.findViewById(R.id.gcit);
        csl=view.findViewById(R.id.csl);
        gsl=view.findViewById(R.id.gcsl);
        caal=view.findViewById(R.id.caal);
        gaal=view.findViewById(R.id.gaal);
        catl=view.findViewById(R.id.cacl);
        gatl=view.findViewById(R.id.gacl);
        ccf=view.findViewById(R.id.ccf);
        gcf=view.findViewById(R.id.gcf);
        ccfl=view.findViewById(R.id.ccfl);
        gcfl=view.findViewById(R.id.gcfl);
        submit=view.findViewById(R.id.upload);
        SharedPreferences settings = view.getContext().getSharedPreferences("id", Context.MODE_PRIVATE);
        reg_no = settings.getString("reg_no", "");
        context=view.getContext();
        new BackgroundCseSem4Data().execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              string_caa=caa.getText().toString().trim();
                string_gaa=gaa.getText().toString().trim();
                string_cgc=cgc.getText().toString().trim();
                string_ggc=ggc.getText().toString().trim();
                string_cco=cco.getText().toString().trim();
                string_gco=gco.getText().toString().trim();
                string_cat=cat.getText().toString().trim();
                string_gat=gat.getText().toString().trim();
                string_ccit=ccit.getText().toString().trim();
                string_gcit=gcit.getText().toString().trim();
                string_csl=csl.getText().toString().trim();
                string_gsl=gsl.getText().toString().trim();
                string_caal=caal.getText().toString().trim();
                string_gaal=gaal.getText().toString().trim();
                string_catl=catl.getText().toString().trim();
                string_gatl =gatl.getText().toString().trim();
                string_ccf=ccf.getText().toString().trim();
                string_gcf=gcf.getText().toString().trim();
                string_ccfl=ccfl.getText().toString().trim();
                string_gcfl=gcfl.getText().toString().trim();
                if(string_ccit.equals(""))
                {
                    Toast.makeText(context,"Fill all the fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                    new BackgroundCseSem4Upload().execute();
                }


            }
        });

        return view;
    }
    public class BackgroundCseSem4Upload extends AsyncTask<String, Void, Void> {
        String res;
        @Override
        protected Void doInBackground(String... strings) {

            String connectionUrl=getString(R.string.ip)+"cseSem4Upload.php";
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
                        URLEncoder.encode("cds", "UTF-8") + "=" + URLEncoder.encode(string_caa, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gds", "UTF-8") + "=" + URLEncoder.encode(string_gaa, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cfolt", "UTF-8") + "=" + URLEncoder.encode(string_cgc, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gfolt", "UTF-8") + "=" + URLEncoder.encode(string_ggc, "UTF-8")
                        + "&"+
                        URLEncoder.encode("ctw", "UTF-8") + "=" + URLEncoder.encode(string_cco, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gtw", "UTF-8") + "=" + URLEncoder.encode(string_gco, "UTF-8") + "&" +

                        URLEncoder.encode("csl", "UTF-8") + "=" + URLEncoder.encode(string_cat, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gsl", "UTF-8") + "=" + URLEncoder.encode(string_gat, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cdsl", "UTF-8") + "=" + URLEncoder.encode(string_ccit, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gdsl", "UTF-8") + "=" + URLEncoder.encode(string_gcit, "UTF-8")
                        + "&"+
                        URLEncoder.encode("cand", "UTF-8") + "=" + URLEncoder.encode(string_csl, "UTF-8") +"&" +

                        URLEncoder.encode("gand", "UTF-8") + "=" + URLEncoder.encode(string_gsl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("candl", "UTF-8") + "=" + URLEncoder.encode(string_caal, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gandl", "UTF-8") + "=" + URLEncoder.encode(string_gaal, "UTF-8")
                        + "&" +
                        URLEncoder.encode("cmit", "UTF-8") + "=" + URLEncoder.encode(string_catl, "UTF-8")
                        + "&"+
                        URLEncoder.encode("gmit", "UTF-8") + "=" + URLEncoder.encode(string_gatl, "UTF-8")
                        + "&" +
                        URLEncoder.encode("ccf", "UTF-8") + "=" + URLEncoder.encode(string_ccf, "UTF-8")+"&"+

                        URLEncoder.encode("gcf", "UTF-8") + "=" + URLEncoder.encode(string_gcf, "UTF-8")+"&"+

                        URLEncoder.encode("ccfl", "UTF-8") + "=" + URLEncoder.encode(string_ccfl, "UTF-8") +"&"+

                        URLEncoder.encode("gcfl", "UTF-8") + "=" + URLEncoder.encode(string_gcfl, "UTF-8");


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

            progressDialog = ProgressDialog.show(context,"Uploading","Please wait...",false,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Log.e("TAGG",res+"");


        }
    }

    public class BackgroundCseSem4Data extends AsyncTask<String, Void, Void> {
        String res = "";

        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "getCseSem4Data.php";
            try {
                URL url = new URL(connectionUrl);
                //    Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                Log.e("TAGGG2::", "hi2");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGGG2::", "hello2");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                Log.e("TAGGG2::", "here2");

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

                    Toast.makeText(context,"res"+res,Toast.LENGTH_LONG).show();
                    // string_ccp,string_gcp,string_ceg,string_geg,string_cp,string_gp,string_ccpl,string_gcpl,
                    //     string_ccsw,string_gcsw,string_cee,string_gee,string_cc,string_gc,string_cm,string_gm,string_ccl,string_gcl;

                    Log.e("TAGGGcse3",res);
                    JSONObject jobject=new JSONObject(res);
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject =jsonarray.getJSONObject(0);
                    caa.setText(jsonObject.getString("caa"));
                    gaa.setText(jsonObject.getString("gaa"));
                    cgc.setText(jsonObject.getString("cgc"));
                    ggc.setText(jsonObject.getString("ggc"));
                    cco.setText(jsonObject.getString("cco"));
                    gco.setText(jsonObject.getString("gco"));
                    cat.setText(jsonObject.getString("cat"));
                    gat.setText(jsonObject.getString("gat"));
                    ccit.setText(jsonObject.getString("ccit"));
                    gcit.setText(jsonObject.getString("gcit"));
                    csl.setText(jsonObject.getString("csl"));
                    gsl.setText(jsonObject.getString("gsl"));
                    caal.setText(jsonObject.getString("caal"));
                    gaal.setText(jsonObject.getString("gaal"));
                    catl.setText(jsonObject.getString("catl"));
                    gatl.setText(jsonObject.getString("gatl"));
                    ccf.setText(jsonObject.getString("ccf"));
                    gcf.setText(jsonObject.getString("gcf"));
                    ccfl.setText(jsonObject.getString("ccfl"));
                    gcfl.setText(jsonObject.getString("gcfl"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAGCSE3", "" + e.toString());
                }



            }
            else
            {
                Toast.makeText(context, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
            //progressDialog.dismiss();
        }


    }





}
