package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weihuan.buzzbuzz.db.Ingredient;
import com.weihuan.buzzbuzz.network.IngredientResponse;
import com.weihuan.buzzbuzz.network.RecipeApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientDescription extends AppCompatActivity {
    private static final String TAG = "IngredientDescription";
    private Retrofit retrofit = null;
    private TextView title;
    private TextView style;
    private ImageView imageView;
    private TextView description;
    private TextView ifAlcoholic;
    private List<Ingredient> ingredients;

    public static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";
    public static final String IMAGE_BASE_URL = "https://www.thecocktaildb.com/images/ingredients/%s.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_description);
        Log.d(TAG, "onCreate: ");
        title = findViewById(R.id.descriptionTitle);
        style = findViewById(R.id.descriptionStyle);
        imageView = findViewById(R.id.descriptionImage);
        description = findViewById(R.id.descriptionDetails);
        ifAlcoholic = findViewById(R.id.descriptionAlcohol);
        initView();
    }

    private void initView() {
        

        Intent intent = getIntent();
        String titleData = intent.getStringExtra("ingredientName");
        String imageURL = String.format(IMAGE_BASE_URL, titleData);
        title.setText(titleData);
        Picasso.get().load(imageURL).into(imageView);

        fetchIngredientData(titleData);
    }

    private void fetchIngredientData(String query) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RecipeApiService recipeApiService = retrofit.create(RecipeApiService.class);
        Call<IngredientResponse> call = recipeApiService.getIngredient(query);
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                Log.d(TAG, "onResponse: ");
                IngredientResponse ingredientResponse = response.body();
                ingredients = ingredientResponse.getIngredient();
                Ingredient ingredient = ingredients.get(0);
                try {
                    String type = ingredient.getType();
                    if (!type.equals("null")) {
                        style.setText("Style: " + type);
                    }
                } catch (Exception e) {
                    style.setVisibility(View.GONE);
                }

                description.setText(ingredient.getDescription());
                try {
                    if (ingredient.getIfAlcohol().equals("Yes")) {
                        ifAlcoholic.setText("Alcoholic");
                    } else {
                        ifAlcoholic.setText("Non-alcoholic");
                    }
                } catch (Exception e) {
                    ifAlcoholic.setVisibility(View.GONE);
                }

                Log.d(TAG, "onResponse: " + ingredient.getDescription());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
