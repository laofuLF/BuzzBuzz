package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RecipeDetails extends AppCompatActivity {
    private static final String TAG = "RecipeDetails";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        TextView layoutTitle = findViewById(R.id.detailRecipeTitle);
        TextView instructions = findViewById(R.id.detailRecipeInstruction);
        Intent intent = getIntent();
        String test2 = intent.getStringExtra("instructions");

//        layoutTitle.setText(test2);
        instructions.setText(test2);

    }
}
