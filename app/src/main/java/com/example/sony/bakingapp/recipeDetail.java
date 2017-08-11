package com.example.sony.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class recipeDetail extends AppCompatActivity implements recipeFragment.onClick{
    private Recipe recipeData;
    private ArrayList<Ingredients> ingredientsData;
    private ArrayList<Steps> stepsData;

    public recipeDetail(){}
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("recipe")) {
                recipeData = intentThatStartedThisActivity.getParcelableExtra("recipe");
                getSupportActionBar().setTitle(recipeData.getRecipeName());
                stepsData=recipeData.getStepsArrayList();
                ingredientsData=recipeData.getIngredientsArrayList();
            }
        }
        recipeFragment rf=new recipeFragment(ingredientsData,stepsData);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.my_container,rf).commit();


    }

    @Override
    public void onClicked(ArrayList<Ingredients> i,Steps s) {
        if(i!=null) {
            Class destinationClass = detailActivity.class;
            Intent intentToStartDetailActivity = new Intent(this.getApplicationContext(), destinationClass);
            intentToStartDetailActivity.putParcelableArrayListExtra("ingredients", i);
            intentToStartDetailActivity.putExtra("recipeName", recipeData.getRecipeName());
            startActivity(intentToStartDetailActivity);
        }
        else
        {
            Class destinationClass = detailActivity.class;
            Intent intentToStartDetailActivity = new Intent(this.getApplicationContext(), destinationClass);
            intentToStartDetailActivity.putExtra("steps", s);
            intentToStartDetailActivity.putParcelableArrayListExtra("stepsData",stepsData);
            intentToStartDetailActivity.putExtra("recipeName", recipeData.getRecipeName());
            startActivity(intentToStartDetailActivity);
        }
    }
}
