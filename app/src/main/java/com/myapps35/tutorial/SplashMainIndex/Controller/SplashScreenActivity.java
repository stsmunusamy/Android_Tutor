package com.myapps35.tutorial.SplashMainIndex.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.myapps35.tutorial.R;

public class SplashScreenActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(SplashScreenActivity.this, MyApps35Tutors.class));

                finish();
            }
        }, 3000);
    }
}
