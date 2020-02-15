package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RecipeDetails extends AppCompatActivity {
    private static final String TAG = "RecipeDetails";

    private TextView layoutTitle;
    private TextView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        layoutTitle.findViewById(R.id.detailRecipeTitle);
        instructions.findViewById(R.id.detailRecipeInstruction);
        Intent intent = getIntent();
        String test2 = intent.getStringExtra("test");

        layoutTitle.setText(test2);
    }
}
