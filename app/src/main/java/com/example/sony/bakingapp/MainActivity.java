package com.example.sony.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler,LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {
    private RecyclerView mRecyclerView;
    private RecipeAdapter recipeAdapter;
    private ProgressBar mLoadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        recipeAdapter=new RecipeAdapter(this);
        mRecyclerView.setAdapter(recipeAdapter);
        getSupportLoaderManager().initLoader(11,null,this);

    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {
            @Override
            protected void onStartLoading() {
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
                super.onStartLoading();
            }

            @Override
            public ArrayList<Recipe> loadInBackground() {
                try {
                    String jsonRecipeResponse=PullData.getResponseFromHttpUrl();
                    ArrayList<Recipe> recipeData=JSONParser.getSimpleRecipeStringsFromJson(MainActivity.this,jsonRecipeResponse);
                    return recipeData;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        mRecyclerView.setVisibility(View.VISIBLE);
        recipeAdapter.setRecipeData(data);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }

    @Override
    public void onClick(Recipe recipeData) {
        Context context = this;
        Class destinationClass = recipeDetail.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("recipe", recipeData);
        startActivity(intentToStartDetailActivity);
    }
}
