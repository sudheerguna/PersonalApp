package com.doodleblue.personalapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodleblue.personalapp.Activity.HomePage;
import com.doodleblue.personalapp.CustomView.CustomTextview_Regular;
import com.doodleblue.personalapp.R;

import java.util.ArrayList;

/**
 * Created by satish on 1/12/2019.
 */

public class homepageAdapter extends
        RecyclerView.Adapter<homepageAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> phoneList = new ArrayList<>();

    public homepageAdapter(Context context,ArrayList<String> nameList,ArrayList<String> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
        this.nameList = nameList;
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    @Override
    public homepageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_layout, parent, false);

        homepageAdapter.MyViewHolder vh = new homepageAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final homepageAdapter.MyViewHolder holder, final int position) {

        holder.name.setText(nameList.get(position));
        holder.phone.setText(phoneList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePage)context).show_popup(position,true,nameList.get(position),phoneList.get(position));
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomTextview_Regular name,phone;
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (CustomTextview_Regular) itemView.findViewById(R.id.name);
            phone = (CustomTextview_Regular) itemView.findViewById(R.id.phone);
        }
    }
}

