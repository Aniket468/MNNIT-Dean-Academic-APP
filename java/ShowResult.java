package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.ArrayList;

public class ShowResult extends AppCompatActivity {

    String reg_no;
    ProgressDialog progressDialog;
    TextView cem,gem,ceml,geml,ce,ge,cel,gel,cm,gm,cp,gp,cpl,gpl,cw,gw;
    TextView ccp,gcp,ceg,geg,cp1,gp1,ccpl,gcpl,ccsw,gcsw,cee,gee,cc,gc,cm1,gm1,ccl,gcl;
    TextView cds,gds,cfolt,gfolt,ctw,gtw,csl,gsl,cdsl,gdsl,cand,gand,candl,gandl,cmit,gmit;
    TextView caa,gaa,cgc,ggc,cco,gco,cat,gat,ccit,gcit,csl1,gsl1,caal,gaal,catl,gatl,ccf,gcf,ccfl,gcfl;
    LinearLayout layout_sem1,layout_sem_2,layout_sem_3,layout_sem_4;
    TextView spi1,spi2,spi3,spi4;
    ArrayList<Entry> yvalue;
    ArrayList<Entry> yvalu2;
    LineChart lineChart;
    Button transcript;
    String scem1,sgem1,sceml1,sgeml1,sce1,sge1,scel1,sgel1,scm11,sgm11,scw1,sgw1,scp1,sgp1,scpl1,sgpl1;
    String ssccp1,ssgcp1,ssceg1,ssgeg1,sscp11,ssgp11,ssccpl1,ssgcpl1,ssccsw1,ssgcsw1,sscee1,ssgee1,sscc1,ssgc1,ssccl1,ssgcl1,sscm1,ssgm1;
    String string_cds,string_gds,string_cfolt,string_gfolt,string_ctw,string_gtw,
            string_csl,string_gsl,string_cdsl,string_gdsl,string_cand,string_gand,string_candl,
            string_gandl,string_cmit,string_gmit;
    String string_caa,string_gaa,string_cgc,string_ggc,string_cco,string_gco,string_cat,string_gat,string_ccit,string_gcit,sstring_csl,sstring_gsl,
            string_caal,string_gaal,string_catl,string_gatl,string_ccf,string_gcf,string_ccfl,string_gcfl;

    float p1=0,p2=0,p3=0,P4=0,p5=0;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_result);
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        reg_no = sharedPreferences.getString("reg_no", "0");
        name=sharedPreferences.getString("name","0");
        transcript=findViewById(R.id.transcript);
        layout_sem1=findViewById(R.id.sem1);
        spi1=findViewById(R.id.spi1);
        cem=findViewById(R.id.cem);
        gem=findViewById(R.id.gem);
        ceml=findViewById(R.id.ceml);
        geml=findViewById(R.id.geml);
        ce=findViewById(R.id.ce);
        ge=findViewById(R.id.ge);
        cel=findViewById(R.id.cel);
        gel=findViewById(R.id.gel);
        cm=findViewById(R.id.cm1);
        gm=findViewById(R.id.gm1);
        cp=findViewById(R.id.cp);
        gp=findViewById(R.id.gp);
        gpl=findViewById(R.id.gpl);
        cpl=findViewById(R.id.cpl);
        cw=findViewById(R.id.cw);
        gw=findViewById(R.id.gw);
        //sem 2 data
        ccp=findViewById(R.id.ccp);
        gcp=findViewById(R.id.gcp);
        ceg=findViewById(R.id.ceg);
        geg=findViewById(R.id.geg);
        cp1=findViewById(R.id.cp1);
        gp1=findViewById(R.id.gp1);
        ccpl=findViewById(R.id.ccpl);
        gcpl=findViewById(R.id.gcpl);
        ccsw=findViewById(R.id.ccsw);
        gcsw=findViewById(R.id.gcsw);
        cee=findViewById(R.id.cee);
        gee=findViewById(R.id.gee);
        cc=findViewById(R.id.cc);
        gc=findViewById(R.id.gc);
        ccl=findViewById(R.id.ccl);
        gcl=findViewById(R.id.gcl);
        cm1=findViewById(R.id.cm);
        gm1=findViewById(R.id.gm);

        //sem3
        cds=findViewById(R.id.cds);
        gds=findViewById(R.id.gds);
        cfolt=findViewById(R.id.cfolt);
        gfolt=findViewById(R.id.gfolt);
        ctw=findViewById(R.id.ctw);
        gtw=findViewById(R.id.gtw);
        csl=findViewById(R.id.csl);
        gsl=findViewById(R.id.gsl);
        cdsl=findViewById(R.id.cdsl);
        gdsl=findViewById(R.id.gdsl);
        cand=findViewById(R.id.cand);
        gand=findViewById(R.id.gand);
        candl=findViewById(R.id.candl);
        gandl=findViewById(R.id.gandl);
        cmit=findViewById(R.id.cmit);
        gmit=findViewById(R.id.gmit);

        //semester 4

        caa=findViewById(R.id.caa);
        gaa=findViewById(R.id.gaa);
        cgc=findViewById(R.id.cgc);
        ggc=findViewById(R.id.ggc);
        cco=findViewById(R.id.cco);
        gco=findViewById(R.id.gco);
        cat=findViewById(R.id.cat);
        gat=findViewById(R.id.gat);
        ccit=findViewById(R.id.ccit);
        gcit=findViewById(R.id.gcit);
        csl1=findViewById(R.id.csl1);
        gsl1=findViewById(R.id.gcsl);
        caal=findViewById(R.id.caal);
        gaal=findViewById(R.id.gaal);
        catl=findViewById(R.id.cacl);
        gatl=findViewById(R.id.gacl);
        ccf=findViewById(R.id.ccf);
        gcf=findViewById(R.id.gcf);
        ccfl=findViewById(R.id.ccfl);
        gcfl=findViewById(R.id.gcfl);
        spi4=findViewById(R.id.spi4);

        layout_sem_4=findViewById(R.id.sem4);
        layout_sem_4.setVisibility(View.GONE);
        layout_sem_3=findViewById(R.id.sem3);
        spi3=findViewById(R.id.spi3);
        layout_sem_3.setVisibility(View.GONE);
        layout_sem_2=findViewById(R.id.sem2);
        spi2=findViewById(R.id.spi2);
        layout_sem1.setVisibility(View.GONE);
        layout_sem_2.setVisibility(View.GONE);
        lineChart=findViewById(R.id.linechart);
        lineChart.setScaleEnabled(false);
        lineChart.setDragEnabled(true);
        yvalue=new ArrayList<>();
        yvalu2=new ArrayList<>();
       new BackgroundCseSem1Data().execute();
       new BackgroundCseSem2Data().execute();
       new BackgroundCseSem3Data().execute();
       new BackgroundCseSem4Data().execute();

        SharedPreferences sp=getSharedPreferences("id", Context.MODE_PRIVATE);
        String name1=sp.getString("name","0");
        String reg_no1=sp.getString("reg_no","0");
        String branch="Computer Science And Engineering";
        String degree="Btech";
        transcript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = Environment.getExternalStorageDirectory().toString();
                File folder = new File(str, "Transcript");
                if (folder.exists()) {
                    Log.e("TAG","Folder Exist");
                } else {
                    folder.mkdir();
                    Log.e("TAG","Folder Created");
                }
                File file = new File(folder, name+".pdf");
                try {
                    if (file.createNewFile()) {
                        Log.e("TAG","File Creatint");
                    } else {
                        Log.e("TAG","File exits");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("TAGG","4:::"+e);
                }
                Log.e("TAG", "" + file.getAbsolutePath());

                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                document.open();
                Paragraph p=new Paragraph("Motilal Nehru National Institute Of Technology \n Allahabad--211004\n\n");
                p.setAlignment(Element.ALIGN_CENTER);
                try {
                    document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Paragraph q=new Paragraph("Name                : "+name1+"\n"+"Registration No : "+reg_no1+"\n"+"Branch                : "+branch+"\n"
                        +"Degree               : "+degree+"\n\n\n");
                try {
                    document.add(q);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                if(layout_sem1.getVisibility()==View.VISIBLE){
                    PdfPTable table=gettranssem1();
                    try {
                        Paragraph w;
                        w=new Paragraph("Semester 1 Gradesheet"+" ( "+reg_no+" )\n\n");
                        w.setAlignment(Element.ALIGN_CENTER);
                        document.add(w);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    try {
                        document.add(table);

                        try {
                            document.add(new Paragraph("\nSPI of Semester 1 is "+p1));
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        document.add(new Paragraph("\n\n\n\n"));
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                }
                if(layout_sem_2.getVisibility()==View.VISIBLE)
                {
                    PdfPTable table=gettranssem2();
                    try {
                        Paragraph p1;
                        p1=new Paragraph("Semester 2 Gradesheet"+" ( "+reg_no+" )\n\n");
                        p1.setAlignment(Element.ALIGN_CENTER);
                        document.add(p1);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    try {
                        document.add(table);

                        try {
                            document.add(new Paragraph("\nSPI of Semester 2 is "+p2));
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n"));
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }

                if(layout_sem_3.getVisibility()==View.VISIBLE)
                {
                    PdfPTable table=gettranssem3();
                    try {
                        Paragraph w;
                        w=new Paragraph("Semester 3 Gradesheet"+" ( "+reg_no+" )\n\n");
                        w.setAlignment(Element.ALIGN_CENTER);
                        document.add(w);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    try {
                        document.add(table);

                        try {
                            document.add(new Paragraph("\nSPI of Semester 3 is "+p3));
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        document.add(new Paragraph("\n\n\n\n"));
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }

                if(layout_sem_4.getVisibility()==View.VISIBLE)
                {
                    PdfPTable table=gettranssem4();
                    try {
                        Paragraph w;
                        w=new Paragraph("Semester 4 Gradesheet"+" ( "+reg_no+" )\n\n");
                        w.setAlignment(Element.ALIGN_CENTER);
                        document.add(w);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    try {
                        document.add(table);

                        try {
                            document.add(new Paragraph("\nSPI of Semester 4 is "+p5));
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        document.add(new Paragraph("\n\n\n\n"));
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
                document.close();
                Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();
            }

        });

    }

    public class BackgroundCseSem1Data extends AsyncTask<String, Void, Void> {
        String res = "";

        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "getCseSem1Data.php";


            try {
                URL url = new URL(connectionUrl);

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

            progressDialog= ProgressDialog.show(ShowResult.this,"Fetching Data","Please wait...",false,false);
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
                    Log.e("TAGGG",jsonObject.getString("cem"));
                    //Toast.makeText(getApplicationContext(),""+jsonObject.getString("cem"),Toast.LENGTH_LONG).show();
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
                    if(!(gw.getText().toString().trim().equals("null"))&&!(gw.getText().toString().equals("empty"))){
                        layout_sem1.setVisibility(View.VISIBLE);
                        calculateSpi();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "" + e.toString());
                }



            }
            else
            {
                Toast.makeText(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

        }
        private void calculateSpi()
        {
            int creditEM=Integer.valueOf(cem.getText().toString());
            int creditEML=Integer.valueOf(ceml.getText().toString());
            int creditEL=Integer.valueOf(ce.getText().toString());
            int creditELL=Integer.valueOf(cel.getText().toString());
            int creditM=Integer.valueOf(cm.getText().toString());
            int creditW=Integer.valueOf(cw.getText().toString());
            int creditP=Integer.valueOf(cp.getText().toString());
            int creditPL=Integer.valueOf(cpl.getText().toString());
            int TotalCredit=creditEL+creditELL+creditEM+creditEML+creditM+creditP+creditPL+creditW;
            int obtainedPointer=creditEL*GradeVal(ge.getText().toString())+creditELL*GradeVal(gel.getText().toString())+
                    creditEM*GradeVal(gem.getText().toString())
                    +creditEML*GradeVal(geml.getText().toString())+creditM*GradeVal(gm.getText().toString())+
                    creditP*GradeVal(gp.getText().toString())+creditPL*GradeVal(gp.getText().toString())+
                    creditW*GradeVal(gw.getText().toString());
            p1=(float)obtainedPointer/TotalCredit;
            spi1.setText(" SPI  "+p1);
            yvalue.add(new Entry(1,p1));
            yvalu2.add(new Entry(1,p1));

        }


    }
    public int GradeVal(String val)
    {
        if(val.equalsIgnoreCase("A+"))
        {
            return 10;
        }
        else if(val.equalsIgnoreCase("A"))
            return 9;
        else if(val.equalsIgnoreCase("B+"))
            return 8;
        else if(val.equalsIgnoreCase("B"))
        {
            return 7;
        }
        else if(val.equalsIgnoreCase("C"))
        {
            return 6;
        }
        else if(val.equalsIgnoreCase("D"))
        {
            return 5;
        }
        else return 4;

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


                    Log.e("TAGGG",res);
                    JSONObject jobject=new JSONObject(res);
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject =jsonarray.getJSONObject(0);
                    ccp.setText(jsonObject.getString("ccp"));
                    gcp.setText(jsonObject.getString("gcp"));
                    ceg.setText(jsonObject.getString("ceg"));
                    geg.setText(jsonObject.getString("geg"));
                    cp1.setText(jsonObject.getString("cp"));
                    gp1.setText(jsonObject.getString("gp"));
                    ccpl.setText(jsonObject.getString("ccpl"));
                    gcpl.setText(jsonObject.getString("gcpl"));
                    ccsw.setText(jsonObject.getString("ccsw"));
                    gcsw.setText(jsonObject.getString("gcsw"));
                    cee.setText(jsonObject.getString("cee"));
                    gee.setText(jsonObject.getString("gee"));
                    cc.setText(jsonObject.getString("cc"));
                    gc.setText(jsonObject.getString("gc"));
                    cm1.setText(jsonObject.getString("cm"));
                    gm1.setText(jsonObject.getString("gm"));
                    ccl.setText(jsonObject.getString("ccl"));
                    gcl.setText(jsonObject.getString("gcl"));
                    if(!(cc.getText().toString().trim().equals("null"))&&!((cc.getText().toString().trim().equals("empty")))) {
                        layout_sem_2.setVisibility(View.VISIBLE);
                        calculateSpi();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "" + e.toString());
                }



            }
            else
            {
                Toast.makeText(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
            //progressDialog.dismiss();
        }
        private void calculateSpi()
        {
         //   TextView ccp,gcp,ceg,geg,cp1,gp1,ccpl,gcpl,ccsw,gcsw,cee,gee,cc,gc,cm1,gm1,ccl,gcl;
            int c1=Integer.parseInt(ccp.getText().toString());
            int c2=Integer.parseInt(ceg.getText().toString());
            int c3=Integer.parseInt(cpl.getText().toString());
            int c4=Integer.parseInt(ccpl.getText().toString());
            int c5=Integer.parseInt(ccsw.getText().toString());
            int c6=Integer.parseInt(cee.getText().toString());
            int c7=Integer.parseInt(cc.getText().toString());
            int c8=Integer.parseInt(cm1.getText().toString());
            int c9=Integer.parseInt(ccl.getText().toString());
            int Total_credit=c1+c2+c3+c4+c5+c6+c7+c8+c9;
            Log.e("TAGGG","Total Credit "+Total_credit);
            int Obtained_credit=c1*GradeVal(gcp.getText().toString())+c2*GradeVal(geg.getText().toString())+c3*GradeVal(gp1.getText().toString())+
                    c4*GradeVal(gcpl.getText().toString())+c5*GradeVal(gcsw.getText().toString())+c6*GradeVal(gee.getText().toString())+c7*GradeVal(gc.getText().toString())+
                    c8*GradeVal(gm1.getText().toString())+c9*GradeVal(gcl.getText().toString());
             p2=(float)Obtained_credit/Total_credit;
            Log.e("TAGGG","Obtained Credit "+Obtained_credit);
            spi2.setText("SPI    "+p2);
            yvalue.add(new Entry(2,p2));
            float cpi=(p1+p2)/2;
            yvalu2.add(new Entry(2,cpi));



        }


    }
    public void graphSet()
    {
        LineDataSet set1= new LineDataSet(yvalue,"SPI");
        LineDataSet set2=new LineDataSet(yvalu2,"CPI");
        set2.setColor(Color.parseColor("#ADEA0A"));
        set2.setCircleColor(Color.parseColor("#EE0D88"));
        ArrayList<ILineDataSet> dataset=new ArrayList<>();
        dataset.add(set1);
        dataset.add(set2);

        LineData lineData =new LineData(dataset);
        lineChart.setData(lineData);
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

                    Toast.makeText(getApplicationContext(),"res"+res,Toast.LENGTH_LONG).show();
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
                    if(!(gmit.getText().toString().trim().equals("null"))&&!((gmit.getText().toString().trim().equals("empty")))) {
                        layout_sem_3.setVisibility(View.VISIBLE);
                        calculateSpi();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAGCSE3", "" + e.toString());
                }



            }
            else
            {
                Toast.makeText(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
            //progressDialog.dismiss();

        }
        private void  calculateSpi()
       {

           int c1= Integer.parseInt(cds.getText().toString());
           int c2=Integer.parseInt(cfolt.getText().toString());
           int c3=Integer.parseInt(ctw.getText().toString());
           int c4=Integer.parseInt(csl.getText().toString());
           int c5=Integer.parseInt(cdsl.getText().toString());
           int c6=Integer.parseInt(cand.getText().toString());
           int c7=Integer.parseInt(candl.getText().toString());
           int c8=Integer.parseInt(cmit.getText().toString());
           int total_credit=c1+c2+c3+c4+c5+c6+c7+c8;
           int Obtained_pointer=c1*GradeVal(gds.getText().toString())+c2*GradeVal(gfolt.getText().toString())
                   +c3*GradeVal(gtw.getText().toString())+c4*GradeVal(gsl.getText().toString())+c5*GradeVal(gdsl.getText().toString())+c6*GradeVal(gand.getText().toString())+c7*GradeVal(gandl.getText().toString())+c8*GradeVal(gmit.getText().toString());
           p3=(float)Obtained_pointer/total_credit;
           spi3.setText("SPI    "+p2);
           yvalue.add(new Entry(3,p3));
           float cpi=(p1+p2+p3)/3;
           yvalu2.add(new Entry(3,cpi));


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

                    Toast.makeText(getApplicationContext(), "res" + res, Toast.LENGTH_LONG).show();
                    // string_ccp,string_gcp,string_ceg,string_geg,string_cp,string_gp,string_ccpl,string_gcpl,
                    //     string_ccsw,string_gcsw,string_cee,string_gee,string_cc,string_gc,string_cm,string_gm,string_ccl,string_gcl;

                    Log.e("TAGGGcse3", res);
                    JSONObject jobject = new JSONObject(res);
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject = jsonarray.getJSONObject(0);
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
                    csl1.setText(jsonObject.getString("csl"));
                    gsl1.setText(jsonObject.getString("gsl"));
                    caal.setText(jsonObject.getString("caal"));
                    gaal.setText(jsonObject.getString("gaal"));
                    catl.setText(jsonObject.getString("catl"));
                    gatl.setText(jsonObject.getString("gatl"));
                    ccf.setText(jsonObject.getString("ccf"));
                    gcf.setText(jsonObject.getString("gcf"));
                    ccfl.setText(jsonObject.getString("ccfl"));
                    gcfl.setText(jsonObject.getString("gcfl"));

                    if (!(gcfl.getText().toString().trim().equals("null")) && !((gcfl.getText().toString().trim().equals("empty")))) {
                        layout_sem_4.setVisibility(View.VISIBLE);
                        calculateSpi();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAGCSE3", "" + e.toString());
                }


            } else {
                Toast.makeText(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
            if(yvalu2.size()>0)
            graphSet();
            super.onPostExecute(aVoid);
            //progressDialog.dismiss();
        }


        private void calculateSpi() {
            int c1 = Integer.parseInt(caa.getText().toString());
            int c2 = Integer.parseInt(cgc.getText().toString());
            int c3 = Integer.parseInt(cco.getText().toString());
            int c4 = Integer.parseInt(cat.getText().toString());
            int c5 = Integer.parseInt(ccit.getText().toString());
            int c6 = Integer.parseInt(csl1.getText().toString());
            int c7 = Integer.parseInt(caal.getText().toString());
            int c8 = Integer.parseInt(catl.getText().toString());
            int c9 = Integer.parseInt(ccf.getText().toString());
            int c10 = Integer.parseInt(ccfl.getText().toString());
            int TotalCredit = c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8 + c9 + c10;
            int ObtainedPointer = c1 * GradeVal(gaa.getText().toString()) + c2 * GradeVal(ggc.getText().toString()) + c3 * GradeVal(gco.getText().toString())
                    + c4 * GradeVal(gat.getText().toString()) + c5 * GradeVal(gcit.getText().toString()) + c6 * GradeVal(gsl1.getText().toString()) +
                    c7 * GradeVal(gaal.getText().toString()) + c8 * GradeVal(gatl.getText().toString()) + c9 * GradeVal(gcf.getText().toString()) +
                    c10 * GradeVal(gcfl.getText().toString());
            p5=(float)ObtainedPointer/TotalCredit;
            spi4.setText("SPI "+p5);
            yvalue.add(new Entry(4,p5));
            float cpi=(p1+p2+p3+p5)/4;
            yvalu2.add(new Entry(4,cpi));

        }



    }

    public PdfPTable gettranssem1(){
        scem1=cem.getText().toString();
        sgem1=gem.getText().toString();
        sceml1=ceml.getText().toString();
        scel1=cel.getText().toString();
        sgeml1=geml.getText().toString();
        sce1=ce.getText().toString();
        sge1=ge.getText().toString();
        sgel1=gel.getText().toString();
        scm11=cm.getText().toString();
        sgm11=gm.getText().toString();
        scw1=cw.getText().toString();
        sgw1=gw.getText().toString();
        scp1=cp.getText().toString();
        sgp1=gp.getText().toString();
        scpl1=cpl.getText().toString();
        sgpl1=gpl.getText().toString();
        String []subject={"Engineering Mechanics","Engineering Mechanics Lab","English Language","Language Lab",
                "Mathematics1","Workshop","Physics-1","Physics-Lab"};
        String []credit={scem1,sceml1,sce1,scel1,scm11,scw1,scp1,scpl1};
        String []grade={sgem1,sgeml1,sge1,sgel1,sgm11,sgw1,sgp1,sgpl1};

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        // table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Subjects");
        table.addCell("Credits");
        table.addCell("Grades");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i=0;i<7;i++){
            table.addCell(subject[i]);
            table.addCell(credit[i]);
            table.addCell(grade[i]);
        }

        return table;
    }

    public PdfPTable gettranssem2(){
        ssccp1=ccp.getText().toString();
        ssgcp1=gcp.getText().toString();
        ssceg1=ceg.getText().toString();
        ssgeg1=geg.getText().toString();
        sscp11=cp1.getText().toString();
        ssgp11=gp1.getText().toString();
        ssccpl1=ccpl.getText().toString();
        ssgcpl1=gcpl.getText().toString();
        ssccsw1=ccsw.getText().toString();
        ssgcsw1=gcsw.getText().toString();
        sscee1=cee.getText().toString();
        ssgee1=gee.getText().toString();
        sscc1=cc.getText().toString();
        ssgc1=gc.getText().toString();
        ssccl1=ccl.getText().toString();
        ssgcl1=gcl.getText().toString();
        sscm1=cm.getText().toString();
        ssgm1=gm.getText().toString();
        String []subject={"Computer Programming","Engineering Graphics","Physics II","Computer Programming Lab",
                "Communication Skills Workshop","Environment and Ecology","Chemistry","Chemistry-Lab","Mathematics II"};
        String []credit={ssccp1,ssceg1,sscp11,ssccpl1,ssccsw1,sscee1,sscc1,ssccl1,sscm1,};
        String []grade={ssgcp1,ssgeg1,ssgp11,ssgcpl1,ssgcsw1,ssgee1,ssgc1,ssgcl1,ssgm1};

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        // table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Subjects");
        table.addCell("Credits");
        table.addCell("Grades");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i=0;i<9;i++){
            table.addCell(subject[i]);
            table.addCell(credit[i]);
            table.addCell(grade[i]);
        }

        return table;
    }

    public PdfPTable gettranssem3(){
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
        String []subject={"Data Structures","Foundation of Logical Thought","Technical Writing","Programming Tools",
                "Data Structure Lab","Analog And Digital Electronics","Analog And Digital Electronics Lab","Management of IT Industries"};
        String []credit={string_cds,string_cfolt,string_ctw,
                string_csl,string_cdsl,string_cand,string_candl,
                string_cmit};
        String []grade={string_gds,string_gfolt,string_gtw,
                string_gsl,string_gdsl,string_gand,
                string_gandl,string_gmit};

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        // table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Subjects");
        table.addCell("Credits");
        table.addCell("Grades");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i=0;i<8;i++){
            table.addCell(subject[i]);
            table.addCell(credit[i]);
            table.addCell(grade[i]);
        }

        return table;
    }

    public PdfPTable gettranssem4(){
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
        sstring_csl=csl.getText().toString().trim();
        sstring_gsl=gsl.getText().toString().trim();
        string_caal=caal.getText().toString().trim();
        string_gaal=gaal.getText().toString().trim();
        string_catl=catl.getText().toString().trim();
        string_gatl =gatl.getText().toString().trim();
        string_ccf=ccf.getText().toString().trim();
        string_gcf=gcf.getText().toString().trim();
        string_ccfl=ccfl.getText().toString().trim();
        string_gcfl=gcfl.getText().toString().trim();
        String []subject={"Data Structures","Foundation of Logical Thought","Technical Writing","Programming Tools",
                "Data Structure Lab","Analog And Digital Electronics","Analog And Digital Electronics Lab","Management of IT Industries"};
        String []credit={string_caa,string_cgc,string_cco,string_cat,string_ccit,sstring_csl,
                string_caal,string_catl,string_ccf,string_ccfl};
        String []grade={string_gaa,string_ggc,string_gco,string_gat,string_gcit,sstring_gsl,
                string_gaal,string_gatl,string_gcf,string_gcfl};

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        // table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Subjects");
        table.addCell("Credits");
        table.addCell("Grades");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i=0;i<8;i++){
            table.addCell(subject[i]);
            table.addCell(credit[i]);
            table.addCell(grade[i]);
        }

        return table;
    }

}
