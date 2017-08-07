package com.example.sony.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v7.recyclerview.R.attr.layoutManager;

/**
 * Created by SONY on 8/7/2017.
 */

public class recipeFragment extends Fragment {
    private ArrayList<Ingredients> ingredientsData;
    private ArrayList<Steps> stepsData;
    private RecyclerView mRecyclerView;
    private StepsAdapter mStepsAdapter;
    public recipeFragment(ArrayList<Ingredients> i,ArrayList<Steps> s) {
        ingredientsData=i;
        stepsData=s;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.recipe_fragment,container,false);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.steps_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mStepsAdapter=new StepsAdapter();
        mStepsAdapter.setstepsData(stepsData);
        mRecyclerView.setAdapter(mStepsAdapter);
        return rootView;
    }
}
