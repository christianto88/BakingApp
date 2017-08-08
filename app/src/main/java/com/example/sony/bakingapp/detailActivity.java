package com.example.sony.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class detailActivity extends AppCompatActivity {
    private ArrayList<Ingredients> ingredientsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("ingredients")) {
                ingredientsData = intentThatStartedThisActivity.getParcelableExtra("ingredients");
            }
        }
        ingredientsAndStepsFragment x=new ingredientsAndStepsFragment(ingredientsData);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.my_container2,x).commit();
    }
}
