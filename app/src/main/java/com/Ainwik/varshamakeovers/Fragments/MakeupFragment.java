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
import com.Ainwik.varshamakeovers.adapters.AdapterOfMakeup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.varshamakeovers.model.RecordsOfMakeup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MakeupFragment extends Fragment {

    RecyclerView recyclerView;
    String api="http://varshamakeovers.com/GetMakeup.php";
    RequestQueue requestQueue;
    ArrayList<RecordsOfMakeup> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_makeup, container, false);

        recyclerView = view.findViewById(R.id.RVmakeup);
        requestQueue = Volley.newRequestQueue(getActivity());

        arrayList=new ArrayList<RecordsOfMakeup>();

        makeup();

        return view;
    }

    public void makeup()
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

                        RecordsOfMakeup myRecords = new RecordsOfMakeup(name,cost,image);
                        arrayList.add(myRecords);

                    }
                    //set Record to adapter
                    AdapterOfMakeup adapter = new AdapterOfMakeup(getActivity(),arrayList);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL   , false);
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