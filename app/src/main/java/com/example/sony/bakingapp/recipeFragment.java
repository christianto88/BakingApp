package com.example.sony.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v7.recyclerview.R.attr.layoutManager;

/**
 * Created by SONY on 8/7/2017.
 */

public class recipeFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{
    private ArrayList<Ingredients> ingredientsData;
    private ArrayList<Steps> stepsData;
    private RecyclerView mRecyclerView;
    private StepsAdapter mStepsAdapter;
    onClick mCallback;
    private Parcelable layoutManagerSavedState;
    private int lastFirstVisiblePosition;

    @Override
    public void onClick(Steps stepsData) {
        mCallback.onClicked(null,stepsData);
    }

    public interface onClick{
        void onClicked(ArrayList<Ingredients> i,Steps s);
    }
    public recipeFragment(){}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scrollLoc",lastFirstVisiblePosition);
        outState.putParcelableArrayList("ingreData",ingredientsData);
        outState.putParcelableArrayList("stepsData",stepsData);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null) {
            lastFirstVisiblePosition = savedInstanceState.getInt("scrollLoc");
            ingredientsData=savedInstanceState.getParcelableArrayList("ingreData");
            stepsData=savedInstanceState.getParcelableArrayList("stepsData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        if(args!=null){

            ingredientsData=args.getParcelableArrayList("ingre");
            stepsData=args.getParcelableArrayList("step");
        }
        final Context context=getContext();
        View rootView=inflater.inflate(R.layout.recipe_fragment,container,false);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.steps_recycler);
        TextView z=(TextView)rootView.findViewById(R.id.tv_recipe_ingredients);
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClicked(ingredientsData,null);

            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mStepsAdapter=new StepsAdapter(this);
        mStepsAdapter.setstepsData(stepsData);
        mRecyclerView.setAdapter(mStepsAdapter);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
         lastFirstVisiblePosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
//        Log.d("LOG","lat visible:"+lastFirstVisiblePosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback=(onClick)context;
    }
}
