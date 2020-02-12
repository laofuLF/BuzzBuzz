package com.weihuan.buzzbuzz.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApiService {

    @GET("search.php")
    Call<RecipeResponse> getRecipes(@Query("s") String query);
}
