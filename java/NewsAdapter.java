package com.example.aniketkumar.mnnit_portal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import java.util.List;

/**
 * Created by Aniket Kumar on 17-Sep-18.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    java.util.List<Data> data= Collections.emptyList();
    public NewsAdapter(List<Data> data)
    {
        this.data=data;

    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.new_items,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {


        holder.newText.setText(data.get(position).description);
        holder.date.setText(data.get(position).date);
        if(!data.get(position).des1.equals("empty")) {
            holder.link.setText(data.get(position).des1);
            holder.t1.setText(data.get(position).link1);
        }
        if(!data.get(position).des2.equals("empty")) {
            holder.link2.setText(data.get(position).des2);
            holder.t2.setText(data.get(position).link2);
        }
        if(!data.get(position).des3.equals("empty")) {
            holder.link3.setText(data.get(position).des3);
            holder.t3.setText(data.get(position).link3);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder
    {

        TextView newText,link,link2,link3,date;
        ImageView imageView;
        TextView t1,t2,t3;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newText=itemView.findViewById(R.id.newsText);
            link=itemView.findViewById(R.id.link1);
            link2=itemView.findViewById(R.id.link2);
            link3=itemView.findViewById(R.id.link3);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            date=itemView.findViewById(R.id.date);
            imageView=itemView.findViewById(R.id.imageView);
            link.setVisibility(View.GONE);
            link2.setVisibility(View.GONE);
            link3.setVisibility(View.GONE);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String p1=link.getText().toString();
                    p1=p1.trim();
                    String p2=link2.getText().toString();
                    p2=p2.trim();
                    String p3=link3.getText().toString();
                    p3=p3.trim();

                    if(p1.equals(""))
                    {
                        Toast.makeText(view.getContext(),"No any Attachment Available",Toast.LENGTH_LONG).show();
                    }
                    else if(p2.equals(""))
                    {
                        int p=link.getVisibility();
                        if(p==View.GONE)
                        {
                            link.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            link.setVisibility(View.GONE);
                        }
                    }
                    else if(p3.equals(""))
                    {
                        int p=link.getVisibility();
                        if(p==View.GONE)
                        {
                            link.setVisibility(View.VISIBLE);
                            link2.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            link.setVisibility(View.GONE);
                            link2.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        int p=link.getVisibility();
                        if(p==View.GONE)
                        {
                            link.setVisibility(View.VISIBLE);
                            link2.setVisibility(View.VISIBLE);
                            link3.setVisibility(View.VISIBLE);

                        }
                        else
                        {
                            link.setVisibility(View.GONE);
                            link2.setVisibility(View.GONE);
                            link3.setVisibility(View.GONE);
                        }
                    }


                }
            });
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 Intent intent=new Intent(view.getContext(),Notification_View.class);
                 intent.putExtra("link",t1.getText().toString());
                 view.getContext().startActivity(intent);
                }
            });
            link2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),Notification_View.class);
                    intent.putExtra("link",t2.getText().toString());
                    view.getContext().startActivity(intent);

                }
            });
            link3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),Notification_View.class);
                    intent.putExtra("link",t3.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    String ip=view.getContext().getString(R.string.ip);
                    String l1=t1.getText().toString().trim();
                    if(!l1.equals(""))
                    {
                        l1=ip+l1;
                    }

                    String l2=t2.getText().toString().trim();
                    if(!l2.equals(""))
                    {
                        l2=ip+l2;
                    }
                    String l3=t3.getText().toString().trim();
                    if(!l3.equals(""))
                    {
                        l3=ip+l3;
                    }
                    String text;
                    text=newText.getText().toString();
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "mnnit Notification: \n"+text+"\n"+l1+"\n"+l2+"\n"+l3);
                    sendIntent.setType("text/plain");
                    view.getContext().startActivity(sendIntent);
                    return false;
                }
            });

        }
    }
}
