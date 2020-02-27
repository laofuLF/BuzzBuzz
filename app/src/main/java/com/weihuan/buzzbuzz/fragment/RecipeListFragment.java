package com.weihuan.buzzbuzz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weihuan.buzzbuzz.R;
import com.weihuan.buzzbuzz.RecipeDetails;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.Recipe;
import com.weihuan.buzzbuzz.network.RecipeApiService;
import com.weihuan.buzzbuzz.network.RecipeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;


public class RecipeListFragment extends Fragment implements RecipesRecyclerAdapter.OnRecipeListener {
    public static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";
    private static final String TAG = "RecipeListFragment";

    private FrameLayout fragmentContainer;
    private RecyclerView recyclerView;
    private RecyclerView horizontalScrollView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;
    private CardView cardView;
//    private TextView tabTitle;
    private EditText editQuery;
    private TextView tabTitle;
    private Retrofit retrofit = null;
    private List<Recipe> data;
    private HorizontalAdapter horizontalAdapter;
    private LinearLayoutManager horizontalLayout;

    RecipesRecyclerAdapter adapter;



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
        horizontalScrollView = view.findViewById(R.id.horizontal_recycler_view);
        cardView = view.findViewById(R.id.card_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        horizontalScrollView.setLayoutManager(layoutManager);
        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalScrollView.setLayoutManager(horizontalLayout);

        tabTitle = view.findViewById(R.id.tabTitle);

        editQuery = (EditText) view.findViewById(R.id.edit_query);


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
        editQuery.setVisibility(View.GONE);
        tabTitle.setText(R.string.tab1Title);
        List<Recipe> randomRecipes = new ArrayList<>();
        fetchRandomRecipes(0, randomRecipes);

//        for (int i = 0; i < 10; i++) {
//            data.addAll(fetchRandomRecipes());
//        }

    }

    private void initSearch(View view) {
        Log.i("initSearch:", "1111111111111");
        editQuery.setVisibility(View.VISIBLE);
        editQuery.setImeActionLabel("SEARCH", KeyEvent.KEYCODE_ENTER);
        editQuery.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String query = editQuery.getText().toString();
                fetchRecipes(query);
                return true;
            }
            return false;
        });
        tabTitle.setText(R.string.tab2Title);
    }

    /**
     * Init the fragment
     */
    private void initMyRecipe(View view) {
        tabTitle.setText(R.string.tab3Title);
        editQuery.setVisibility(View.GONE);
        // test db data
        List<Recipe> RecipeDatabase = new ArrayList<>();

//        db.resetTable();
        RecipeDatabase.addAll(db.getAllRecipes());
        for (Recipe recipeDB: RecipeDatabase) {
            int id = recipeDB.getId();
            Log.i("Database data:", String.valueOf(id));
        }
        data = RecipeDatabase;
        adapter = new RecipesRecyclerAdapter(data, this);
        recyclerView.setAdapter(adapter);



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


    }

    private void fetchRecipes(String query) {
        Log.i("fetched:", "111111111");
        if (retrofit == null) {
            Log.i("fetched:", "retrofit = null");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RecipeApiService recipeApiService = retrofit.create(RecipeApiService.class);
        Call<RecipeResponse> call = recipeApiService.getRecipes(query);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.i("responded:", response.body().toString());
                RecipeResponse recipeResponse = response.body();
                data = recipeResponse.getRecipes();
//                for (Recipe recipe: data) {
//                    db.insertRecipe(recipe);
//                }
                if (data != null) {
                    adapter = new RecipesRecyclerAdapter(data, RecipeListFragment.this);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter = new RecipesRecyclerAdapter(data, RecipeListFragment.this);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getActivity(), "No Result Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.i("failed:", "11111111111111");
            }
        });
    }


    private void fetchRandomRecipes(int nRandom, List<Recipe> randomRecipes) {
        Log.i("random fetched:", "111111111");
        if (retrofit == null) {
            Log.i("fetched:", "retrofit = null");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RecipeApiService recipeApiService = retrofit.create(RecipeApiService.class);
        Call<RecipeResponse> call = recipeApiService.getRandomRecipes();
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.i("random_responded:", response.body().toString());
                RecipeResponse recipeResponse = response.body();
                randomRecipes.addAll(recipeResponse.getRecipes());
                if (nRandom < 10) {
                    fetchRandomRecipes(nRandom + 1, randomRecipes);
                }else {
                    data = randomRecipes;
                    adapter = new RecipesRecyclerAdapter(data, RecipeListFragment.this);
                    recyclerView.setAdapter(adapter);
                    horizontalAdapter = new HorizontalAdapter(data, RecipeListFragment.this);
                    horizontalScrollView.setAdapter(horizontalAdapter);

//                    db.insertRecipes(randomRecipes);
                }
//                for (Recipe recipe: data) {
//                    db.insertRecipe(recipe);
//                }
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

    @Override
    public void onRecipeClick(int position) {
        Log.d(TAG, "onRecipeClick: " + position);
        Log.d(TAG, "onRecipeClick: " + data.get(position).getInstructions());
        Recipe currenRecipe = data.get(position);
        Intent intent = new Intent(getActivity(), RecipeDetails.class);
        intent.putExtra("instructions", currenRecipe.getInstructions());
        intent.putExtra("name", currenRecipe.getRecipeName());
        intent.putExtra("image", currenRecipe.getImage());
        intent.putExtra("glass", currenRecipe.getGlass());
        intent.putExtra("ingredients", currenRecipe.getAllIngredients());
        intent.putExtra("measurements", currenRecipe.getAllMeasurements());
        startActivity(intent);
    }

//    public ArrayList<String> getIngredients(int position) {
//        ArrayList<String> ingredients = new ArrayList<>();
//        String currentIngredient = data.get(position).getIngredient1()
//    }
}



