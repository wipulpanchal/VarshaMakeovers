package com.Ainwik.varshamakeovers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ainwik.varshamakeovers.R;
import com.bumptech.glide.Glide;
import com.example.varshamakeovers.model.Records;

import java.util.ArrayList;

public class MyAdapterOfEvents extends RecyclerView.Adapter<MyAdapterOfEvents.MyViewHolder>
{


    Context context;
    ArrayList<Records> arrayList;

    public MyAdapterOfEvents(Context context, ArrayList<Records> arrayList)

    {
        this.context=context;
        this.arrayList= arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view=LayoutInflater.from(context).inflate(R.layout.customlayoutofevents,parent,false);

        MyViewHolder holder=new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Records obj = arrayList.get(position);
        Glide.with(context).load(obj.getImageUurl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
        }
    }

}
