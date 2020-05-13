package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.guillotine.animation.GuillotineAnimation;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class After_Admin_Login extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 200;

    Button bProfile,login_news;
    @BindView(R.id.toolbar1)
    Toolbar toolbar;
    @BindView(R.id.profile_root)
    Button registration;
    FrameLayout root;
    @BindView(R.id.content_hamburger3)
    View contentHamburger;
    GuillotineAnimation.GuillotineBuilder gui;
    String Data;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ProgressDialog progressDialog;
    TextView tu1,vu,nvu;
    CardView total,verified,not_verified;
    ProgressBar progressBar;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Warning!!!");
        builder.setMessage("Hey Admin are you want to logout : ? ");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_drawer_layout);
        Bundle bundle = getIntent().getExtras();


        ButterKnife.bind(this);
        root=findViewById(R.id.profile_root);
        toolbar=findViewById(R.id.toolbar1);
        tu1=findViewById(R.id.tu);
        vu=findViewById(R.id.vu);
        nvu=findViewById(R.id.nvu);

        contentHamburger=findViewById(R.id.content_hamburger3);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);

        }


     View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.admin_menu, null);
        root.addView(guillotineMenu);
        gui=  new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger3), contentHamburger);
        gui.setStartDelay(RIPPLE_DURATION);
        gui.setActionBarViewForAnimation(toolbar);
        gui.setClosedOnStart(true);
        gui.build();
        registration=findViewById(R.id.registration);
        registration.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),AdminRegistrationDetails.class);
            startActivity(i);
        });
        if(bundle!=null)
        Data = bundle.getString("json");
        if(Data==null||bundle==null)
        {
            new DataUpdate().execute();
        }

        navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setItemIconTintList(null);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        total=findViewById(R.id.total_user);
        verified=findViewById(R.id.verified_user);
        not_verified=findViewById(R.id.not_verified_user);
        progressBar=findViewById(R.id.progressBar);

        setInvisible();
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AdminActivity.class);
                intent.putExtra("json",Data);
                intent.putExtra("check","total");
                startActivity(intent);
            }
        });
        verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AdminActivity.class);
                intent.putExtra("json",Data);
                intent.putExtra("check","verified");
                startActivity(intent);
            }
        });
        not_verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AdminActivity.class);
                intent.putExtra("json",Data);
                intent.putExtra("check","not_verified");
                startActivity(intent);
            }
        });
        new TotalBackgroundTask().execute();
        setNavigation();
    }
    public void adminlogout(View view)
    {
        Intent in=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
        finish();
    }
    public void updateCalender(View view)
    {
        gui.build().close();
        Intent intent =new Intent(getApplicationContext(),UpdateCalender.class);
        startActivity(intent);

    }
    public void alluser(View view)
    {
        gui.build().close();
        Intent intent =new Intent(getApplicationContext(),AdminActivity.class);
        intent.putExtra("json",Data);
        intent.putExtra("check","total");
        startActivity(intent);
    }

    public void updateNotification(View view)
    {
       Intent intent=new Intent(getApplicationContext(),News_update.class);
       startActivity(intent);
    }
    public class TotalBackgroundTask extends AsyncTask<String,Void,Void> {

        private String result;
        String gender;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"getno.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.e("TAG",result+"");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
            //
            //progressDialog = ProgressDialog.show(getApplicationContext(),"Sending message","Please wait...",false,false);
            setInvisible();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // progressDialog.dismiss();
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
            if(result==null)
            {
                Toast.makeText(getApplicationContext(),"connection error",Toast.LENGTH_LONG).show();
            }
            else if(result.contains("Failed"))
            {
                Toast.makeText(getApplicationContext(),"Authentication error",Toast.LENGTH_LONG).show();
            }
             else
//            {
//                String [] str=result.split(" ");
//                tu1.setText(str[0]);
//                nvu.setText(str[1]);
//                vu.setText(str[2]);
//            }
            {
                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                tu1.setText(result);
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray=null;
                try {
                    jsonArray=jsonObject.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject1 = null;
                try {
                     jsonObject1=jsonArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tu1.setText(jsonObject1.getString("total"));
                    vu.setText(jsonObject1.getString("verified"));
                    nvu.setText(jsonObject1.getString("notverified"));
                    setVisible();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private void setNavigation()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_user:

                        Intent intent =new Intent(getApplicationContext(),AdminActivity.class);
                        intent.putExtra("json",Data);
                        intent.putExtra("check","total");
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        return true;
                     case R.id.updatedetails:
                        Intent inten =new Intent(getApplicationContext(),UpdateCalender.class);
                        startActivity(inten);
                        drawerLayout.closeDrawers();
                        return true;
                     case R.id.registrationDetails:
                        Intent i=new Intent(getApplicationContext(),AdminRegistrationDetails.class);
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        return true;
                     case R.id.updatenotification:
                         Intent inte=new Intent(getApplicationContext(),News_update.class);
                         startActivity(inte);
                         drawerLayout.closeDrawers();
                         return true;
                    case R.id.logout:
                        drawerLayout.closeDrawers();
                        Intent in=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(in);
                        finish();
                        return  true;


                    default:

                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                //menuItem.setChecked(true);



                return true;
            }
        });
    }

     public void setVisible()
     {
         progressBar.setVisibility(View.GONE);
         total.setVisibility(View.VISIBLE);
         verified.setVisibility(View.VISIBLE);
         not_verified.setVisibility(View.VISIBLE);
     }
     public  void  setInvisible()
     {
         progressBar.setVisibility(View.VISIBLE);
         total.setVisibility(View.GONE);
         verified.setVisibility(View.GONE);
         not_verified.setVisibility(View.GONE);
     }

    @Override
    protected void onPostResume() {
        new TotalBackgroundTask().execute();
        new DataUpdate().execute();
        super.onPostResume();
    }
    public class DataUpdate extends AsyncTask<String,Void,Void> {

        private String result;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"adlogin1.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG",result+"");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //progressDialog.dismiss();
            if(result==null)
            {
                Toast.makeText(getApplicationContext(),"Weak connection",Toast.LENGTH_LONG).show();
            }
            else if(result.contains("Failed"))
            {
               Toast.makeText(getApplicationContext(),"Server Not Responding",Toast.LENGTH_LONG).show();
            }
            else
            {
                //   Toast.makeText(getApplicationContext(),result+"",Toast.LENGTH_LONG).show();
                Data=result;
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
