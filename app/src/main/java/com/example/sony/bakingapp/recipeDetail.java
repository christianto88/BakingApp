package com.example.sony.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class recipeDetail extends AppCompatActivity {
    private Recipe recipeData;
    private ArrayList<Ingredients> ingredientsData;
    private ArrayList<Steps> stepsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("recipe")) {
                recipeData = intentThatStartedThisActivity.getParcelableExtra("recipe");
                stepsData=recipeData.getStepsArrayList();
            }
        }
        recipeFragment rf=new recipeFragment(ingredientsData,stepsData);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.recipe_detail_container,rf).commit();


    }
}
