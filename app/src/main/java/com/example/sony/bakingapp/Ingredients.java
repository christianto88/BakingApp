package com.example.sony.bakingapp;

/**
 * Created by SONY on 8/3/2017.
 */

public class Ingredients {
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    private int quantity;
    private String measure;
    private String ingredient;
}
