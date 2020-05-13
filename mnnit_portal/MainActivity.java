package com.example.aniketkumar.mnnit_portal;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Menu {

    private static final long RIPPLE_DURATION = 250;
    com.yalantis.guillotine.animation.GuillotineAnimation.GuillotineBuilder gui;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    View guillotineMenu;
    NavigationView navigationView;
    RecyclerView recyclerView;
    Button logReg;
    DrawerLayout drawer;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.drawer_logout);
        ButterKnife.bind(this);
        root = findViewById(R.id.root);
        toolbar = findViewById(R.id.toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("number", Context.MODE_PRIVATE);
        String p = sharedPreferences.getString("count", "0");
        //getApplicationContext().startForegroundService(new Intent(getBaseContext(), NotificationService.class));
        if (p.equals("0")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("count", "0");
            editor.apply();
        }

        new BackGroundNotificationCheck().execute();

        contentHamburger = findViewById(R.id.content_hamburger);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);

        }

        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);



        Fragment newFragment = new ViewFlipperHome(MainActivity.this);
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.mainframe, newFragment);

        fragmentTransaction.commit();
        gui = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger);
        gui.setClosedOnStart(true);
        gui.build();
        navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       // camera_check();
        isStoragePermissionGranted();

      setNavigation();

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

    @Override
    public void onclickLogReg(View view) {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }
    public void clickNews(View view) throws InterruptedException {
        gui.build().close();

        Fragment newFragment=new News();
        replaceFragment(newFragment);

    }
    public void home_click(View view) throws InterruptedException {
        gui.build().close();

        Fragment newFragment=new ViewFlipperHome(MainActivity.this);
        replaceFragment(newFragment);

    }
    public void replaceFragment(Fragment destFragment)
    {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.mainframe, destFragment);

        fragmentTransaction.commit();

    }
    public void clickDownload(View view)
    {
        gui.build().close();
        Fragment newFragment=new Download_Fragment();
        replaceFragment(newFragment);
    }
    public void onclickAcademiccalender(View view)
    {
        Intent intent =new Intent(getApplicationContext(),PDFVIEW.class);
        startActivity(intent);
    }
    public void adlogin(View view)
    {

        Intent i=new Intent(getApplicationContext(),AdminLogin.class);
        startActivity(i);
    }
    public void Fee(View view) throws InterruptedException {
        gui.build().close();
        Fragment newFragment=new Fee_Structure();
        replaceFragment(newFragment);
    }

    public class BackGroundNotificationCheck extends AsyncTask<String,Void,String>
    {
  String res="";
  String ip="";

        @Override
        protected String doInBackground(String... strings)
        {
            while(ip.equals("")) {
                WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                //Log.e("TAGip",ip);
            }

            String fileUrl = getString(R.string.ip) +"getNewsCount.php";
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //Log.e("TAG", "" + "yes");
               InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                res = convertStreamToString(inputStream);
                urlConnection.disconnect();
            } catch (FileNotFoundException e) {
                Log.e("TAG", "" + e);
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.e("TAG", "" + e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("TAG", "" + e);
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            SharedPreferences sharedPreferences =getSharedPreferences("number",Context.MODE_PRIVATE);
            String p=sharedPreferences.getString("count","0");
           // Log.e("TAG","S"+s);

            if(s!=null)
            {
               // Log.e("TAG","count p "+p);
                if(!(p.equals(s)))
                {

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("count",s);
                    editor.commit();
                    String idChannel = "News_update";
                    Intent mainIntent;

                    mainIntent = new Intent(getApplicationContext(), MainActivity.class);

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mainIntent, 0);

                    NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                    NotificationChannel mChannel = null;
                    // The id of the channel.

                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), idChannel);
                    builder.setContentTitle(getApplicationContext().getString(R.string.app_name))
                            .setSmallIcon(R.drawable.calendar)
                            .setContentIntent(pendingIntent)
                            .setContentText("New Notification ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mChannel = new NotificationChannel(idChannel, getApplicationContext().getString(R.string.app_name), importance);
                        // Configure the notification channel.
                        mChannel.setDescription("Important Notices");
                        mChannel.enableLights(true);
                        mChannel.setLightColor(Color.RED);
                        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        mNotificationManager.createNotificationChannel(mChannel);

                    } else {
                        builder.setContentTitle(getApplicationContext().getString(R.string.app_name))
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.mdtp_red_focused))
                                .setVibrate(new long[]{100, 250})
                                .setLights(Color.YELLOW, 500, 5000)
                                .setAutoCancel(true);
                    }

                    mNotificationManager.notify(1, builder.build());




                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"S is null ",Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(s);
            new BackGroundNotificationCheck().execute();
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

    }
    private void setNavigation()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        Fragment newFragment=new ViewFlipperHome(MainActivity.this);
                        replaceFragment(newFragment);
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_login:
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        drawer.closeDrawers();
                        startActivity(intent);
                        return  true;
                    case R.id.nav_notifications:
                        drawer.closeDrawers();
                        Fragment news=new News();
                        replaceFragment(news);
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
                       return true;
                    case R.id.nav_fee:
                        drawer.closeDrawers();
                        Fragment newFragme11=new Fee_Structure();
                        replaceFragment(newFragme11);
                        break;
                    case R.id.nav_adminLogin:

                    case R.id.nav_about_us:
                        Intent i=new Intent(getApplicationContext(),AdminLogin.class);
                        startActivity(i);
                        drawer.closeDrawers();

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


    public void camera_check()
    {
        if(Build.VERSION.SDK_INT>=23) {
            if (getApplicationContext().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                        getApplicationContext(), Manifest.permission.CAMERA)) {


                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            200);
                }

            }
        }
        else
        {
            return ;
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }
}
