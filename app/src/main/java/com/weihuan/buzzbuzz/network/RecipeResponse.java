package com.weihuan.buzzbuzz.network;

import com.google.gson.annotations.SerializedName;
import com.weihuan.buzzbuzz.db.Recipe;

import java.util.List;

public class RecipeResponse {

    @SerializedName("drinks")
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
