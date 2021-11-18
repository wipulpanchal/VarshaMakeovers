package com.Ainwik.varshamakeovers.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.Ainwik.varshamakeovers.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingFragment extends Fragment {
    TextView textViewDate, textViewSpinner, textViewTime;
    Calendar calendar;
    int y, m, d;
    Spinner spinner;
    Button btnAppointment;
    String data[];
    String url = "http://www.varshamakeovers.com//signup.php";
    RequestQueue requestQueue;
    int h, min;

    double latitude, longitude;

    String values="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        spinner = view.findViewById(R.id.spinner);
        btnAppointment = view.findViewById(R.id.buttonOfAppointment);
        textViewDate = view.findViewById(R.id.date);
        textViewSpinner = view.findViewById(R.id.textSpinner);
        textViewTime = view.findViewById(R.id.Time);
        calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);
        h = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        requestQueue = Volley.newRequestQueue(getActivity());


        data = getResources().getStringArray(R.array.services);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spnrlayout, data);
        spinner.setAdapter(adapter);




        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textViewTime.setText( selectedHour + ":" + selectedMinute);;

                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait !!");
                progressDialog.show();

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Data Uploaded", Toast.LENGTH_SHORT).show();

                        values="Hi I Just Booked an Appointment on "+textViewTime.getText().toString()+"at"+textViewDate.getText().toString()+"/n"+"i want to book a service"+textViewSpinner.getText().toString();

                        PackageManager packageManager = getActivity().getPackageManager();
                        Intent i = new Intent(Intent.ACTION_VIEW);

                        try {
                            String url = "https://api.whatsapp.com/send?phone=+918467090016"+"&text=" + URLEncoder.encode(values, "UTF-8");
                            i.setPackage("com.whatsapp");
                            i.setData(Uri.parse(url));
                            if (i.resolveActivity(packageManager) != null) {
                                getActivity().startActivity(i);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String,String> data = new HashMap<String,String>();
                        data.put("time",textViewTime.getText().toString());
                        data.put("date",textViewDate.getText().toString());
                        data.put("service",textViewSpinner.getText().toString());

                        return data;
                    }
                };

                requestQueue.add(request);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String item=  spinner.getSelectedItem().toString();
                textViewSpinner.setText(item);
               // Toast.makeText(getActivity(), "You are selected:- "+item, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });


        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT,listener,y,m,d).show();
            }
        });

        return view;



    }


    DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month)
        {
            textViewDate.setText(day_of_month+" / "+(month+1)+" / "+year);
        }
    };




}