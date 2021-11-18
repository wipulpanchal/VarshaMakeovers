package com.Ainwik.varshamakeovers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.Ainwik.varshamakeovers.Fragments.BookingFragment;
import com.Ainwik.varshamakeovers.Fragments.DashboardFragment;
import com.Ainwik.varshamakeovers.Fragments.HomeFragment;
import com.Ainwik.varshamakeovers.Fragments.InfoFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Home extends AppCompatActivity
{

    double latitude,longitude;

    MeowBottomNavigation bottomNavigation;
    Fragment fragment = null;
    Toolbar toolbar;
    SharedPreferences preferences,preferences1;
    SharedPreferences.Editor editor,editor1;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


        setSupportActionBar(toolbar);

        preferences=getApplicationContext().getSharedPreferences("login_credential", Context.MODE_PRIVATE);
        editor=preferences.edit();
        preferences1=getApplicationContext().getSharedPreferences("location", Context.MODE_PRIVATE);
        editor1=preferences1.edit();

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION},0);
    return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {


                latitude=location.getLatitude();
                longitude=location.getLongitude();

                editor1.putString("lat",String.valueOf(latitude));
                editor1.putString("lon",String.valueOf(longitude));
                editor1.commit();
            }
        });



        bottomNavigation = findViewById(R.id.bottom_navigation);

            bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_dashboard_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.booking));
            bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_info_24));



            bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
                @Override
                public void onShowItem(MeowBottomNavigation.Model item) {


                    switch (item.getId()) {
                        case 1:
                            fragment = new HomeFragment();
                            break;

                        case 2:
                            fragment = new DashboardFragment();
                            break;

                        case 3:
                            fragment = new BookingFragment();
                            break;
                        case 4:
                            fragment = new InfoFragment();
                            break;
                    }

                    loadFragment(fragment);


                }
            });

    /*        //set notification count
            bottomNavigation.setCount(1, "");*/
            //set home fragment initially selected
            bottomNavigation.show(1, true);

            bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
                @Override
                public void onClickItem(MeowBottomNavigation.Model item) {




                }
            });

            bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
                @Override
                public void onReselectItem(MeowBottomNavigation.Model item) {


                }
            });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mymenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item1:
                editor.clear();
                editor.commit();
                startActivity(new Intent(getApplicationContext(), Login.class));
               finish();
                break;




        }


        return super.onOptionsItemSelected(item);
    }

        public void loadFragment (Fragment fragment)
        {
            //replace fragment
            FragmentManager fm=getSupportFragmentManager();
                   FragmentTransaction ft= fm.beginTransaction();
                   ft.replace(R.id.frame_layout, fragment);
                 //  ft.addToBackStack("");
                   ft.commit();


        }

/*
    @Override
    public void onBackPressed()
    {

        ab = new AlertDialog.Builder(Home.this);
        ab.setTitle("ALERT !!");
        ab.setIcon(R.mipmap.ic_launcher);
        ab.setMessage("You really want to exit ?");
        ab.setCancelable(false);
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();

            }
        });
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        ab.setNeutralButton("Dont know", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ab.show();




    }*/


    public void user_permission()
    {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION},0);

            return;
        }


    }



}