package com.myapps35.tutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.myapps35.tutorial.BottomSheet.BottomSheetActivity;
import com.myapps35.tutorial.CustomLoadingAnimation.CustomAnimationLoadingActivity;
import com.myapps35.tutorial.Facebook_Integration.FacebookSinginActivity;
import com.myapps35.tutorial.FileSharingViaIntent.FileSharingActivity;
import com.myapps35.tutorial.LockScreenDemo.CustomLockScreen;
import com.myapps35.tutorial.MusicPlayer.MusicActivity;
import com.myapps35.tutorial.R;
import com.myapps35.tutorial.Simple_Fingure_Gesture.SimpleGestureActivity;
import com.myapps35.tutorial.Utils.FabArcMenu.ArcMenu;
import com.myapps35.tutorial.WifiChat.MainActivity_Wifi_chat;


public class MyApps35Tutors extends AppCompatActivity
{

    private CollapsingToolbarLayout cToolbarLayout;

    private ArcMenu arcMenu;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private FloatingActionButton fab4;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolBarInit();

        initialize();

        setonClickListener();
    }


    private void toolBarInit()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);

        setSupportActionBar(toolbar);

        cToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mCollapsing_toolbar);

        cToolbarLayout.setTitle("Welcome");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.adele);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener()
        {
            @Override
            public void onGenerated(Palette palette)
            {
                //noinspection ResourceType
                cToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));

                //noinspection ResourceType
                cToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });

    }

    private void initialize()
    {

        arcMenu = (ArcMenu) findViewById(R.id.arcMenu);

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

        fab1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MyApps35Tutors.this, SimpleGestureActivity.class));
            }
        });

        fab2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MyApps35Tutors.this, FacebookSinginActivity.class));
            }
        });

        fab3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        fab4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

    }

    private void setonClickListener()
    {

        ((CardView) findViewById(R.id.btnMusicPlayer)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), MusicActivity.class));
            }
        });

        ((CardView) findViewById(R.id.btnLockScreen)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), CustomLockScreen.class));
            }
        });

        ((CardView) findViewById(R.id.btnWifiChat)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), MainActivity_Wifi_chat.class));
            }
        });

        ((CardView) findViewById(R.id.btnCustomLoadingBar)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), CustomAnimationLoadingActivity.class));
            }
        });


        ((CardView) findViewById(R.id.btnWifiPrinting)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), CustomAnimationLoadingActivity.class));
            }
        });

        ((CardView) findViewById(R.id.btnBottomSheetExample)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), BottomSheetActivity.class));
            }
        });

        ((CardView) findViewById(R.id.btnFileSharing)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), FileSharingActivity.class));
            }
        });



        arcMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(arcMenu.isMenuOpened())
                {
                    arcMenu.setBackgroundColor(getResources().getColor(R.color.blackTransparent55));
                } else
                {
                    arcMenu.setBackgroundColor(getResources().getColor(R.color.blackTransparent));
                }
            }
        });

    }

}