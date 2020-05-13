package com.example.aniketkumar.mnnit_portal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageView cal, genderImage;
    EditText edate;
    Button register;
    ProgressBar progressBar;
           // mainprogress;
    FloatingActionButton camera;

    CoordinatorLayout coordinatorLayout;
    private  EditText reg_name, reg_password, reg_cPassword,reg_num;
    private  RadioGroup category1;
    private  RadioButton male, female;
    private String name, password, cPassword, date, gender, category,reg_no,encodeImage;
    private CircleImageView circle;
    private Bitmap b=null;
    ProgressDialog progressDialog;
    int counter=1;
    Bitmap bitmap1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //back button on toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.signupToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        cal = findViewById(R.id.calender);
        edate = findViewById(R.id.date);
        register = findViewById(R.id.register);
        reg_num=findViewById(R.id.reg_regNumber);
        reg_name = findViewById(R.id.reg_name);
        reg_password = findViewById(R.id.reg_password);
        reg_cPassword = findViewById(R.id.reg_cPassword);
        genderImage = findViewById(R.id.genderImage);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        camera = findViewById(R.id.camera);
        circle = findViewById(R.id.profile_image);
        coordinatorLayout =findViewById(R.id.container);
//        mainprogress=findViewById(R.id.mainprogress);
//        mainprogress.setVisibility(View.VISIBLE);
//        mainprogress.setVisibility(View.GONE);
        category1 = (RadioGroup) findViewById(R.id.category);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calender fragment loads for date picking
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Signup.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });


//        circle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(b!=null)
//                {
//                   imageRoate();
//                   counter++;
//                }
//            }
//        });

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bitmap1=convertImageViewToBitmap(circle);
                bitmap1=Bitmap.createScaledBitmap(bitmap1, 900, 1100, true);
                ImageDialog cdd=new ImageDialog(Signup.this,bitmap1);
                cdd.show();
            }
        });



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Select upload option..");
                builder.setMessage("Choose one......")
                        .setCancelable(true)
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 0);
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                Intent intent = new Intent();

                                intent.setType("image/*");

                                intent.setAction(Intent.ACTION_GET_CONTENT);

                                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trim function used to remove the trailing and leading space of a string
                name = reg_name.getText().toString();
                name = name.trim();
                password = reg_password.getText().toString();
                password = password.trim();
                cPassword = reg_cPassword.getText().toString();
                cPassword = cPassword.trim();
                date = edate.getText().toString();
                date = date.trim();
                reg_no=reg_num.getText().toString();
                reg_no=reg_no.trim();

                if (name.equals("") || password.equals("") || cPassword.equals("") | date.equals("")||reg_no.equals("")) {

                    Toast.makeText(getApplicationContext(), "Fill all the Credentials", Toast.LENGTH_LONG).show();
                } else if (!password.equals(cPassword)) {
                    Toast.makeText(getApplicationContext(), "Password and Confirm password Mismatch!", Toast.LENGTH_LONG).show();

                }
                else if(!isDateValid(date))
                {
                    Toast.makeText(getApplicationContext(), "Select date from celendar icon or write in proper format!", Toast.LENGTH_LONG).show();
                }
                else if (gender==null) {
                    Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_LONG).show();
                } else if (category==null) {
                    Toast.makeText(getApplicationContext(), "Select Category !", Toast.LENGTH_LONG).show();

                } else if(b==null){
                    Toast.makeText(getApplicationContext(), "Select Profile_Pic !", Toast.LENGTH_LONG).show();
                }
                else {
                    if(password.length()<8)
                    {
                        Toast.makeText(getApplicationContext(),"Password must be atleast of 8 characters ",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(reg_no.length()<4)
                            Toast.makeText(getApplicationContext(),"Invalid Registration Number !!",Toast.LENGTH_LONG).show();
                        else{
                            BackgroundRegistration backgroundRegistration=new BackgroundRegistration();
                            backgroundRegistration.execute();
                        }
                    }


                }


            }
        });
        category1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.general:
                        category = "General";
                        break;
                    case R.id.obc:
                        category = "OBC";
                        break;
                    case R.id.sc:
                        category = "SC/ST";
                        break;
                    case R.id.minority:
                        category = "Minority";
                        break;
                }

            }
        });


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Male";
                // Toast.makeText(getApplicationContext(),"male",Toast.LENGTH_LONG).show();
                genderImage.setImageResource(0);
                genderImage.setImageResource(R.drawable.male);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Female";
                //Toast.makeText(getApplicationContext(),"Female",Toast.LENGTH_LONG).show();
                genderImage.setImageResource(0);
                genderImage.setImageResource(R.drawable.female);
            }
        });


    }

    private Bitmap convertImageViewToBitmap(ImageView v){

        Bitmap bm=((BitmapDrawable)v.getDrawable()).getBitmap();

        return bm;
    }

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public void imageRoate(){

        circle.setRotation(counter*90);

    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        edate.setText(date);
    }

    //for back button on toolbar
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    //overriding for setting the image from (gallery or camera )
    //here requestCode 0 for camera and 1 or gallery (Request code is passed from calling function ) see camera.setOnclickListener..
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TAG1", "" + resultCode);
        if (data != null && resultCode == RESULT_OK && requestCode == 0) {
            super.onActivityResult(requestCode, resultCode, data);
            b = (Bitmap) data.getExtras().get("data");
            bitmap1=b;
            circle.setImageBitmap(b);
            Encode_Image_Bitmap encode_image_bitmap=new Encode_Image_Bitmap();
            encode_image_bitmap.execute();
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

           Encode_image encode_image=new Encode_image(uri);
           encode_image.execute();


        }
    }
    private  class Encode_Image_Bitmap extends  AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            b.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] array = stream.toByteArray();
            encodeImage= Base64.encodeToString(array, 0);
            return null;
        }
    }

       private class Encode_image extends AsyncTask<Void, Void, Void> {
        Uri uri;
        Bitmap bitmap;
        public Encode_image(Uri uri)
        {
            this.uri=uri;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                b.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] array = stream.toByteArray();
                encodeImage= Base64.encodeToString(array, 0);
                Log.e("TAG",encodeImage);
                bitmap = Bitmap.createScaledBitmap(b, 300, 400, true);
                //bitmap=RotateBitmap(bitmap,270);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            bitmap1=bitmap;
            circle.setImageBitmap(bitmap);
        }
    }

    public class BackgroundRegistration extends AsyncTask<String, Void, Void> {
    String res;


        @Override
        protected Void doInBackground(String... strings) {





            String connectionUrl=getString(R.string.ip)+"register.php";

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

                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8")
                        + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
                        + "&" +
                        URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")
                        + "&" +
                        URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8")
                        + "&"+
                        URLEncoder.encode("pic", "UTF-8") + "=" + URLEncoder.encode(encodeImage, "UTF-8")
                        + "&" +
                        URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(category, "UTF-8");
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

//            coordinatorLayout.setVisibility(View.GONE);
//            mainprogress.setVisibility(View.VISIBLE);
            progressDialog =progressDialog.show(Signup.this,"Sending Data","Please Wait a While",false,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

//            mainprogress.setVisibility(View.GONE);
//            coordinatorLayout.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
            if(res==null)
            {
                Toast.makeText(getApplicationContext(),"Server not Responding",Toast.LENGTH_LONG).show();
            }
            else
                 if(res.contains("SUCCESS"))
                 {
                     Toast.makeText(getApplicationContext(),"Great you are SuccessFully Register",Toast.LENGTH_LONG).show();
                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(),"Choose another Reg_no",Toast.LENGTH_LONG).show();
                 }
        }
    }

}