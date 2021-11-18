package com.Ainwik.varshamakeovers.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ainwik.varshamakeovers.Login;
import com.Ainwik.varshamakeovers.R;


public class InfoFragment extends Fragment {
    Button b1;
    TextView t1;

    SharedPreferences preferences,preferences1;
    SharedPreferences.Editor editor,editor1;
    ImageView map;

    double latitude,longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        b1=view.findViewById(R.id.logout);
        t1=view.findViewById(R.id.Mobile);
        map=view.findViewById(R.id.map);

        preferences=getActivity().getSharedPreferences("login_credential", Context.MODE_PRIVATE);
        editor=preferences.edit();

        preferences1=getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        editor1=preferences1.edit();

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:9812991855"));

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED )
                {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},0);
                    return;

                }
                startActivity(i);

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openMap();

                /*ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait !!");
                progressDialog.show();*/

//                AsyncTask asyncTask=new AsyncTask() {
//                    @Override
//                    protected Object doInBackground(Object[] objects)
//                    {
//
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPreExecute() {
//                        super.onPreExecute();
//                    }
//
//                    @Override
//                    protected void onPostExecute(Object o) {
//                        super.onPostExecute(o);
//                    }
//                };


//                if (latitude>0 && longitude>0) {
//                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                            Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=28.473330417498712, 77.51567559765535"));
//                    startActivity(intent);
//                    progressDialog.dismiss();
//                }





              /*  Uri uri = Uri.parse("geo:28.77376176324229, 76.77204409758586");

                Intent i= new Intent(Intent.ACTION_VIEW, uri);

                i.setPackage("com.google.android.apps.maps");

                startActivity(i);*/
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               editor.clear();
               editor.commit();
               startActivity(new Intent(getActivity(), Login.class));
               getActivity().finish();
            }
        });


   return view;
    }

    private void openMap()
    {
     Uri uri = Uri.parse("geo:0, 0?q=Varsha Beauty Parlour - Best Salon In Sampla - Bridal Makeup Artist In Sampla वर्षा ब्यूटी पार्लर");
     Intent i = new Intent(Intent.ACTION_VIEW,uri);
     i.setPackage("com.google.android.apps.maps");
     startActivity(i);
    }

}