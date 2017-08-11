package com.example.sony.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SONY on 8/7/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private ArrayList<Steps> stepsData;
    private final StepsAdapterOnClickHandler clickHandler;

    public StepsAdapter(StepsAdapterOnClickHandler clickHandler) {
        this.clickHandler=clickHandler;
    }

    public interface StepsAdapterOnClickHandler{
        void onClick(Steps stepsData);
    }


    @Override
    public StepsAdapter.StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_recycler_layout,parent,false);
        return new StepsAdapter.StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.StepsAdapterViewHolder holder, int position) {

        holder.stepsText.setText("Steps #"+ ++position);
    }

    @Override
    public int getItemCount() {
        if(stepsData == null)
            return 0;
        return stepsData.size();
    }
    public void setstepsData(ArrayList<Steps> s){
        stepsData=s;
        notifyDataSetChanged();
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView stepsText;
        public StepsAdapterViewHolder(View itemView) {
            super(itemView);
            stepsText=(TextView)itemView.findViewById(R.id.tv_steps);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition=getAdapterPosition();
            Steps sData=stepsData.get(adapterPosition);
            clickHandler.onClick(sData);
        }
    }
}
