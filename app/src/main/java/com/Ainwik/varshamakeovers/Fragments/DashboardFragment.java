package com.Ainwik.varshamakeovers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.Ainwik.varshamakeovers.R;
import com.Ainwik.varshamakeovers.adapters.MyAdapterOfFacialOffers;
import com.Ainwik.varshamakeovers.adapters.MyAdapterOfHairstyleOffers;
import com.Ainwik.varshamakeovers.adapters.MyAdapterOfMakeupOffers;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.varshamakeovers.model.Records;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    String api_getProfile="http://www.varshamakeovers.com/GetHairoffers.php";
    String api_getMakeup="http://www.varshamakeovers.com/GetMakeupOffers.php";
    String api_getFacial="http://www.varshamakeovers.com/GetFacialOffers.php";
    RequestQueue requestQueue;
    RecyclerView recyclerOfHair,recyclerOfMakeup,recyclerOfFacial;
    ImageView imageButtonOfHaircut,imageButtonOfHandAndFeet,imageButtonOfMakeup,imageButtonOfOthers,imageButtonOfHairTreatmentandTexture;

    ArrayList<Records>arrayList;
    ArrayList<Records> arrayList1;
    ArrayList<Records> arrayList2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerOfHair = view.findViewById(R.id.RvHairOffers);
        recyclerOfMakeup = view.findViewById(R.id.RvMakeupOffers);
        recyclerOfFacial = view.findViewById(R.id.RvFacialOffers);
        requestQueue = Volley.newRequestQueue(getActivity());

        imageButtonOfHaircut = view.findViewById(R.id.buttonOfHaircut);
        imageButtonOfHairTreatmentandTexture = view.findViewById(R.id.buttonOfHairTreatment);
        imageButtonOfHandAndFeet = view.findViewById(R.id.buttonOfHandAndFeet);
        imageButtonOfMakeup = view.findViewById(R.id.buttonOfMakeup);
        imageButtonOfOthers = view.findViewById(R.id.buttonOfHairExtension);

        arrayList=new ArrayList<Records>();
        arrayList1=new ArrayList<Records>();
        arrayList2=new ArrayList<Records>();

        requestQueue = Volley.newRequestQueue(getActivity());



        imageButtonOfOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Home home = (Home) getActivity();
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.frame_layout, new HairExtensionFragment());
                 ft.addToBackStack("");
                ft.commit();


            }
        });


        imageButtonOfHaircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.frame_layout, new HairCutFragment());
                ft.addToBackStack("");
                ft.commit();

            }
        });
        imageButtonOfHairTreatmentandTexture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Home home = (Home) getActivity();
                home.loadFragment(new HairTreatmentFragment());*/

                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.frame_layout, new HairTreatmentFragment());
                ft.addToBackStack("");
                ft.commit();

            }
        });
        imageButtonOfHandAndFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Home home = (Home) getActivity();
                home.loadFragment(new HandAndFeetFragment());*/

                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.frame_layout, new HandAndFeetFragment());
                ft.addToBackStack("");
                ft.commit();

            }
        });
        imageButtonOfMakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Home home = (Home) getActivity();
                home.loadFragment(new MakeupFragment());*/

                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.frame_layout, new MakeupFragment());
                ft.addToBackStack("");
                ft.commit();

            }
        });


        makeupUpdates();
        hairUpdates();
        facialUpdates();

        return view;


    }


    public void hairUpdates()
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

                        Records myRecords = new Records(image);
                        arrayList.add(myRecords);

                    }
                    //set Record to adapter
                    MyAdapterOfHairstyleOffers adapter = new MyAdapterOfHairstyleOffers(getActivity(),arrayList);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerOfHair.setLayoutManager(manager);
                    recyclerOfHair.setAdapter(adapter);

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
              //  Toast.makeText(getActivity(), "Check Your Network Connection", Toast.LENGTH_SHORT).show();


            }
        });
        requestQueue.add(request);

    }

public void facialUpdates()
    {
        StringRequest request = new StringRequest(Request.Method.POST, api_getFacial, new Response.Listener<String>() {
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
                        arrayList2.add(myRecords);

                    }
                    //set Record to adapter
                    MyAdapterOfFacialOffers adapter = new MyAdapterOfFacialOffers(getActivity(),arrayList2);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerOfFacial.setLayoutManager(manager);
                    recyclerOfFacial.setAdapter(adapter);

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
                //Toast.makeText(getActivity(), "Check Your Network Connection", Toast.LENGTH_SHORT).show();


            }
        });
        requestQueue.add(request);

    }




    public void makeupUpdates()
    {

        StringRequest request1 = new StringRequest(Request.Method.POST, api_getMakeup, new Response.Listener<String>() {
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
                    MyAdapterOfMakeupOffers adapter = new MyAdapterOfMakeupOffers(getActivity(),arrayList1);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerOfMakeup.setLayoutManager(manager);
                    recyclerOfMakeup.setAdapter(adapter);

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
                //Toast.makeText(getActivity(), "Check Your Network Connection", Toast.LENGTH_SHORT).show();


            }
        });
        requestQueue.add(request1);


    }





}

