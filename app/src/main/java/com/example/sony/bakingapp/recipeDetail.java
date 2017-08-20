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
     String recipeString="";
    private boolean mTwoPane;
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
        if(findViewById(R.id.tablet_linear)!=null){
            mTwoPane=true;
            if(savedInstanceState==null) {
                recipeFragment rf=new recipeFragment();
                FragmentManager fm=getSupportFragmentManager();
                fm.beginTransaction().add(R.id.my_container,rf).commit();
                ingredientsAndStepsFragment x = new ingredientsAndStepsFragment();
//                Bundle args = new Bundle();
//                args.putParcelableArrayList("stepsArray", stepsArrayList);
//                args.putParcelable("steps", stepsData);
//                x.setArguments(args);
                fm.beginTransaction().add(R.id.my_container2, x).commit();
            }
        }
        else{
            mTwoPane=false;
        }
        recipeFragment rf=new recipeFragment();
        Bundle args=new Bundle();
        args.putParcelableArrayList("ingre",ingredientsData);
        args.putParcelableArrayList("step",stepsData);
        rf.setArguments(args);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.my_container,rf).commit();
    for(int x=0;x<ingredientsData.size();x++){
        recipeString=recipeString.concat(x+1+". "+ingredientsData.get(x).getQuantity()+ingredientsData.get(x).getMeasure()+"  "+ingredientsData.get(x).getIngredient()+"\n");

    }
    ingredientService.startBakingService(this,recipeString);
    }

    @Override
    public void onClicked(ArrayList<Ingredients> i,Steps s) {
        if(i!=null) {
            if(mTwoPane){
                ingredientsAndStepsFragment x = new ingredientsAndStepsFragment();
                Bundle args = new Bundle();
                args.putParcelableArrayList("ingreData", i);
                x.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.mycontainer_new,x).commit();
            }else {
                Class destinationClass = detailActivity.class;
                Intent intentToStartDetailActivity = new Intent(this.getApplicationContext(), destinationClass);
                intentToStartDetailActivity.putParcelableArrayListExtra("ingredients", i);
                intentToStartDetailActivity.putExtra("recipeName", recipeData.getRecipeName());
                startActivity(intentToStartDetailActivity);
            }
        }
        else
        {
            if(mTwoPane){
                ingredientsAndStepsFragment x = new ingredientsAndStepsFragment();
                Bundle args = new Bundle();
                args.putParcelableArrayList("stepsArray",stepsData);
                args.putParcelable("steps",s);
                x.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.mycontainer_new,x).commit();
            }
            else {
                Class destinationClass = detailActivity.class;
                Intent intentToStartDetailActivity = new Intent(this.getApplicationContext(), destinationClass);
                intentToStartDetailActivity.putExtra("steps", s);
                intentToStartDetailActivity.putParcelableArrayListExtra("stepsData", stepsData);
                intentToStartDetailActivity.putExtra("recipeName", recipeData.getRecipeName());
                startActivity(intentToStartDetailActivity);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
