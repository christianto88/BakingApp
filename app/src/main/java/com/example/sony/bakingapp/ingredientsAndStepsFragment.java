package com.example.sony.bakingapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SONY on 8/8/2017.
 */

public class ingredientsAndStepsFragment extends Fragment {
    private ArrayList<Ingredients> ingredientsData;
    private Steps stepsData;
    private ArrayList<Steps> stepsArrayList;
    onClick mCallback;
    public interface onClick{
        void onClicked(int index);
    }
    public ingredientsAndStepsFragment( ArrayList<Ingredients> i,Steps s,ArrayList<Steps> stepsArrayList) {
        ingredientsData=i;
        stepsData=s;
        this.stepsArrayList=stepsArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        int idx=0;
        View rootView=inflater.inflate(R.layout.ingredientsandsteps_fragment,container,false);
        TextView tv1=(TextView)rootView.findViewById(R.id.tv_detail);
        TextView tv2=(TextView)rootView.findViewById(R.id.tv_video_player);
        ImageButton ib_next=(ImageButton)rootView.findViewById(R.id.next_button);
        ImageButton ib_prev=(ImageButton)rootView.findViewById(R.id.prev_button);
        ib_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClicked(stepsData.getId()+1);
            }
        });
        if(ingredientsData!=null){
            for(int a=0;a<ingredientsData.size();a++){
                tv1.setVisibility(View.VISIBLE);
                tv1.append(idx+1 +") "+ingredientsData.get(a).getQuantity()+ingredientsData.get(a).getMeasure()+"  "+ingredientsData.get(a).getIngredient()+"\n\n");
                idx++;
            }
        }
        else
        {
            if(!stepsData.getVideoURL().equals("") || !stepsData.getThumbnailURL().equals("")){
                tv1.setVisibility(View.VISIBLE);
                tv1.append(stepsData.getShortDescription()+"\n"+stepsData.getDescription());
                tv2.setVisibility(View.VISIBLE);
            }else
            {
                tv2.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv1.append(stepsData.getShortDescription()+"\n"+stepsData.getDescription());
            }
        }

        return rootView;
    }
}
