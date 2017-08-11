package com.example.sony.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sony.bakingapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class detailActivity extends AppCompatActivity implements ingredientsAndStepsFragment.onClick{
    private ArrayList<Ingredients> ingredientsData;
    private Steps stepsData;
    private ArrayList<Steps> stepsArrayList;
    ActivityDetailBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            String recipeName=intentThatStartedThisActivity.getStringExtra("recipeName");
            getSupportActionBar().setTitle(recipeName);
            if (intentThatStartedThisActivity.hasExtra("ingredients")) {
                ingredientsData = intentThatStartedThisActivity.getParcelableArrayListExtra("ingredients");
                ingredientsAndStepsFragment x=new ingredientsAndStepsFragment(ingredientsData,null,null);
                FragmentManager fm=getSupportFragmentManager();
                fm.beginTransaction().add(R.id.my_container2,x).commit();
            }
            else {
                stepsData= intentThatStartedThisActivity.getParcelableExtra("steps");
                stepsArrayList=intentThatStartedThisActivity.getParcelableArrayListExtra("stepsData");
                ingredientsAndStepsFragment x=new ingredientsAndStepsFragment(null,stepsData,stepsArrayList);
                FragmentManager fm=getSupportFragmentManager();
                fm.beginTransaction().add(R.id.my_container2,x).commit();
            }
        }
        else{
            Log.v("tes","ga ada data");
        }

    }

    @Override
    public void onClicked(int index) {
        stepsData=stepsArrayList.get(index);
        ingredientsAndStepsFragment x=new ingredientsAndStepsFragment(null,stepsData,stepsArrayList);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.my_container2,x).commit();
    }
}
