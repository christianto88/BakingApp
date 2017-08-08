package com.example.sony.bakingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SONY on 8/8/2017.
 */

public class ingredientsAndStepsFragment extends Fragment {
    private ArrayList<Ingredients> ingredientsData;
    private ArrayList<Steps> stepsData;
    public ingredientsAndStepsFragment( ArrayList<Ingredients> i) {
        ingredientsData=i;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.ingredientsandsteps_fragment,container,false);
        TextView tv1=(TextView)rootView.findViewById(R.id.tv_detail);
        TextView tv2=(TextView)rootView.findViewById(R.id.tv_video_player);
        return rootView;
    }
}