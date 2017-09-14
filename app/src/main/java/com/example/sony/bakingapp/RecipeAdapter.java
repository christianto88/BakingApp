package com.example.sony.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SONY on 8/4/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

   private ArrayList<Recipe> recipeData;
    public Context context;
    private final RecipeAdapterOnClickHandler clickHandler;
    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe recipeData);
    }
    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler){
        this.clickHandler=clickHandler;
        this.context=context;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout,parent,false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        holder.recipeText.setText(recipeData.get(position).getRecipeName());
        if(!recipeData.get(position).getImage().equals("")){
//        add picasso
            Picasso.with(context).load(recipeData.get(position).getImage()).into(holder.recipeImg);

        }
    }

    @Override
    public int getItemCount() {
        if(recipeData == null)
            return 0;
        return recipeData.size();
    }
    public void setRecipeData(ArrayList<Recipe> r){
        recipeData=r;
        notifyDataSetChanged();
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView recipeText;
        private ImageView recipeImg;
        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            recipeText=(TextView)itemView.findViewById(R.id.tv_recipe);
            recipeImg=(ImageView)itemView.findViewById(R.id.iv_recipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition=getAdapterPosition();
            Recipe rData=recipeData.get(adapterPosition);
            clickHandler.onClick(rData);
        }
    }
}
