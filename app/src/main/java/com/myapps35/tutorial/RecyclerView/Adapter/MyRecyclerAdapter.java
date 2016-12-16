package com.myapps35.tutorial.RecyclerView.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapps35.tutorial.R;

import java.util.ArrayList;

/**
 * Created by span-tech on 12/15/2016.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>
{

    ArrayList<String> mDataList;


    /*
    *  Step 1:
    *  Get the Data List for Create Views
    */
    public MyRecyclerAdapter(ArrayList<String> mDataList)
    {
        this.mDataList = mDataList;

    }

    /*
    *  Step 3:
    *  Initialize the Layout & return the view
    */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mrecycler_view_adapter_item, parent, false);

        return new ViewHolder(view);
    }

    /*
    *  Step 5:
    *  Set the data's for view items with respective position & viewHolder
    */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.lblName.setText(mDataList.get(position));

        holder.lblDescription.setText(mDataList.size() > position+1 ? mDataList.get(position+1) : mDataList.get(0));
    }

    /*
   *  Step 2:
   *  Initialize the Data List size for Create the no of view with repect to Data's
   */
    @Override
    public int getItemCount()
    {
        return mDataList.size();
    }

    /*
    *  Step 4:
    *  Declare & Initialize the View Items
    */
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView lblDescription;
        TextView lblName;

        ViewHolder(View itemView)
        {
            super(itemView);

            lblName = (TextView) itemView.findViewById(R.id.lblName);
            lblDescription = (TextView) itemView.findViewById(R.id.lblDescription);

        }
    }
}