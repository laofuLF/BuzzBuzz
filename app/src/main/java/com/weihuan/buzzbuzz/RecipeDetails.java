package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.Recipe;
import com.weihuan.buzzbuzz.fragment.IngredientsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeDetails extends AppCompatActivity {
    private static final String TAG = "RecipeDetails";
    private HashMap<String, String> ingredientMesurementMap = new HashMap<>();
    private String name;
    private String instruction;
    private String image;
    private String glass;
    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> measurements = new ArrayList<>();
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        initView();
    }

//    private void getMap(ArrayList<String> ingredients, ArrayList<String> measurements) {
//        ingredientMesurementMap.clear();
//        for (int i = 0; i < ingredients.size(); i++) {
//            ingredientMesurementMap.put(ingredients.get(i), measurements.get(i));
//        }
//    }

    private void initView() {
//        getIntentData();
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("MyRecipe");

        TextView layoutTitle = findViewById(R.id.detailRecipeTitle);
        TextView instructions = findViewById(R.id.detailRecipeInstruction);
        ImageView detailImage = findViewById(R.id.detailsImage);
        ListView ingredientsListView = findViewById(R.id.ingredientsListView);

        ingredients = recipe.getAllIngredients();
        measurements = recipe.getAllMeasurements();
        image = recipe.getImage();
        instruction = recipe.getInstructions();
        name = recipe.getRecipeName();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ingredientsListView.getLayoutParams();
        params.height = 150 * ingredients.size();
        ingredientsListView.setLayoutParams(params);
//        getMap(ingredients, measurements);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients, measurements);
        ingredientsListView.setAdapter(ingredientsAdapter);
        Log.d(TAG, "onCreate: " + instruction);
        Log.d(TAG, "onCreate: " + ingredients);
//        layoutTitle.setText(test2);
        Picasso.get().load(image).into(detailImage);
        instructions.setText(instruction);
        layoutTitle.setText(name);

        ingredientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + ingredients.get(position));
                Intent intent = new Intent(getApplication(), IngredientDescription.class);
                intent.putExtra("ingredientName", ingredients.get(position));
                startActivity(intent);
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        instruction = intent.getStringExtra("instructions");
        recipe.setInstructions(instruction);
        name = intent.getStringExtra("name");
        recipe.setRecipeName(name);
        image = intent.getStringExtra("image");
        recipe.setImage(image);
        glass = intent.getStringExtra("glass");
        recipe.setGlass(glass);
        ingredients = intent.getStringArrayListExtra("ingredients");

        recipe.setIngredient1(ingredients.get(0));
        measurements = intent.getStringArrayListExtra("measurements");
    }

    public void onClickShare(View view) {
        final String result = "Here is how to make a " + name + " \n" + instruction;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, result);
        String chooseTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooseTitle);
        startActivity(chosenIntent);
    }

    public void onClickSave(View view) {
        DatabaseHelper db = new DatabaseHelper(this);
        if (db.checkExist(recipe.getId())) {
            Toast.makeText(this, "Already Saved", Toast.LENGTH_SHORT).show();
        } else {
            db.insertRecipe(recipe);
            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
