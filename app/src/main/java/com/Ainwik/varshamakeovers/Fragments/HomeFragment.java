package com.Ainwik.varshamakeovers.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Ainwik.varshamakeovers.R;
import com.Ainwik.varshamakeovers.adapters.MyAdapterOfAfterBefore;
import com.Ainwik.varshamakeovers.adapters.MyAdapterOfEvents;
import com.Ainwik.varshamakeovers.adapters.MyAdapterPager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.varshamakeovers.model.Banner;
import com.example.varshamakeovers.model.Records;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewOfEvents,recyclerViewOfAfterBefore;

MyAdapterPager adapter;
ViewPager mPager;
CircleIndicator indicator;
int currentPage=0;
    String api_getEvents="http://www.varshamakeovers.com/getEvents.php";
    String api_get="http://www.varshamakeovers.com/get_afterBefore.php";
    String api_getProfile="http://www.varshamakeovers.com/getBanner.php";
    RequestQueue requestQueue;
    ArrayList<Records>arrayList;
    ArrayList<Records>arrayList1;

ArrayList<Banner> bannerArrayList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if(!isConnectedToInternet(getActivity()))
        {
            showCustomDialog();

        }





        recyclerViewOfEvents = view.findViewById(R.id.RecyclerOfEvents);
        recyclerViewOfAfterBefore= view.findViewById(R.id.RecyclerOfAfterBefore);



        mPager=view.findViewById(R.id.pager);
        indicator=view.findViewById(R.id.indicator);
        arrayList=new ArrayList<Records>();
        arrayList1=new ArrayList<Records>();
        bannerArrayList=new ArrayList<Banner>();

        requestQueue = Volley.newRequestQueue(requireActivity());
        jadu();
        eventUpdates();
        AfterBefore();

        return view;
    }

    private void showCustomDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please connect to the internet to proceed further").setCancelable(false).
                setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

    }


    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo wifiConn = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
       NetworkInfo mobileConn = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

       if ((wifiConn !=null && wifiConn.isConnected()) ||  (mobileConn !=null && mobileConn.isConnected()))
        {
            return true;

        }
       else {
           return false;
       }

    }

    private void AfterBefore()
    {
        StringRequest request = new StringRequest(Request.Method.POST, api_get, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0;i<jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String image = obj.getString("images");

                        Records myRecords = new Records(image);
                        arrayList1.add(myRecords);

                    }
                    //set Record to adapter
                    MyAdapterOfAfterBefore myAdapterOfAfterBefore = new MyAdapterOfAfterBefore(getActivity(),arrayList1);
                    //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);

                    //recyclerViewOfAfterBefore.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                    recyclerViewOfAfterBefore.setAdapter(myAdapterOfAfterBefore);
                    recyclerViewOfAfterBefore.setLayoutManager(manager);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                try {
                    Object obj = R.string.INTERNET_ERROR;
                    String temp = getString((Integer) obj);
                    error(temp,error);
                }catch(Exception e){
                    //e.printStackTrace();
                }
            }
        });
        requestQueue.add(request);


    }

    private void error(String message,VolleyError error) {

        //Activity activity = getActivity();

        //if(activity!=null)
            Toast.makeText(getActivity(), ""+message+error, Toast.LENGTH_SHORT).show();
    }

    public void eventUpdates()
    {
        StringRequest request = new StringRequest(Request.Method.POST, api_getEvents, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0;i<jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String image = obj.getString("images");

                        Records myRecords = new Records(image);
                        arrayList.add(myRecords);

                    }
                    //set Record to adapter
                    MyAdapterOfEvents myAdapterOfEvents = new MyAdapterOfEvents(getActivity(),arrayList);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                    recyclerViewOfEvents.setAdapter(myAdapterOfEvents);
                    recyclerViewOfEvents.setLayoutManager(manager);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                //error("check Your Connection",error);

                //Toast.makeText(getActivity(), "Check Your Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }



    private void jadu()
    {

        StringRequest request = new StringRequest(Request.Method.POST, api_getProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0;i<jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String image = obj.getString("images");

                        Banner myRecords = new Banner(image);
                        bannerArrayList.add(myRecords);

                    }
                    //set Record to adapter


                    adapter=new MyAdapterPager(getActivity(), bannerArrayList);
                    mPager.setAdapter(adapter);
                    indicator.setViewPager(mPager);
                    // adapter.notifyDataSetChanged();
                    //mPager.notifySubtreeAccessibilityStateChanged();

                    final Handler handler=new Handler(); //android.os


                    final Runnable update=new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(currentPage== bannerArrayList.size())
                            {
                                currentPage=0;
                            }
                            mPager.setCurrentItem(currentPage++,true);
                        }
                    };

                    Timer swipeTimer=new Timer();

                    swipeTimer.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            handler.post(update);
                        }
                    },3000,3000);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

               // error("check Your Connection",error);

               // Toast.makeText(requireActivity(), "hi", Toast.LENGTH_SHORT).show();


            }
        });
        requestQueue.add(request);







        //  indicator.setBackgroundColor(Color.BLUE);



    }

    }

