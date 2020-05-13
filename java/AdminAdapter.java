package com.example.aniketkumar.mnnit_portal;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.List;

/**
 * Created by Aniket Kumar on 19-Sep-18.
 */

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> implements Filterable{
    List<com.example.aniketkumar.mnnit_portal.List> Data = Collections.emptyList();
    List<com.example.aniketkumar.mnnit_portal.List>filterList=Collections.emptyList();


    public AdminAdapter(List <com.example.aniketkumar.mnnit_portal.List>Data) throws JSONException {
        this.Data=Data;
        filterList=Data;
    }
    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.admin_card_item,parent,false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {



            holder.name.setText(filterList.get(position).name);
            holder.reg_no.setText(filterList.get(position).reg_no);
            if(filterList.get(position).status.equals("0"))
            {
                holder.status.setText("Not Verified");
                holder.status.setBackgroundColor(Color.parseColor("#F5160F"));
            }
            else
            {
                holder.status.setText("Verified");
                holder.status.setBackgroundColor(Color.parseColor("#63F709"));
                holder.status.setTextColor(Color.parseColor("#000000"));

            }



    }



    @Override
    public int getItemCount() {
        Log.e("TAG","length of json"+Data.size());

        return filterList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = Data;
                } else {
                    List<com.example.aniketkumar.mnnit_portal.List> filteredList = new ArrayList<>();
                    for (com.example.aniketkumar.mnnit_portal.List row : Data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.reg_no.contains(charString) || row.name.toLowerCase().contains(charString.toLowerCase())||row.name.contains(charString)) {
                            filteredList.add(row);
                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values =filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<com.example.aniketkumar.mnnit_portal.List>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder{

        TextView name,reg_no,status;
        public AdminViewHolder(View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.user_name);
            reg_no=itemView.findViewById(R.id.user_reg_no);
            status=itemView.findViewById(R.id.status);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {

             int pos=getAdapterPosition();
                Intent intent=new Intent(view.getContext(),Verification.class);
                intent.putExtra("name",filterList.get(pos).name);
                intent.putExtra("reg_no",filterList.get(pos).reg_no);
                intent.putExtra("status",filterList.get(pos).status);
                intent.putExtra("category",filterList.get(pos).category);
                intent.putExtra("gender",filterList.get(pos).gender);
                intent.putExtra("pass",filterList.get(pos).pass);
                intent.putExtra("dob",filterList.get(pos).dob);
                intent.putExtra("imagepath",filterList.get(pos).imagepath);
                view.getContext().startActivity(intent);

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                int pos=getAdapterPosition();
                String str=filterList.get(pos).reg_no.substring(4,5);
                if(str.equals("4"))
                {
                    Intent i=new Intent(view.getContext(),ComputerScience.class);
                    i.putExtra("reg_no",filterList.get(pos).reg_no);
                    i.putExtra("name",filterList.get(pos).name);
                    view.getContext().startActivity(i);}
                else if(str.equals("5"))
                {

                }

                return false;
            }
        });
        }
    }
}
