package com.Ainwik.varshamakeovers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    ImageView logo,background;
    // ImageView imageView,imageView1;
    // Animation animation,animation2;
    LottieAnimationView lottieAnimationView;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

       // background=findViewById(R.id.img);

        animation=AnimationUtils.loadAnimation(this,R.anim.fade_in);

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O) {
            lottieAnimationView = findViewById(R.id.lottie);
            //lottieAnimationView1=findViewById(R.id.lottie1);
            lottieAnimationView.animate().translationX(2000).setDuration(1000).setStartDelay(4000);
            // lottieAnimationView1.animate().translationX(1400).setDuration(1000).setStartDelay(4000);
            //logo.animate().translationY(400).setDuration(1000).setStartDelay(4000);
          //  background.animate().translationY(-2500).setDuration(1000).setStartDelay(4000);

            //imageView=findViewById(R.id.imageViewOfName);
            // imageView1=findViewById(R.id.imageViewOfLogo);
            //  animation= AnimationUtils.loadAnimation(this, R.anim.slide);
            // animation2= AnimationUtils.loadAnimation(this, R.anim.fade_in);

            //imageView.setAnimation(animation);
            //imageView1.setAnimation(animation2);
        }
        else
        {
            logo.setAnimation(animation);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(new Intent(SplashActivity.this,Login.class));
                finish();
            }
        },4900);
    }
}