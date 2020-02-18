package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeDetails extends AppCompatActivity {
    private static final String TAG = "RecipeDetails";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        TextView layoutTitle = findViewById(R.id.detailRecipeTitle);
        TextView instructions = findViewById(R.id.detailRecipeInstruction);
        ImageView detailImage = findViewById(R.id.detailsImage);
        Intent intent = getIntent();
        String instruction = intent.getStringExtra("instructions");
        String name = intent.getStringExtra("name");
        String image = intent.getStringExtra("image");
        String glass = intent.getStringExtra("glass");

        Log.d(TAG, "onCreate: " + instruction);


//        layoutTitle.setText(test2);
        instructions.setText(instruction);
        layoutTitle.setText(name);
        Picasso.get().load(image).into(detailImage);
    }
}
