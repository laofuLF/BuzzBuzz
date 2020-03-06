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
    private List<Recipe> verticalRecipes;
    private List<Recipe> horizontalRecipes;
    private HorizontalAdapter horizontalAdapter;
    private LinearLayoutManager horizontalLayout;
    private TextView iconic;
    private View viewId;

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
        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalScrollView.setLayoutManager(horizontalLayout);
        iconic = view.findViewById(R.id.iconic);
        viewId = view.findViewById(R.id.view);

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
        List<Recipe> randomRecipes1 = new ArrayList<>();
        List<Recipe> randomRecipes2 = new ArrayList<>();
        fetchRandomRecipes(0, randomRecipes1, randomRecipes2);
        iconic.setText("Iconic Cocktails");
//        for (int i = 0; i < 10; i++) {
//            data.addAll(fetchRandomRecipes());
//        }

    }

    private void initSearch(View view) {
        Log.i("initSearch:", "1111111111111");
        editQuery.setVisibility(View.VISIBLE);
        iconic.setVisibility(View.GONE);
        viewId.setVisibility(View.GONE);
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
        iconic.setVisibility(View.GONE);
        refreshDatabase();
    }

    /**
     * Display updated recipes from db
     */
    public void refreshDatabase() {
        List<Recipe> RecipeDatabase = new ArrayList<>();
        RecipeDatabase.addAll(db.getAllRecipes());
        for (Recipe recipeDB: RecipeDatabase) {
            int id = recipeDB.getId();
            Log.i("Database data:", String.valueOf(id));
        }
        verticalRecipes = RecipeDatabase;
        adapter = new RecipesRecyclerAdapter(verticalRecipes, this);
        recyclerView.setAdapter(adapter);
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
                verticalRecipes = recipeResponse.getRecipes();
//                for (Recipe recipe: data) {
//                    db.insertRecipe(recipe);
//                }
                if (verticalRecipes != null) {
                    adapter = new RecipesRecyclerAdapter(verticalRecipes, RecipeListFragment.this);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter = new RecipesRecyclerAdapter(verticalRecipes, RecipeListFragment.this);
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


    private void fetchRandomRecipes(int nRandom, List<Recipe> randomRecipes1, List<Recipe> randomRecipes2) {
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
                if (nRandom < 10) {
                    randomRecipes1.addAll(recipeResponse.getRecipes());
                } else if (nRandom >= 10 && nRandom < 20) {
                    randomRecipes2.addAll(recipeResponse.getRecipes());
                }
                if (nRandom < 20) {
                    fetchRandomRecipes(nRandom + 1, randomRecipes1, randomRecipes2);
                }else {
                    verticalRecipes = randomRecipes1;
                    adapter = new RecipesRecyclerAdapter(verticalRecipes, RecipeListFragment.this);
                    recyclerView.setAdapter(adapter);
                    horizontalRecipes = randomRecipes2;
                    horizontalAdapter = new HorizontalAdapter(horizontalRecipes, RecipeListFragment.this);
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
        Log.d(TAG, "onRecipeClick: " + verticalRecipes.get(position).getInstructions());
        Recipe currentRecipe = verticalRecipes.get(position);
        Intent intent = new Intent(getActivity(), RecipeDetails.class);
        intent.putExtra("MyRecipe", currentRecipe);
        startActivity(intent);

//        startActivity(currentRecipe);
    }

    @Override
    public void onHorizontalRecipeClick(int position) {
        Log.d(TAG, "onHorizontalRecipeClick: ");
        Recipe currentRecipe = horizontalRecipes.get(position);
        Intent intent = new Intent(getActivity(), RecipeDetails.class);
        intent.putExtra("MyRecipe", currentRecipe);
        startActivity(intent);
    }

    public void sendBroadcast(View view) {

    }
    
//    private void startActivity(Recipe currentRecipe) {
//        Intent intent = new Intent(getActivity(), RecipeDetails.class);
//        intent.putExtra("instructions", currentRecipe.getInstructions());
//        intent.putExtra("name", currentRecipe.getRecipeName());
//        intent.putExtra("image", currentRecipe.getImage());
//        intent.putExtra("glass", currentRecipe.getGlass());
//        intent.putExtra("ingredients", currentRecipe.getAllIngredients());
//        intent.putExtra("measurements", currentRecipe.getAllMeasurements());
//        startActivity(intent);
//    }
}



