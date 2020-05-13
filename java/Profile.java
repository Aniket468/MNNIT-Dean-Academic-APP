package com.example.aniketkumar.mnnit_portal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Context context;
    ProgressDialog progressDialog;

    public Profile() {
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    String regId;
    ProgressBar progressBarImage;
    LinearLayout fixedData, variableData;
    TextView name, regNo, dob, category,gender;
    CircleImageView imageView;
    EditText account, ifsc, contactH, contactP, bloodGroup, religion, nationality;
    Context ctx;
    Button submit;
    Bitmap b=null;
    FloatingActionButton camera;
    String encodeImage="12";
    ProgressBar mainBar;
    Bitmap bitmap;
    Button editProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View retView = inflater.inflate(R.layout.fragment_profile, container, false);
        ctx = retView.getContext();
        progressBarImage = retView.findViewById(R.id.progressBarImage);
        mainBar=retView.findViewById(R.id.mainbar);
        mainBar.setVisibility(View.GONE);
        context=retView.getContext();
        fixedData = retView.findViewById(R.id.fixedData);//Fixed  data layout4
        fixedData.setVisibility(View.GONE);
        variableData = retView.findViewById(R.id.variableData); // Variable data Layout
        variableData.setVisibility(View.GONE);
        camera=retView.findViewById(R.id.profile_camera);
        editProfile=retView.findViewById(R.id.edit_profile);
        //fixed layout item begin here
        name = retView.findViewById(R.id.profile_name);
        regNo = retView.findViewById(R.id.profile_regNum);
        dob = retView.findViewById(R.id.profile_dob);
        gender=retView.findViewById(R.id.profile_gender);
        category = retView.findViewById(R.id.profile_category);
        imageView = retView.findViewById(R.id.profile_image);
        imageView.setVisibility(View.VISIBLE);
        progressBarImage.setVisibility(View.VISIBLE);
        //fixed end here

        //variable layout item begin here

        account = retView.findViewById(R.id.profile_bank);
        ifsc = retView.findViewById(R.id.profile_IFSC);
        contactH = retView.findViewById(R.id.profile_Contact_home);
        contactP = retView.findViewById(R.id.profile_Contact);
        bloodGroup = retView.findViewById(R.id.profile_blood);
        religion = retView.findViewById(R.id.profile_religion);
        nationality = retView.findViewById(R.id.profile_Nationality);
        submit=retView.findViewById(R.id.submit);
        account.setEnabled(false);
        ifsc.setEnabled(false);
        contactH.setEnabled(false);
        contactP.setEnabled(false);
        bloodGroup.setEnabled(false);
        religion.setEnabled(false);
        nationality.setEnabled(false);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account.isEnabled())
                {
                    editProfile.setText("Click for Edit");
                    account.setEnabled(false);
                    ifsc.setEnabled(false);
                    contactH.setEnabled(false);
                    contactP.setEnabled(false);
                    bloodGroup.setEnabled(false);
                    religion.setEnabled(false);
                    nationality.setEnabled(false);
                }
                else {
                    editProfile.setText("Click for non Non Edit");
                    account.setEnabled(true);
                    ifsc.setEnabled(true);
                    contactH.setEnabled(true);
                    contactP.setEnabled(true);
                    bloodGroup.setEnabled(true);
                    religion.setEnabled(true);
                    nationality.setEnabled(true);
                }

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundUpdate().execute();
            }
        });

        // variable layout item end here
        //filling the fixed layout filled.
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap=convertImageViewToBitmap(imageView);
                bitmap=Bitmap.createScaledBitmap(bitmap, 900, 1100, true);
                ImageDialog cdd=new ImageDialog(ctx,bitmap);
                cdd.show();
            }
        });
        SharedPreferences sharedPreferences = retView.getContext().getSharedPreferences("id", MODE_PRIVATE);
        regId = sharedPreferences.getString("reg_no", "0");
        new BackgroundGetFixedData().execute();
        new BackgroundGetVariableData().execute();
        return retView;
    }
    private Bitmap convertImageViewToBitmap(ImageView v){

        Bitmap bm=((BitmapDrawable)v.getDrawable()).getBitmap();

        return bm;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TAG1", "" + resultCode);
        if (data != null && resultCode == RESULT_OK && requestCode == 0) {
            super.onActivityResult(requestCode, resultCode, data);
            b = (Bitmap) data.getExtras().get("data");
            Encode_Image_Bitmap encode_image=new Encode_Image_Bitmap();
            encode_image.execute();

            Log.e("TAGGG","camera Clicked");

            imageView.setImageBitmap(b);

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
            progressDialog=progressDialog.show(context,"Wait","Please Wait a while",false,false);
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                b = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                b.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] array = stream.toByteArray();
                encodeImage= Base64.encodeToString(array, 0);
                Log.e("TAG",encodeImage);
                bitmap = Bitmap.createScaledBitmap(b, 300, 400, true);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            imageView.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    }

    public class BackgroundGetFixedData extends AsyncTask<String, Void, Void> {
        String res = "";


        @Override
        protected Void doInBackground(String... strings) {


            String connectionUrl = getString(R.string.ip) + "getFixedData1.php";

           // Log.e("TAGG::", "hello Bro");
            try {
                URL url = new URL(connectionUrl);
            //    Log.e("TAGG::", "hello hello");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                //Log.e("TAGG::", "hi");

                httpURLConnection.setDoOutput(true);
                OutputStream out = httpURLConnection.getOutputStream();
               // Log.e("TAGG::", "hello");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

               // Log.e("TAGG::", "here1");

                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(regId, "UTF-8");

              //  Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                //Log.d("TAG", res + "");
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
            progressDialog=progressDialog.show(context,"Fetching Data","Please Wait a while",false,false);

            fixedData.setVisibility(View.GONE);
            progressBarImage.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //progressBarImage.setVisibility(View.GONE);
            fixedData.setVisibility(View.VISIBLE);
            if (res != null) {
                try {

                    // Toast.makeText(ctx,"res"+res,Toast.LENGTH_LONG).show();
                    Log.e("TAG",res);
                    JSONObject jobject=new JSONObject(res);
                    JSONArray jsonarray = jobject.getJSONArray("result");
                    JSONObject jsonObject =jsonarray.getJSONObject(0);
                    String url = jsonObject.getString("imagepath");
                    String im1 = getString(R.string.ip) + url;
                    String nam=jsonObject.getString("name");
                    String slot=jsonObject.getString("slot");
                    SharedPreferences sharedPreferences=ctx.getSharedPreferences("id", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("name",nam);
                    editor.putString("slot",slot);
                    editor.putString("dob",jsonObject.getString("dob"));
                    editor.apply();
                    name.setText(nam);
                    regNo.setText(regId);
                    category.setText(jsonObject.getString("category"));
                    gender.setText(jsonObject.getString("gender"));
                    dob.setText(jsonObject.getString("dob"));
                    Log.e("TAG",im1);

                    //problem in image fetching
                    new BackgroundFetchedImage().execute((im1));
                    Thread.sleep(20);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "" + e.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                super.onPostExecute(aVoid);
            }
            else
            {
                Toast.makeText(ctx, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
           progressDialog.dismiss();
        }


    }


    public class BackgroundFetchedImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay = strings[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                 mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;


        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            // progressDialog.dismiss();
            progressBarImage.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onPreExecute() {
           // progressDialog.show(context,"Fetching Data","Please Wait",false,false);
            super.onPreExecute();
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

                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(regId, "UTF-8");

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
           // progressDialog.show(context,"Fetching Data","Please Wait",false,false);
           variableData.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {


           variableData.setVisibility(View.VISIBLE);
            if (res != null) {
                try {
                    // Toast.makeText(ctx,"res"+res,Toast.LENGTH_LONG).show();
                    if (res.contains("not")) {
                      Toast.makeText(ctx,"Update Your Details",Toast.LENGTH_LONG).show();
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
                    } catch(JSONException e){
                        e.printStackTrace();
                        Log.e("TAG", "" + e.toString());
                    }

                super.onPostExecute(aVoid);
            }
            else
            {
                Toast.makeText(ctx, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
            }
            //progressDialog.dismiss();
        }


    }

    public class BackgroundUpdate extends AsyncTask<String, Void, Void> {
        String res;
        String strAccount=account.getText().toString();
        String strIfsc=ifsc.getText().toString();
        String strPersonal=contactP.getText().toString();
        String strHome=contactH.getText().toString();
        String strBlood=bloodGroup.getText().toString();
        String strReligion=religion.getText().toString();
        String strNationality=nationality.getText().toString();
        @Override
        protected Void doInBackground(String... strings) {

            String connectionUrl=getString(R.string.ip)+"update.php";

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

                String data = URLEncoder.encode("account", "UTF-8") + "=" + URLEncoder.encode(strAccount, "UTF-8") + "&" +
                        URLEncoder.encode("ifsc", "UTF-8") + "=" + URLEncoder.encode(strIfsc, "UTF-8")
                        + "&" +
                        URLEncoder.encode("home", "UTF-8") + "=" + URLEncoder.encode(strHome, "UTF-8")
                        + "&" +
                        URLEncoder.encode("personal", "UTF-8") + "=" + URLEncoder.encode(strPersonal, "UTF-8")
                        + "&" +
                        URLEncoder.encode("blood", "UTF-8") + "=" + URLEncoder.encode(strBlood, "UTF-8")
                        + "&" +
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(regId, "UTF-8")
                        + "&" +
                        URLEncoder.encode("religion", "UTF-8") + "=" + URLEncoder.encode(strReligion, "UTF-8")
                        + "&" +
                        URLEncoder.encode("pic", "UTF-8") + "=" + URLEncoder.encode(encodeImage, "UTF-8")
                        + "&" +
                        URLEncoder.encode("nationality", "UTF-8") + "=" + URLEncoder.encode(strNationality, "UTF-8");
                Log.e("data", data);
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
           progressDialog= progressDialog.show(context,"Updating","Please Wait",false,false);
            fixedData.setVisibility(View.GONE);
            variableData.setVisibility(View.GONE);
            mainBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            fixedData.setVisibility(View.VISIBLE);
            variableData.setVisibility(View.VISIBLE);
            mainBar.setVisibility(View.GONE);
            super.onPostExecute(aVoid);
        }
    }
}
