package com.myapps35.tutorial.CustomLoadingAnimation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cleveroad.androidmanimation.AnimationDialogFragment;
import com.cleveroad.androidmanimation.LoadingAnimationView;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcLoader;
import com.myapps35.tutorial.R;

public class CustomAnimationLoadingActivity extends AppCompatActivity
{

    private LoadingAnimationView animation;

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

    void lastProgressbarDoubleCircle()
    {
        ArcConfiguration configuration = new ArcConfiguration(this);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setText("Please wait..");

        //        // Using this configuration with Dialog
        //        mDialog.setConfiguration(configuration);
        //
        //        // Using this configuration with ArcLoader
        //        mSimpleArcLoader.refreshArcLoaderDrawable(configuration);
    }

    private void androidBootAnimation()
    {
        animation.startAnimation();
        animation.pauseAnimation();
        animation.stopAnimation();

        AnimationDialogFragment fragment = new AnimationDialogFragment.Builder()
                .setBackgroundColor(Color.WHITE)
                .setFirstColor(getResources().getColor(R.color.colorAccent))
                .setSecondColor(getResources().getColor(R.color.colorPrimary))
                .setThirdColor(getResources().getColor(R.color.colorPrimaryDark))
                .setFourthColor(getResources().getColor(R.color.textColor))
                .setSpeedCoefficient(1.0f)
                .build();
        fragment.show(getSupportFragmentManager(), "Animation");
    }
}