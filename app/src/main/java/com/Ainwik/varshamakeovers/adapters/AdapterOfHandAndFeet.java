package com.Ainwik.varshamakeovers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ainwik.varshamakeovers.R;
import com.bumptech.glide.Glide;
import com.example.varshamakeovers.model.RecordsOfHairTreatment;

import java.util.ArrayList;

public class AdapterOfHandAndFeet extends RecyclerView.Adapter<AdapterOfHandAndFeet.MyViewHolder>
{


    Context context;
    ArrayList<RecordsOfHairTreatment> arrayList;

    public AdapterOfHandAndFeet(Context context, ArrayList<RecordsOfHairTreatment> arrayList)

    {
        this.context=context;
        this.arrayList= arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view=LayoutInflater.from(context).inflate(R.layout.custom_layout_of_hand_and_feet,parent,false);

        MyViewHolder holder=new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        RecordsOfHairTreatment obj = arrayList.get(position);
        holder.name.setText(""+obj.getName());
        holder.cost.setText("₹ "+obj.getCost());
        Glide.with(context).load(obj.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,cost;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.nameOfHairTreatment);
            cost=itemView.findViewById(R.id.rupeeOfHairTratment);
            imageView=itemView.findViewById(R.id.imageViewOfHairTreatment);
        }
    }

}
