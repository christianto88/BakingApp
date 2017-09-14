package com.example.sony.bakingapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SONY on 8/3/2017.
 */

public final class JSONParser {
    public static ArrayList<Recipe> getSimpleRecipeStringsFromJson(Context context, String RecipeJsonStr)

            throws JSONException {
        if(RecipeJsonStr==null){
            return null;
        }
        else {
            JSONArray recipeArray=new JSONArray(RecipeJsonStr);
            ArrayList<Recipe> parsedRecipeData = new ArrayList<Recipe>();
            parsedRecipeData.clear();
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONArray ingredientsArray = recipeArray.getJSONObject(i).getJSONArray("ingredients");
                JSONArray stepsArray=recipeArray.getJSONObject(i).getJSONArray("steps");
                ArrayList<Ingredients> ingredientsArrayList=new ArrayList<Ingredients>();
                ingredientsArrayList.clear();
                for(int j=0;j<ingredientsArray.length();j++){
                    JSONObject ingredient=ingredientsArray.getJSONObject(j);
                    Ingredients v=new Ingredients();
                    v.setQuantity(ingredient.getInt("quantity"));
                    v.setIngredient(ingredient.getString("ingredient"));
                    v.setMeasure(ingredient.getString("measure"));
                    ingredientsArrayList.add(v);
                }
                ArrayList<Steps> stepsArrayList=new ArrayList<Steps>();
                stepsArrayList.clear();
                for (int k=0;k<stepsArray.length();k++){
                    JSONObject step=stepsArray.getJSONObject(k);
                    Steps w=new Steps();
                    w.setId(step.getInt("id"));
                    w.setDescription(step.getString("description"));
                    w.setShortDescription(step.getString("shortDescription"));
                    w.setThumbnailURL(step.getString("thumbnailURL"));
                    w.setVideoURL(step.getString("videoURL"));
                    stepsArrayList.add(w);
                }
                Recipe r=new Recipe();
                r.setRecipeName(recipeArray.getJSONObject(i).getString("name"));
                r.setServings(recipeArray.getJSONObject(i).getInt("servings"));
                r.setImage(recipeArray.getJSONObject(i).getString("image"));
               r.setIngredientsArrayList(ingredientsArrayList);
                r.setStepsArrayList(stepsArrayList);
                parsedRecipeData.add(r);

            }

            return parsedRecipeData;
        }
    }
}
