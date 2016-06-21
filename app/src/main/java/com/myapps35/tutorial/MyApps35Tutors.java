package com.myapps35.tutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.myapps35.tutorial.LockScreenDemo.CustomLockScreen;
import com.myapps35.tutorial.MusicPlayer.MusicActivity;
import com.myapps35.tutorial.Push_Notification_Using_GCM.RegisterActivity;
import com.myapps35.tutorial.Utils.FabArcMenu.ArcMenu;
import com.myapps35.tutorial.WifiChat.MainActivity_Wifi_chat;

public class MyApps35Tutors extends AppCompatActivity
{

    private CollapsingToolbarLayout cToolbarLayout;

    private FrameLayout fabOverlay;

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

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        cToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mCollapsing_toolbar);

        cToolbarLayout.setTitle("Welcome");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.adele);

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

        fabOverlay = (FrameLayout) findViewById(R.id.fabOverlay);

        arcMenu = (ArcMenu) findViewById(R.id.arcMenu);

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

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

        ((CardView) findViewById(R.id.btnGcm)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
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

        arcMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(arcMenu.isMenuOpened())
                {
                    fabOverlay.setBackgroundColor(getResources().getColor(R.color.blackTransparent55));
                }
                else
                {
                    fabOverlay.setBackgroundColor(getResources().getColor(R.color.blackTransparent));
                }
            }
        });

    }

}