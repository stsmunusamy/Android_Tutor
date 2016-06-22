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
    }

    void startAnim()
    {
        findViewById(R.id.SemiCircleSpin).setVisibility(View.VISIBLE);
    }

    void stopAnim()
    {
        findViewById(R.id.SemiCircleSpin).setVisibility(View.GONE);
    }
}