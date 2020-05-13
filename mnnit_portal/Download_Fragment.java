package com.example.aniketkumar.mnnit_portal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Download_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Download_Fragment() {
        // Required empty public constructor
    }
    CardView card1,card2,card3,card4,card5,card6,card7;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_download_, container, false);
        card1=view.findViewById(R.id.card1);
        card2=view.findViewById(R.id.card2);
        card3=view.findViewById(R.id.card3);
        card4=view.findViewById(R.id.card4);
        card5=view.findViewById(R.id.card5);
        card6=view.findViewById(R.id.card6);
        card7=view.findViewById(R.id.card7);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               function("card1",view);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                function("card2",view);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                function("card3",view);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                function("card4",view);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                function("card5",view);
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                function("card6",view);
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                function("card7",view);
            }
        });


        return view;
    }

    public void function(String val,View view)
    {
        Intent intent=new Intent(view.getContext(),Download_Section.class);
        intent.putExtra("card",val);
        startActivity(intent);
    }



}
