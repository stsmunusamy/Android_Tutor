package com.myapps35.tutorial.RecyclerView.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.myapps35.tutorial.R;
import com.myapps35.tutorial.RecyclerView.Adapter.MyRecyclerAdapter;
import com.myapps35.tutorial.RecyclerView.Utilites.DividerItemDecoration;
import com.myapps35.tutorial.RecyclerView.Utilites.RecyclerTouchListener;

import java.util.ArrayList;

public class RecyclerDemoActivity extends Activity
{

    /*
    *  Step 1:
    *  Declare 3 components for Recycler View
    *  Widget
    *  Adater
    *  Layout Mangaer
    */

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mRecyclerAdapter;
    RecyclerView.LayoutManager mRecyclerLayoutManager;
    RecyclerView.ItemDecoration itemDecoration;

    ArrayList<String> mDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_demo);

        initDatas();

        initialize();

        setAdapter();
    }

    private void initDatas()
    {
        mDataList = new ArrayList<>();

        mDataList.add("One");
        mDataList.add("Two");
        mDataList.add("Three");
        mDataList.add("Four");
        mDataList.add("Five");
        mDataList.add("Six");
        mDataList.add("Seven");
        mDataList.add("Eight");
        mDataList.add("Nine");
        mDataList.add("Ten");
        mDataList.add("Eleven");
    }

    private void initialize()
    {
        /*
        *  Step 2:
        *  Initialize the View
        */
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
    }

    private void setAdapter()
    {
        /*
        *  Step 3:
        *  Use this setting to improve performance if you know that changes
        *  in content do not change the layout size of the RecyclerView
        */
        mRecyclerView.setHasFixedSize(true);


        /*
        *  Step 4:
        *  set the Linear Layout Properties for RecyclerView
        */
        mRecyclerLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerLayoutManager);


        /*
        *  Step 5:
        *  Set the List Indicator for recycler view
        */
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        /*
        *  Step 6:
        *  Initialize & set the adapter for RecyclerView
        */
        mRecyclerAdapter = new MyRecyclerAdapter(mDataList);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(getApplicationContext(), mDataList.get(position) + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(RecyclerDemoActivity.this, "Lonk Clicked", Toast.LENGTH_SHORT).show();

            }
        }));
    }
}