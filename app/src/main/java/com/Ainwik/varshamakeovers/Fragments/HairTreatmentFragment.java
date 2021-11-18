package com.Ainwik.varshamakeovers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Ainwik.varshamakeovers.R;
import com.Ainwik.varshamakeovers.adapters.AdapterOfHairTreatment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.varshamakeovers.model.RecordsOfHairTreatment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HairTreatmentFragment extends Fragment {

    RecyclerView recyclerView;
    //String api="get_hairstyles.php";
   String api="http://varshamakeovers.com/get_hairtreatments_and_texture.php";
    RequestQueue requestQueue;
    ArrayList<RecordsOfHairTreatment> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_hair_treatment, container, false);

        recyclerView = view.findViewById(R.id.RVTreatment);
        requestQueue = Volley.newRequestQueue(getActivity());

        arrayList=new ArrayList<RecordsOfHairTreatment>();

        hairTreatments();

        return view;
    }

    public void hairTreatments()
    {
        StringRequest request = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
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

                        String image = obj.getString("image");
                        String name = obj.getString("name");
                        int cost = obj.getInt("cost");

                        RecordsOfHairTreatment myRecords = new RecordsOfHairTreatment(name,cost,image);
                        arrayList.add(myRecords);

                    }
                    //set Record to adapter
                    AdapterOfHairTreatment adapter = new AdapterOfHairTreatment(getActivity(),arrayList);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);

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
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
        requestQueue.add(request);

    }
}