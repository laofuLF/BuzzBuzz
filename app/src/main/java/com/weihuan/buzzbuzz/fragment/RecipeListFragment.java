package com.weihuan.buzzbuzz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weihuan.buzzbuzz.R;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.Recipe;
import com.weihuan.buzzbuzz.network.RecipeApiService;
import com.weihuan.buzzbuzz.network.RecipeResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListFragment extends Fragment {
    public static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";

    private FrameLayout fragmentContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;
    private CardView cardView;
//    private TextView tabTitle;
    private EditText editQuery;
    private TextView tabTitle;
    private ImageView searchIcon;
    private Retrofit retrofit = null;
    private List<Recipe> data;


    /**
     * Create a new instance of the fragment
     */
    public static RecipeListFragment newInstance(int index) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = new DatabaseHelper(getActivity());
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        fragmentContainer = view.findViewById(R.id.fragment_container);
        recyclerView = view.findViewById(R.id.fragment_recycler_view);
        cardView = view.findViewById(R.id.card_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tabTitle = view.findViewById(R.id.tabTitle);

        editQuery = (EditText) view.findViewById(R.id.edit_query);
        searchIcon = view.findViewById(R.id.search_mag_icon);


        int tabIndex = getArguments().getInt("index", -1);
        switch (tabIndex) {
            case 0:
                initPopular(view);
                break;
            case 1:
                initSearch(view);
                break;
            case 2:
                initMyRecipe(view);
                break;
        }
        return view;
    }

    private void initPopular(View view) {
        tabTitle.setText(R.string.tab1Title);
        fetchRecipes();
    }

    private void initSearch(View view) {
        Log.i("initSearch:", "1111111111111");

        tabTitle.setText(R.string.tab2Title);

    }

    /**
     * Init the fragment
     */
    private void initMyRecipe(View view) {
        tabTitle.setText(R.string.tab3Title);
        // test db data
        List<Recipe> RecipeDatabase = new ArrayList<>();
        RecipeDatabase.addAll(db.getAllRecipes());
        for (Recipe recipeDB: RecipeDatabase) {
            int id = recipeDB.getId();
            Log.i("Database data:", String.valueOf(id));
        }


//        for (int i = 0; i < 50; i++) {
//            cocktailNames.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
//        }

//        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita";

//        List<Recipe> RecipeDatabase = new ArrayList<>();
//        RecipeDatabase.addAll(db.getAllRecipes());
//        for (Recipe recipeDB: RecipeDatabase) {
//            String name = recipeDB.getRecipeName();
//            Log.i("Database data:", String.valueOf(name));
//            cocktailNames.add(name);
//        }

        ListAdapter adapter = new ListAdapter(RecipeDatabase);
        recyclerView.setAdapter(adapter);
    }

    private void fetchRecipes() {
        Log.i("fetched:", "111111111");
        if (retrofit == null) {
            Log.i("fetched:", "retrofit = null");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RecipeApiService recipeApiService = retrofit.create(RecipeApiService.class);
        Call<RecipeResponse> call = recipeApiService.getRecipes("vodka");
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.i("responded:", response.body().toString());
                RecipeResponse recipeResponse = response.body();
                data = recipeResponse.getRecipes();
//                for (Recipe recipe: data) {
//                    db.insertRecipe(recipe);
//                }
                ListAdapter adapter = new ListAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.i("failed:", "11111111111111");
            }
        });
    }

    /**
     * Refresh
     */
    public void refresh() {
        if (getArguments().getInt("index", 0) > -1 && recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed() {
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    public void willBeHidden(){
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }

    private List<Recipe> getData(String result) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
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
            data.add(currentData);
            Log.i("id: ", String.valueOf(id));
            Log.i("Name: ", recipeName);
            Log.i("Glass: ", glass);
            Log.i("Image: ", imageUrl);
        }
        return recipes;
    }

}



