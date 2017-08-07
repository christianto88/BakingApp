package com.example.sony.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by SONY on 8/3/2017.
 */

public class Recipe implements Parcelable{
private ArrayList<Ingredients> ingredientsArrayList;
    private ArrayList<Steps>stepsArrayList;
public Recipe(){}
    protected Recipe(Parcel in) {
        servings = in.readInt();
        recipeName = in.readString();
        stepsArrayList=(ArrayList<Steps>)in.readSerializable();
        ingredientsArrayList=(ArrayList<Ingredients>)in.readSerializable();

    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public ArrayList<Ingredients> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public void setIngredientsArrayList(ArrayList<Ingredients> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
    }

    public ArrayList<Steps> getStepsArrayList() {
        return stepsArrayList;
    }

    public void setStepsArrayList(ArrayList<Steps> stepsArrayList) {
        this.stepsArrayList = stepsArrayList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    private int servings;

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    private String recipeName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(servings);
        dest.writeString(recipeName);
        dest.writeSerializable(ingredientsArrayList);
        dest.writeSerializable(stepsArrayList);
    }
}
