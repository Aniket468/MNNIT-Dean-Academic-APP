package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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

public class News_update extends AppCompatActivity {

    String encode1="",encode2="",encode3="",encode4="",encode5="";
    String description="";
    String linkdes1="",linkdes2="",linkdes3="",linkdes4="",linkdes5="";
    EditText Edescription;
    EditText hint1,hint2,hint3,hint4,hint5;
    TextView name1,name2,name3,name4,name5;
    int p1=0,p2=0,p3=0,p4=0,p5=0;
    LinearLayout l1,l2,l3;
    ProgressDialog progressDialog;
    RadioButton one,two,three,zero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_news_update);
        Edescription=findViewById(R.id.description);
        hint1=findViewById(R.id.hint1);
        hint2=findViewById(R.id.hint2);
        hint3=findViewById(R.id.hint3);
        name1=findViewById(R.id.attachment1);
        name2=findViewById(R.id.attachment2);
        name3=findViewById(R.id.attachment3);
        l1=findViewById(R.id.attach1);
        l2=findViewById(R.id.attach2);
        l3=findViewById(R.id.attach3);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        zero=findViewById(R.id.zero);
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.GONE);
                l3.setVisibility(View.GONE);
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);
                l3.setVisibility(View.GONE);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.GONE);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.VISIBLE);
            }
        });

    }
    public void attach1(View view)
    {
         choose(1);
    }
    public void attach2(View view)
    {
        choose(2);
    }
    public void attach3(View view)
    {
        choose(3);
    }


    public void choose(int i)
    {
        Intent intent = new Intent();

        intent.setType("pdf/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Attachment"), i);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TAG1", "" + resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            name1.setText(uri.getLastPathSegment());
            Log.e("TAG",uri.getPath());


            try {
                InputStream iStream =   getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                encode1= android.util.Base64.encodeToString(bytes,0);
                Toast.makeText(getApplicationContext(),"PDF ENCODED",Toast.LENGTH_LONG).show();
                Log.e("TAG","Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1",e.toString());
            } catch (IOException e) {
                Log.e("TAG2",e.toString());

            }


        }
        else if(requestCode==2&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            Uri uri=data.getData();
            name2.setText(uri.getLastPathSegment());
            Log.e("TAG",uri.getPath());


            try {
                InputStream iStream =   getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                encode2= android.util.Base64.encodeToString(bytes,0);
                Toast.makeText(getApplicationContext(),"PDF ENCODED",Toast.LENGTH_LONG).show();
                Log.e("TAG","Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1",e.toString());
            } catch (IOException e) {
                Log.e("TAG2",e.toString());

            }
        }
        else if(requestCode==3)
        {
            Uri uri=data.getData();
            name3.setText(uri.getLastPathSegment());
            Log.e("TAG",uri.getPath());


            try {
                InputStream iStream =   getContentResolver().openInputStream(uri);
                byte[] bytes = getBytes(iStream);
                encode3= android.util.Base64.encodeToString(bytes,0);
                Toast.makeText(getApplicationContext(),"PDF ENCODED",Toast.LENGTH_LONG).show();
                Log.e("TAG","Pdf Encoded");
            } catch (FileNotFoundException e) {

                Log.e("TAG1",e.toString());
            } catch (IOException e) {
                Log.e("TAG2",e.toString());

            }
        }



    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public void submit(View view)
    {
        description=Edescription.getText().toString();
        linkdes1=hint1.getText().toString();
        linkdes2=hint2.getText().toString();
        linkdes3=hint3.getText().toString();
        description=description.trim();
        linkdes1=linkdes1.trim();
        linkdes2=linkdes2.trim();
        linkdes3=linkdes3.trim();
      if(!description.equals("")) {
          if (zero.isChecked()) {
              new BackgroundNewsUpdate().execute();
          } else if (one.isChecked()) {
              if (encode1.equals("") && linkdes1.equals("")) {
                  Toast.makeText(getApplicationContext(), "You have to Select one Attachment", Toast.LENGTH_LONG).show();
              } else {
                  new BackgroundNewsUpdate().execute();
              }
          } else if (two.isChecked()) {
              if (encode1.equals("") && linkdes1.equals("") && encode2.equals("") && linkdes2.equals("")) {
                  Toast.makeText(getApplicationContext(), "You have to Select two Attachment and fill their des", Toast.LENGTH_LONG).show();
              } else {
                  new BackgroundNewsUpdate().execute();
              }
          } else if (three.isChecked()) {
              if (encode1.equals("") && linkdes1.equals("") && encode2.equals("") && linkdes2.equals("") && encode3.equals("") && linkdes3.equals("")) {
                  Toast.makeText(getApplicationContext(), "You have to Select 3 Attachment and fill all des", Toast.LENGTH_LONG).show();
              } else {
                  new BackgroundNewsUpdate().execute();
              }
          }
      }
      else
      {
        Toast.makeText(getApplicationContext(),"Without  Description can't be uploaded",Toast.LENGTH_LONG).show();
      }
    }
    public class BackgroundNewsUpdate extends AsyncTask<String, Void, Void> {
        String res;
        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"updateNews.php";
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
                String data="";
                if(zero.isChecked())
                {
                    data = URLEncoder.encode("count", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");

                }
                else if(one.isChecked())
                {
                     data = URLEncoder.encode("count", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + "&" +
                            URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8")+ "&" +
                             URLEncoder.encode("attachment1", "UTF-8") + "=" + URLEncoder.encode(encode1, "UTF-8") + "&" +
                             URLEncoder.encode("link1", "UTF-8") + "=" + URLEncoder.encode(linkdes1, "UTF-8");

                }
                else if(two.isChecked())
                {
                    data = URLEncoder.encode("count", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8") + "&" +
                            URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8")+ "&" +
                            URLEncoder.encode("attachment1", "UTF-8") + "=" + URLEncoder.encode(encode1, "UTF-8") + "&" +
                            URLEncoder.encode("link1", "UTF-8") + "=" + URLEncoder.encode(linkdes1, "UTF-8")+ "&" +
                            URLEncoder.encode("attachment2", "UTF-8") + "=" + URLEncoder.encode(encode2, "UTF-8") + "&" +
                            URLEncoder.encode("link2", "UTF-8") + "=" + URLEncoder.encode(linkdes2, "UTF-8");

                }
                else if(one.isChecked())
                {
                    data = URLEncoder.encode("count", "UTF-8") + "=" + URLEncoder.encode("3", "UTF-8") + "&" +
                            URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8")+ "&" +
                            URLEncoder.encode("attachment1", "UTF-8") + "=" + URLEncoder.encode(encode1, "UTF-8") + "&" +
                            URLEncoder.encode("link1", "UTF-8") + "=" + URLEncoder.encode(linkdes1, "UTF-8") +"&" +
                            URLEncoder.encode("attachment2", "UTF-8") + "=" + URLEncoder.encode(encode2, "UTF-8") + "&" +
                            URLEncoder.encode("link2", "UTF-8") + "=" + URLEncoder.encode(linkdes2, "UTF-8")+ "&" +
                            URLEncoder.encode("attachment3", "UTF-8") + "=" + URLEncoder.encode(encode3, "UTF-8") + "&" +
                            URLEncoder.encode("link3", "UTF-8") + "=" + URLEncoder.encode(linkdes3, "UTF-8");

                }
                 Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG", res + "");
            } catch (MalformedURLException e) {
                Log.e("TAG","exception 1"+e.toString());
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.e("TAG","exception 2"+e.toString());
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("TAG","exception 3"+e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAG","exception 4"+e.toString());
                e.printStackTrace();

            }
            catch (Exception e)
            {
                Log.e("TAG",e.toString());
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

            progressDialog = ProgressDialog.show(News_update.this,"Uploading","Please wait...",false,false);

            Toast.makeText(getApplicationContext(),"Uploading",Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();


            super.onPostExecute(aVoid);
            Log.e("tag",res+"");
            Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();

        }
    }

}
