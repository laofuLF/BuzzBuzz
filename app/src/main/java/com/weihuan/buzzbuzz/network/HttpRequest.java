package com.weihuan.buzzbuzz.network;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.weihuan.buzzbuzz.MainActivity;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.Recipe;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpRequest {
//    private Handler handler;
//    private ArrayList<Recipe> recipes;
//
//
//    public HttpRequest(String url) {
//
//        run(url);
//
//    }
//
//    public void run(String url){
//        Log.i("running", "11111");
//
//        OkHttpClient client = getOKHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Log.i("fail", "11111");
//                e.printStackTrace();
//
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                Log.i("success", "11111");
//                final String result = response.body().string();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
////                            getData(result);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
////                MainActivity.this.runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        try {
////                            getData(result);
//////                            for (Recipe recipe: recipeList) {
//////                                int id = recipe.getId();
//////                                Log.i("item id is: ", String.valueOf(id));
////////                                db.insertRecipe(recipe); // insert into database
//////                            }
//////                            // test db data
//////                            ArrayListList<Recipe> RecipeDatabase = new ArrayList<>();
//////                            RecipeDatabase.addAll(db.getAllRecipes());
//////                            for (Recipe recipeDB: RecipeDatabase) {
//////                                int id = recipeDB.getId();
//////                                Log.i("Database data:", String.valueOf(id));
//////                            }
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    }
////                });
//            }
//        });
//    }
//
////
////    private static OkHttpClient getOKHttpClient() {
////        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
////            @Override
////            public void log(String message) {
////                Log.d("wh", "log: message = " + message);
////            }
////        });
////        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
////
////        OkHttpClient.Builder builder = new OkHttpClient.Builder();
////        builder.addInterceptor(loggingInterceptor);
////        return builder.build();
////    }
////
////    private void getData(String result) throws JSONException {
////        recipes.clear();
////        JSONObject reader = new JSONObject(result);
////        JSONArray recipeListJS = reader.getJSONArray("drinks");
////        int count = recipeListJS.length();
////
////        for (int i = 0; i < count; i++) {
////            JSONObject currentRecipeJS = recipeListJS.getJSONObject(i);
////            int id = currentRecipeJS.getInt("idDrink");
////            String recipeName = currentRecipeJS.getString("strDrink");
////            String glass = currentRecipeJS.getString("strGlass");
////            String imageUrl = currentRecipeJS.getString("strDrinkThumb");
////            Recipe currentData = new Recipe(id, recipeName, glass, imageUrl);
////            recipes.add(currentData);
////            Log.i("id: ", String.valueOf(id));
////            Log.i("Name: ", recipeName);
////            Log.i("Glass: ", glass);
////            Log.i("Image: ", imageUrl);
////        }
////    }
////
////
////    public ArrayList<Recipe> getRecipes() {
////        return recipes;
////    }

}
