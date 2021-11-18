package com.Ainwik.varshamakeovers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.Ainwik.varshamakeovers.R;
import com.Ainwik.varshamakeovers.adapters.AdapterOfHaircut;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.varshamakeovers.model.Records1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HairCutFragment extends Fragment {

    RecyclerView recyclerView;
    String api_getProfile="http://www.varshamakeovers.com/get_hairstyles.php";
    RequestQueue requestQueue;
    ArrayList<Records1> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_haircut, container, false);

        recyclerView = view.findViewById(R.id.RVhaircut);
        requestQueue = Volley.newRequestQueue(getActivity());

        arrayList=new ArrayList<Records1>();

        hairUpdates();

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

                        String image = obj.getString("image");
                        String name = obj.getString("name");
                        int cost = obj.getInt("cost");

                        Records1 myRecords = new Records1(name,cost,image);
                        arrayList.add(myRecords);

                    }
                    //set Record to adapter
                    AdapterOfHaircut adapter = new AdapterOfHaircut(getActivity(),arrayList);
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