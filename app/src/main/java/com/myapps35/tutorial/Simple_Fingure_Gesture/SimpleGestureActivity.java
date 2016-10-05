package com.myapps35.tutorial.Simple_Fingure_Gesture;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapps35.tutorial.R;
import com.myapps35.tutorial.Simple_Fingure_Gesture.Utility.SimpleFingerGestures;

public class SimpleGestureActivity extends Activity
{

    private SimpleFingerGestures mySfg = new SimpleFingerGestures();

    TextView gestureTextView;

    RelativeLayout activity_android_expriment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_android_expriment);

        init();

        gestureInit();

        activity_android_expriment.setOnTouchListener(mySfg);

    }

    private void init()
    {
        gestureTextView = (TextView) findViewById(R.id.gestureTextView);

        activity_android_expriment = (RelativeLayout) findViewById(R.id.activity_android_expriment);
    }

    void gestureInit()
    {
        mySfg.setOnFingerGestureListener(new SimpleFingerGestures.OnFingerGestureListener() {
            @Override
            public boolean onSwipeUp(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("swiped " + fingers + " up");
                return false;
            }

            @Override
            public boolean onSwipeDown(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("swiped " + fingers + " down");
                return false;
            }

            @Override
            public boolean onSwipeLeft(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("swiped " + fingers + " left");
                return false;
            }

            @Override
            public boolean onSwipeRight(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("swiped " + fingers + " right");
                return false;
            }

            @Override
            public boolean onPinch(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("pinch");
                return false;
            }

            @Override
            public boolean onUnpinch(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("unpinch");
                return false;
            }

            @Override
            public boolean onDoubleTap(int fingers) {
                return false;
            }
        });
    }

}