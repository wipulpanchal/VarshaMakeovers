package com.Ainwik.varshamakeovers.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.Ainwik.varshamakeovers.R;
import com.bumptech.glide.Glide;
import com.example.varshamakeovers.model.Banner;

import java.util.ArrayList;

public class MyAdapterPager extends PagerAdapter
{

    ArrayList<Banner> arrayList;
    LayoutInflater inflater;
    Activity context;

    public MyAdapterPager(Activity context,
                          ArrayList<Banner> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
        inflater=LayoutInflater.from(context);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }


    @Override
    public int getCount()
    {
        return arrayList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container,
                                  int position)
    {
        View v=inflater.inflate(R.layout.slide,container,false);
        ImageView myImage=(ImageView)v.findViewById(R.id.image);
        Banner banner=arrayList.get(position);

        Glide.with(context).load(banner.getImageUrl()).into(myImage);
        container.addView(v,0);
        return v;
    }
}
