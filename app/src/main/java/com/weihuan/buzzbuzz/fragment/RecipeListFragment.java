package com.weihuan.buzzbuzz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weihuan.buzzbuzz.R;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends Fragment {

    private FrameLayout fragmentContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;
    private CardView cardView;


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
        int tabIndex = getArguments().getInt("index", -1);
        switch (tabIndex) {
            case 2:
                initMyRecipe(view);
                break;
        }
        return view;
    }

    /**
     * Init the fragment
     */
    private void initMyRecipe(View view) {

        fragmentContainer = view.findViewById(R.id.fragment_container);
        recyclerView = view.findViewById(R.id.fragment_recycler_view);
        cardView = view.findViewById(R.id.card_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // test db data
        ArrayList<String> cocktailNames = new ArrayList<>();
        ArrayList<String> ImageUrls = new ArrayList<>();
        List<Recipe> RecipeDatabase = new ArrayList<>();
        RecipeDatabase.addAll(db.getAllRecipes());
        for (Recipe recipeDB: RecipeDatabase) {
            int id = recipeDB.getId();
            Log.i("Database data:", String.valueOf(id));
            cocktailNames.add(recipeDB.getRecipeName());
            ImageUrls.add(recipeDB.getImage());
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

        ListAdapter adapter = new ListAdapter(cocktailNames, ImageUrls);
        recyclerView.setAdapter(adapter);
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

}



