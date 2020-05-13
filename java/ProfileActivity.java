package com.example.aniketkumar.mnnit_portal;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yalantis.guillotine.animation.GuillotineAnimation;

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

public class ProfileActivity extends AppCompatActivity {


    String reg_no;
    DrawerLayout drawer;
    NavigationView navigationView;
    Button bProfile,login_news,registration;
    @BindView(R.id.toolbar1)
    Toolbar toolbar;
    @BindView(R.id.profile_root)


    FrameLayout root;
    @BindView(R.id.content_hamburger2)
    View contentHamburger;
    com.yalantis.guillotine.animation.GuillotineAnimation.GuillotineBuilder gui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.drawer_login);
        ButterKnife.bind(this);
        root=findViewById(R.id.profile_root);
        toolbar=findViewById(R.id.toolbar1);
        contentHamburger=findViewById(R.id.content_hamburger2);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);

        }
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.afterlogin, null);
        root.addView(guillotineMenu);

        bProfile=findViewById(R.id.bProfile);
        login_news=findViewById(R.id.login_news);
        gui=  new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger2), contentHamburger);
                gui.setClosedOnStart(true);
                gui.build();
        navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        registration=findViewById(R.id.registration);
        Fragment profileFragment=new Profile();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.frame,profileFragment);
        // Commit the Fragment replace action.
        fragmentTransaction.commit();

        bProfile.setOnClickListener(view -> {
            gui.build().close();
            Fragment profileFragment1 =new Profile();
            replaceFragment(profileFragment1);
        });
        login_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gui.build().close();
                Fragment newsFragment=new News();
                replaceFragment(newsFragment);
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences("id", Context.MODE_PRIVATE);
                reg_no=sp.getString("reg_no","0");
                checkslot();
                // Toast.makeText(getApplicationContext(),""+str,Toast.LENGTH_LONG).show();
//                Intent i=new Intent(getApplicationContext(),ChooseSlot.class);
//                startActivity(i);
                // new BackgroundCheckreg().execute();
            }
        });
        setNavigation();

    }
    public void checkslot(){
        new BackgroundCheckreg().execute();
    }
    public void replaceFragment(Fragment destFragment)
    {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.frame, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }
    public void SendMail(View view)
    {
        Intent intent =new Intent(getApplicationContext(),MailingActivity.class);
        startActivity(intent);
    }
    public void showresult(View view)
    {
        Intent intent =new Intent(getApplicationContext(),ShowResult.class);
        startActivity(intent);
    }
    public class BackgroundCheckreg extends AsyncTask<String,Void,Void> {

        private String result;
        String gender;

        @Override
        protected Void doInBackground(String... strings) {
            String connectionUrl=getString(R.string.ip)+"checkslot.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");
                Log.e("TAGG",""+data);
                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
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
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
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
            {
                //  sem1.setText(result+"5");
                //   Toast.makeText(getApplicationContext(),result+"",Toast.LENGTH_LONG).show();

                gender=result;
                Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
                if(gender.contains("1"))
                {
                    Intent i=new Intent(getApplicationContext(),GenerateForm.class);
                    startActivity(i);
                    //setslot1visible();

                }
                else{
                    startActivity(new Intent(getApplicationContext(),ChooseSlot.class));
                    //setslot2visible();
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
                    case R.id.nav_profile:
                        drawer.closeDrawers();
                        Fragment profileFragment1 =new Profile();
                        replaceFragment(profileFragment1);
                        break;
                    case R.id.nav_result:
                        Intent intent = new Intent(getApplicationContext(), ShowResult.class);
                        drawer.closeDrawers();
                        startActivity(intent);
                        return  true;
                    case R.id.nav_notifications:
                        drawer.closeDrawers();
                        Fragment news=new News();
                        replaceFragment(news);
                        break;
                    case R.id.nav_registration:
                        drawer.closeDrawers();
                        SharedPreferences sp=getSharedPreferences("id", Context.MODE_PRIVATE);
                        reg_no=sp.getString("reg_no","0");
                        checkslot();
                        break;
                    case R.id.nav_calender:
                        Intent inten =new Intent(getApplicationContext(),PDFVIEW.class);
                        drawer.closeDrawers();
                        startActivity(inten);
                        return true;
                    case R.id.nav_downloads:
                        Fragment newFragment1=new Download_Fragment();
                        drawer.closeDrawers();
                        replaceFragment(newFragment1);
                        break;
                    case R.id.nav_fee:
                        drawer.closeDrawers();
                        Fragment newFragme11=new Fee_Structure();
                        replaceFragment(newFragme11);
                        break;
                    case R.id.nav_mail:
                        drawer.closeDrawers();
                        Intent intent1 =new Intent(getApplicationContext(),MailingActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.nav_logout:
                        drawer.closeDrawers();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        return true;
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
    public void acedemics(View view)
    {
        Intent inten =new Intent(getApplicationContext(),PDFVIEW.class);
        startActivity(inten);
    }
    public void downloads(View view)
    {
        gui.build().close();
        Fragment newFragment1=new Download_Fragment();
        replaceFragment(newFragment1);
    }
    public void  fee(View view)
    {
        gui.build().close();
        Fragment newFragme11=new Fee_Structure();
        replaceFragment(newFragme11);


    }
    public void logout(View view)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    @Override
    protected void onPostResume() {
        android.view.Menu menu=navigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        if(!menuItem.isChecked())
        {
            menuItem.setChecked(true);
        }

        super.onPostResume();
    }
    @Override
    public void onBackPressed() {
        FragmentManager fm=getSupportFragmentManager();
        if(fm.getBackStackEntryCount()==0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setTitle("Warning!!!");
            builder.setMessage("Are you really want to Exit");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
        else {
            android.view.Menu menu=navigationView.getMenu();
            MenuItem menuItem=menu.getItem(0);
            if(!menuItem.isChecked())
            {
                menuItem.setChecked(true);
            }
            super.onBackPressed();
        }

    }
}
