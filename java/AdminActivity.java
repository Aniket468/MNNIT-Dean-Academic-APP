package com.example.aniketkumar.mnnit_portal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String data;
    String message,check;
    AdminAdapter adminAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.search_admin_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Search user");
        recyclerView=findViewById(R.id.recyclerView_Admin);
        Bundle bundle = getIntent().getExtras();
         message = bundle.getString("json");
         check=bundle.getString("check");
         fillList();

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
                message=result;
                if(message!=null)
                fillList();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//               if(query!=null)
//                adminAdapter.getFilter().filter(query);
//                return false;
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                if(query!=null)
                adminAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;


    }

    public void fillList()
    {
        if(message!=null&&check!=null) {
            JSONArray jsonArray;
            JSONObject jsonObject;
            java.util.List<List> Data = new ArrayList<>();
            try {
                jsonObject = new JSONObject(message);

                jsonArray = jsonObject.getJSONArray("result");
                Log.e("TAGG", "length of json " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (check.equals("total")) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Data.add(new List(jsonObject1.getString("name"), jsonObject1.getString("status"), jsonObject1.getString("reg_no"), jsonObject1.getString("pass"), jsonObject1.getString("gender"), jsonObject1.getString("category"), jsonObject1.getString("dob"), jsonObject1.getString("imagepath")));

                    } else if (check.equals("verified")) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1.getString("status").equals("1"))
                            Data.add(new List(jsonObject1.getString("name"), jsonObject1.getString("status"), jsonObject1.getString("reg_no"), jsonObject1.getString("pass"), jsonObject1.getString("gender"), jsonObject1.getString("category"), jsonObject1.getString("dob"), jsonObject1.getString("imagepath")));
                    } else {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1.getString("status").equals("0"))
                            Data.add(new List(jsonObject1.getString("name"), jsonObject1.getString("status"), jsonObject1.getString("reg_no"), jsonObject1.getString("pass"), jsonObject1.getString("gender"), jsonObject1.getString("category"), jsonObject1.getString("dob"), jsonObject1.getString("imagepath")));

                    }

                }
            } catch (JSONException e) {
                Log.e("TAG", "Json error");
                e.printStackTrace();
            }


            Log.e("TAGGGGG", Data.toString());
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            try {
                adminAdapter = new AdminAdapter(Data);
                recyclerView.setAdapter(adminAdapter);

            } catch (JSONException e) {
                Log.e("TAG", "json parsing error");
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        new DataUpdate().execute();
        super.onPostResume();
    }
}
