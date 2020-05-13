package com.example.aniketkumar.mnnit_portal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.itextpdf.text.BaseColor.GREEN;

public class GenerateForm extends AppCompatActivity {
    Button understudent1,underparent1,undervehicle1,generate1;
    String fileUrl;
    Paragraph p;
    String form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_generate_form);
        understudent1=findViewById(R.id.understudent);
        underparent1=findViewById(R.id.underparent);
        undervehicle1=findViewById(R.id.undervehicle);
        generate1=findViewById(R.id.generate);

        understudent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileUrl = getString(R.string.ip) + "Data/forms/undertakingstudent.pdf";
                form ="undertaking Student";
                new BackgroundPDFDownload().execute();
            }
        });
        underparent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileUrl = getString(R.string.ip) + "Data/forms/undertakingparent.pdf";
                form ="undertaking Parents";
                new BackgroundPDFDownload().execute();
            }
        });
        undervehicle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileUrl = getString(R.string.ip) + "Data/forms/undertakingvehicle.pdf";
                form="undertaking vehicle";
                new BackgroundPDFDownload().execute();
            }
        });
        generate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences("id", Context.MODE_PRIVATE);
                String name,reg_no,dob,branch="",refno,slot,program;
                name=sp.getString("name","0");
                reg_no=sp.getString("reg_no","0");
                dob=sp.getString("dob","0");
                slot=sp.getString("slot","0");
                if(slot=="0")
                {
                    slot="- - - - - - - - - -";
                 }
                 if(slot.contains("slot1"))
                 {
                     slot="slot 1 (9:00 am - 12:00 pm)";
                 }
                if(slot.contains("slot2"))
                {
                    slot="slot 2 (1 pm - 2:00 pm)";
                }
                if(slot.contains("slot3"))
                {
                    slot="slot 3 (3:00 pm - 6:00 pm)";
                }

                if(reg_no.substring(4,5).equals("4"))
                {
                    branch="Computer Science and Engineering";
                }
                if(reg_no.substring(4,5).equals("1"))
                {
                    branch="Civil Engineering";
                }
                if(reg_no.substring(4,5).equals("2"))
                {
                    branch="Electrical Engineering";
                }
                if(reg_no.substring(4,5).equals("3"))
                {
                    branch="Mechanical Engineering";
                }
                if(reg_no.substring(4,5).equals("5"))
                {
                    branch="Electronics and Communication Engineering";
                }
                if(reg_no.substring(4,5).equals("0"))
                {
                    branch="Biotech Engineering";
                }
                if(reg_no.substring(4,5).equals("6"))
                {
                    branch="Production and Industrial Engineering";
                }
                if(reg_no.substring(4,5).equals("7"))
                {
                    branch="Chemical Engineering";
                }
                if(reg_no.substring(4,5).equals("8"))
                {
                    branch="Information Technology";
                }


                String str = Environment.getExternalStorageDirectory().toString();
                File folder = new File(str, "mnnit1");
                if (folder.exists()) {
                    Log.e("TAG","Folder Exist");
                } else {
                    folder.mkdir();
                    Log.e("TAG","Folder Created");
                }
                File file = new File(folder, "RegistrationForms.pdf");

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
                PdfPTable table = new PdfPTable(new float[] { 2, 3 });
                // table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                String [] data={name,reg_no,dob,branch,slot,"BTech","KNP214BC2A1D"};
                String [] fields={"Name","Registration no.","Date of Birth","Branch","Slot Choosen","Programme","Reference No."};
                for (int i=0;i<7;i++) {
                    table.addCell(fields[i]);
                    table.addCell(data[i]);
                }
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<7;i++) {
                    PdfPCell[] cells1 = table.getRow(i).getCells();
                    for(int j=0;j<2;j++)
                    {
                        cells1[j].setPadding(10);
                        cells1[j].setHorizontalAlignment(Element.ALIGN_CENTER);
                        cells1[j].setBorderColor(BaseColor.BLACK);
                        if(j==0)
                        {
                            cells1[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
                        }
                        //cells1[j].setBorder(10);
                    }
                }
                document.open();

                try {

//                    Image i = Image.getInstance("drawable://"+R.drawable.home1);
//                           Chunk c = new Chunk(i, 0, -24);
//                    document.add(c);
                    document.add(new Paragraph("\n"));
                    p = new Paragraph(new Paragraph("Motilal Nehru National Institute Of Technology\nAllahabad-211004\n" +
                            "Office of the (Dean Academics)\n\n"));
                    p.setAlignment(Element.ALIGN_CENTER);
                    document.add(p);
                    p=new Paragraph("NOTE: 75% attendance will be counted from the alloted date of registration irrespective of actual date of registration in all the" +
                            "subjects.\n\n" +
                            "Declaration: I will abide by the rules and regulation of Institute related to 75% mandatory attendance in all the subjects.\n\n" +
                            "I hereby certify that the above information is true to the best of my Knowledge:\n\n\n" +
                            "Signature of the Student: ____________________ Date: ___________________");
                   // document.setMargins(30,30,20,20);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                try {
                    document.add(table);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                try {
                    document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                document.close();
                Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();


            }
        });
    }

    public class BackgroundPDFDownload extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String[] objects) {
            InputStream inputStream;
            try {

                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.e("TAG", "" + "yes");
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String str = Environment.getExternalStorageDirectory().toString();
                File folder = new File(str, "mnnit1");

                if (folder.exists()) {
                    Log.e("TAG","Folder Exist");
                } else {
                    folder.mkdir();
                    Log.e("TAG","Folder Created");
                }
                File file = new File(folder, form+".pdf");

                if (file.createNewFile()) {
                    Log.e("TAG","File Creatint");
                } else {
                    Log.e("TAG","File exits");
                }
                Log.e("TAG", "" + file.getAbsolutePath());
                FileOutputStream f = new FileOutputStream(file);
                byte[] buffer = new byte[1024*1024];
                int len = 0;

                while ((len = inputStream.read(buffer)) > 0) {
                    f.write(buffer, 0, len);
                    Log.e("TAG","hi");
                }
                Log.e("TAG",""+buffer);
                f.flush();
                f.getFD().sync();
                f.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }


        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Downloading",Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(),"Download finished",Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }
}
