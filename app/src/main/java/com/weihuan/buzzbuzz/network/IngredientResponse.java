package com.weihuan.buzzbuzz.network;

import com.google.gson.annotations.SerializedName;
import com.weihuan.buzzbuzz.db.IngredientModel;

import java.util.List;

public class IngredientResponse {

    @SerializedName("ingredients")
    private List<IngredientModel> ingredientModels;

    public List<IngredientModel> getIngredient() {
        return ingredientModels;
    }
    public void setIngredient(List<IngredientModel> ingredientModel) {
        this.ingredientModels = ingredientModel;
    }
}
