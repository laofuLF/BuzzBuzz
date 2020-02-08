package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.weihuan.buzzbuzz.Fragments.FavoriteFragment;
import com.weihuan.buzzbuzz.Fragments.HomeFragment;
import com.weihuan.buzzbuzz.Fragments.SearchFragment;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.Recipe;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigationAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private int[] tabColors;

    // UI
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;
    private TextView textView;

    // Database
    private DatabaseHelper db;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    public String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomBar();


        db = new DatabaseHelper(this);
//        recipeList.addAll(db.getAllRecipes());

        run();

    }

    private void run(){
        Log.i("running", "11111");


        OkHttpClient client = getOKHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("fail", "11111");
                e.printStackTrace();

                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("success", "11111");
                final String result = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getData(result);
                            for (Recipe recipe: recipeList) {
                                int id = recipe.getId();
                                Log.i("item id is: ", String.valueOf(id));
//                                db.insertRecipe(recipe); // insert into database
                            }
                            // test db data
                            List<Recipe> RecipeDatabase = new ArrayList<>();
                            RecipeDatabase.addAll(db.getAllRecipes());
                            for (Recipe recipeDB: RecipeDatabase) {
                                int id = recipeDB.getId();
                                Log.i("Database data:", String.valueOf(id));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    private static OkHttpClient getOKHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("wh", "log: message = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }

    private void getData(String result) throws JSONException {
        JSONObject reader = new JSONObject(result);
        JSONArray recipeListJS = reader.getJSONArray("drinks");
        int count = recipeListJS.length();

        for (int i = 0; i < count; i++) {
            JSONObject currentRecipeJS = recipeListJS.getJSONObject(i);
            int id = currentRecipeJS.getInt("idDrink");
            String recipeName = currentRecipeJS.getString("strDrink");
            String glass = currentRecipeJS.getString("strGlass");
            String imageUrl = currentRecipeJS.getString("strDrinkThumb");
            Recipe currentData = new Recipe(id, recipeName, glass, imageUrl);
            recipeList.add(currentData);
            Log.i("id: ", String.valueOf(id));
            Log.i("Name: ", recipeName);
            Log.i("Glass: ", glass);
            Log.i("Image: ", imageUrl);
        }
    }



    private void initBottomBar() {

        bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem(R.string.bottomTitle1, R.drawable.ic_home_black_24dp, R.color.tab1);
        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem(R.string.bottomTitle2, R.drawable.ic_local_bar_black_24dp, R.color.tab2);
        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem(R.string.bottomTitle3, R.drawable.ic_favorite_black_24dp, R.color.tab3);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Fragment selectedFragment = null;

                switch (position) {
                    case 0:
                        selectedFragment = new HomeFragment();
                        break;
                    case 1:
                        selectedFragment = new SearchFragment();
                        break;
                    case 2:
                        selectedFragment = new FavoriteFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        });

    }



}
