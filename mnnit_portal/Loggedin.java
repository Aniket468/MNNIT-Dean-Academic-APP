package com.example.aniketkumar.mnnit_portal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.zip.Inflater;

public class Loggedin extends AppCompatActivity {
    FloatingActionButton fab;
    @SuppressLint("WrongViewCast")
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);
        fab=(FloatingActionButton)findViewById(R.id.loggedmenu);

   fab.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           CustomDialogLoggedinMenu cdd=new CustomDialogLoggedinMenu(Loggedin.this);
           cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           cdd.show();
       }
   });
//        fab=(FloatingActionButton) findViewById(R.id.loggedin);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 Intent i=new Intent(getApplicationContext(),Loggedinmenu.class);
//                 startActivity(i);
//            }
//        });
    }
}
