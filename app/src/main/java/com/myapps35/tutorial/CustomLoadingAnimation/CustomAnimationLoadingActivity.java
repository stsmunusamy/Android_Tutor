package com.myapps35.tutorial.CustomLoadingAnimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.myapps35.tutorial.R;

public class CustomAnimationLoadingActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animation_loading);

        while (true)
        {
            try
            {
                startAnim();

                Thread.sleep(5000);

                stopAnim();

                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }

    void startAnim()
    {
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }

    void stopAnim()
    {
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }
}