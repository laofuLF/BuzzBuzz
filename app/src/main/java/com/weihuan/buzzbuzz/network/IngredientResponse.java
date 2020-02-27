package com.weihuan.buzzbuzz.network;

import com.google.gson.annotations.SerializedName;
import com.weihuan.buzzbuzz.db.Ingredient;

import java.util.List;

public class IngredientResponse {

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredient() {
        return ingredients;
    }
    public void setIngredient(List<Ingredient> ingredient) {
        this.ingredients = ingredient;
    }
}
