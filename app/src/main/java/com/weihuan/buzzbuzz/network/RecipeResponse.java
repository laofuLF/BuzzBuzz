package com.weihuan.buzzbuzz.network;

import com.google.gson.annotations.SerializedName;
import com.weihuan.buzzbuzz.db.RecipeModel;

import java.util.List;

public class RecipeResponse {

    @SerializedName("drinks")
    private List<RecipeModel> recipeModels;

    public List<RecipeModel> getRecipeModels() {
        return recipeModels;
    }

    public void setRecipeModels(List<RecipeModel> recipeModels) {
        this.recipeModels = recipeModels;
    }
}
