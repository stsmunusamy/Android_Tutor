package com.myapps35.tutorial.Simple_Fingure_Gesture;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapps35.tutorial.R;
import com.myapps35.tutorial.Simple_Fingure_Gesture.Utility.SimpleFingerGestures;

public class SimpleGestureActivity extends Activity
{

    TextView gestureTextView;

    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_android_expriment);

        gestureTextView = (TextView) findViewById(R.id.gestureTextView);

        myView = (View) findViewById(R.id.myView);

        SimpleFingerGestures sfg = new SimpleFingerGestures();
        sfg.setDebug(true);
        sfg.setConsumeTouchEvents(true);

        sfg.setOnFingerGestureListener(new SimpleFingerGestures.OnFingerGestureListener() {
            @Override
            public boolean onSwipeUp(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("You swiped " + fingers + " fingers  up " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeDown(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("You swiped " + fingers + " fingers  down " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeLeft(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("You swiped " + fingers + " fingers  left " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onSwipeRight(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("You swiped " + fingers + " fingers  right " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onPinch(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("You pinched " + fingers + " fingers " + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onUnpinch(int fingers, long gestureDuration, double gestureDistance) {
                gestureTextView.setText("You unpinched " + fingers + "fingers"  + gestureDuration + " milliseconds " + gestureDistance + " pixels far");
                return false;
            }

            @Override
            public boolean onDoubleTap(int fingers) {
                gestureTextView.setText("You double tapped");
                return false;
            }
        });


        myView.setOnTouchListener(sfg);

    }

}