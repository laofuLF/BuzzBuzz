package com.weihuan.buzzbuzz.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApiService {

    // https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita
    @GET("search.php")
    Call<RecipeResponse> getRecipes(@Query("s") String query);

    //https://www.thecocktaildb.com/api/json/v1/1/random.php
    @GET("random.php")
    Call<RecipeResponse> getRandomRecipes();
}
